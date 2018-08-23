package com.example.kos.mysecrect.ui.homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.utils.Injections;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {
    private HomePagePresenter mPresenter = new HomePagePresenter();
    private Button btnGenerate, btnMykey;

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
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btnGenerate = findViewById(R.id.btn1);
        btnGenerate.setOnClickListener(this);
        btnMykey = findViewById(R.id.btn2);
        btnMykey.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn1){

        }
        else if (v.getId() == R.id.btn2){

        }
    }
}
