package com.example.kos.mysecrect.ui.homepage;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.generatekey.GenerateKeyActivity;
import com.example.kos.mysecrect.ui.manualgenerate.ManualGenerateActivity;
import com.example.kos.mysecrect.ui.pwdstore.PWDStoreActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {
    private HomePagePresenter mPresenter = new HomePagePresenter();
    private Button btnGenerate, btnMykey,btnManual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mPresenter = new HomePagePresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(HomePageActivity.this));

        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();
    }

    @Override
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        btnGenerate = findViewById(R.id.btn1);
        btnGenerate.setOnClickListener(this);
        btnMykey = findViewById(R.id.btn2);
        btnMykey.setOnClickListener(this);
        btnManual = findViewById(R.id.btn3);
        btnManual.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn1){
            ActivityUtils.startActivity(HomePageActivity.this,GenerateKeyActivity.class,false,true);
        }
        else if (v.getId() == R.id.btn2){
            ActivityUtils.startActivity(HomePageActivity.this, PWDStoreActivity.class,false,true);
        }
        else if(v.getId() == R.id.btn3){
            ActivityUtils.startActivity(HomePageActivity.this, ManualGenerateActivity.class,false,true);
        }
    }



}
