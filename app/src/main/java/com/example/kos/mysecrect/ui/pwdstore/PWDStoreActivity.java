package com.example.kos.mysecrect.ui.pwdstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.pwdstore.adapter.PWDStoreAdapter;
import com.example.kos.mysecrect.utils.FirebaseUtils;
import com.example.kos.mysecrect.utils.Injections;

import java.util.ArrayList;
import java.util.List;

public class PWDStoreActivity extends BaseActivity implements View.OnClickListener {
    private PWDStorePresenter mPresenter = new PWDStorePresenter();
    private ListView listData;
    private PWDStoreAdapter mPWDStoreAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_store);
        mPresenter = new PWDStorePresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(PWDStoreActivity.this));
        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_done_menu, menu);
        return true;
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default: break;


        }
        return true;
    }


    @Override
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        listData = findViewById(R.id.lv_data);
        FirebaseUtils.getDataFromFirebase(mPresenter.getDeveiceId());
        List<DataPWD> myList = new ArrayList<>();
//        mPWDStoreAdapter =new PWDStoreAdapter(getApplicationContext(),R.layout.item_data,myList);
//        listData.setAdapter(mPWDStoreAdapter);
//        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
//        listData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                return false;
//            }
//        });
    }

    @Override
    protected void initData() {

    }
}
