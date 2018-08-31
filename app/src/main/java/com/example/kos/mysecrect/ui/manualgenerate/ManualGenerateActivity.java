package com.example.kos.mysecrect.ui.manualgenerate;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.utils.EncrytedUtils;
import com.example.kos.mysecrect.utils.FirebaseUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.UIUtils;

import java.util.List;

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
        turnOnTouchHideKeyBoard();
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
            UIUtils.showToast(getApplicationContext(),"Cleared data");
            hideKeyboard();
        }else if (v.getId() == R.id.btnSave){
            hideKeyboard();
            if(!edtApplication.getText().toString().equals("")){
                if(!edtPWD.getText().toString().equals("")){


                        DataPWD newData = new DataPWD(edtApplication.getText().toString(), edtPWD.getText().toString(),edtApplication.getText().toString());
                        List<DataPWD> tempList = mPresenter.getList();
                    try {
                        newData = EncrytedUtils.Encrypt(newData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tempList.add(newData);
                        mPresenter.setList(tempList);
                        FirebaseUtils.addNewField(newData,mPresenter.getMyDeviceId());
                        UIUtils.showToast(getApplicationContext(),"Saved successfully");


                }else {
                    UIUtils.showToast(getApplicationContext(),"Please enter the password");
                }
            }else{
                UIUtils.showToast(getApplicationContext(),"Please enter the application name");
            }
        }

    }

}
