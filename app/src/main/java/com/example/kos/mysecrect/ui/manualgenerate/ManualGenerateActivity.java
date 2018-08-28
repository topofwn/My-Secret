package com.example.kos.mysecrect.ui.manualgenerate;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.utils.Injections;

public class ManualGenerateActivity extends BaseActivity implements View.OnClickListener {
    private ManualGeneratePresenter mPresenter = new ManualGeneratePresenter();
    private EditText edtApplication,edtPWD;
    private Button btnSave,btnClear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_generate);
        mPresenter = new ManualGeneratePresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(ManualGenerateActivity.this));
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
      edtApplication = findViewById(R.id.edtApplication);
        edtPWD = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnClear){
            edtApplication.setText("");
            edtPWD.setText("");
        }else if (v.getId() == R.id.btnSave){

        }

    }



}
