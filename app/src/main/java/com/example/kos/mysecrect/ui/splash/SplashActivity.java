package com.example.kos.mysecrect.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.dialog.ConnectionDialog;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.FirebaseApp;

import java.io.IOException;

public class SplashActivity extends BaseActivity implements View.OnClickListener, SplashContract.View {

    private SplashPresenter mPresenter = new SplashPresenter();
    private ProgressBar mProgressBar;

    private ConnectionDialog notifyConnectionDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(SplashActivity.this));

        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();

    }



    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        mProgressBar = findViewById(R.id.prgBar);
    }

    @Override
    protected void initData() {
        //TODO: check network connection, build notify dialog
       if(isNetworkConnected()) {
            Handler handler = new Handler();
            handler.post(() -> {
                Thread tr = new Thread(() -> {
                    AdvertisingIdClient.Info adInfo;
                    String id;
                    try {
                        adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                        id = adInfo.getId();
                        mPresenter.setMyDeviceId(id);
                    } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
                        // Encountered a recoverable error connecting to Google Play services.
                        OGILVYLog.l(exception);
                    }
                });
                tr.start();
            });


            updateProgressBar(90);
            gotoLogin();
        }
        else{
           onNoInternetConnection();
       }
    }





    @Override
    public void updateProgressBar(int count) {
        if(mProgressBar != null ){
            mProgressBar.setProgress(count);
        }
    }


    @Override
    public void gotoLogin() {
        ActivityUtils.startActivity(SplashActivity.this, LoginActivity.class,true);
    }

    @Override
    public void DismissDialog() {

    }
    @Override
    public void onNoInternetConnection() {
        if (notifyConnectionDialog == null) {
            notifyConnectionDialog = new ConnectionDialog(SplashActivity.this);
            notifyConnectionDialog.setDialogListener(this);
        }
        if (!notifyConnectionDialog.isShowing()) {
            notifyConnectionDialog.show();
        }
    }


}
