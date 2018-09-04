package com.example.kos.mysecrect.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.homepage.HomePageActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private FirebaseFirestore db;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(SplashActivity.this));
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();

    }

@Override
protected void onStart() {
    super.onStart();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    if(currentUser != null){
        ActivityUtils.startActivity(SplashActivity.this, HomePageActivity.class,false,true);
    }
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
        updateProgressBar(50);
        List<DataPWD> myArray = new ArrayList<>();
        CollectionReference col = db.collection("DataPWd");
        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    DataPWD data = document.toObject(DataPWD.class);
                    myArray.add(data);
                }
                mPresenter.setListData(myArray);
                updateProgressBar(80);
                gotoHomePage();
            }

        });
    }

    @Override
    public void updateProgressBar(int count) {
        if(mProgressBar != null ){
            mProgressBar.setProgress(count);
        }
    }

    @Override
    public void gotoHomePage() {
        ActivityUtils.startActivity(SplashActivity.this,HomePageActivity.class,true);
    }
}
