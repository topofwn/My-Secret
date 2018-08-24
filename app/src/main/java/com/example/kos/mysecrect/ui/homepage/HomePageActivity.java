package com.example.kos.mysecrect.ui.homepage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.pwdstore.PWDStoreActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {
    private HomePagePresenter mPresenter = new HomePagePresenter();
    private Button btnGenerate, btnMykey;
    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();
        db.collection("DataPWd").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DataPWD> initData = queryDocumentSnapshots.toObjects(DataPWD.class);
                mPresenter.setListData(initData);
            }
        });
       //TODO : get list data with device id
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn1){
            ActivityUtils.startActivity(HomePageActivity.this,PWDStoreActivity.class,true);
        }
        else if (v.getId() == R.id.btn2){
            ActivityUtils.startActivity(HomePageActivity.this, PWDStoreActivity.class,true);
        }
    }
}
