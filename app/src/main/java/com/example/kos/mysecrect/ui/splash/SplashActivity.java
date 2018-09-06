package com.example.kos.mysecrect.ui.splash;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.homepage.HomePageActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.AlertType;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.MessageType;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.example.kos.mysecrect.utils.UIUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.CharMatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity implements View.OnClickListener, SplashContract.View {

    private SplashPresenter mPresenter = new SplashPresenter();
    private ProgressBar mProgressBar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(!isNetworkConnected()){
            onNoInternetConnection();
        }else {
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
}
