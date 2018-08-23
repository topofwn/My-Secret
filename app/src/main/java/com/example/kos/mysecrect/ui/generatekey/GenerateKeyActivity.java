package com.example.kos.mysecrect.ui.generatekey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.utils.Injections;
import com.google.firebase.firestore.FirebaseFirestore;


public class GenerateKeyActivity extends BaseActivity implements View.OnClickListener {
    private GenerateKeyPresenter mPresenter = new GenerateKeyPresenter();
    private TextView key;
    private Button generate, save;
    private  FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GenerateKeyPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(GenerateKeyActivity.this));
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        key = findViewById(R.id.txtKey);
        generate = findViewById(R.id.btnGenerate);
        generate.setOnClickListener(this);
        save = findViewById(R.id.btnSaveKey);
        save.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSaveKey){

        }
        else if (v.getId() == R.id.btnGenerate){

        }
    }
}
