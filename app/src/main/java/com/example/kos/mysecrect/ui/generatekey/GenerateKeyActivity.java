package com.example.kos.mysecrect.ui.generatekey;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.utils.EncrytedUtils;
import com.example.kos.mysecrect.utils.FirebaseUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.UIUtils;

import java.security.SecureRandom;
import java.util.List;


public class GenerateKeyActivity extends BaseActivity implements View.OnClickListener {
    private GenerateKeyPresenter mPresenter = new GenerateKeyPresenter();
    private TextView key;
    private Button generate, save;

    private String errorMaxLength,errorMinLength;
    private EditText numDigit,edtName;
    private List<String> myData;
    final static String allowedCharacter = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#%&+=-";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_key);
        errorMaxLength = getResources().getString(R.string.pwd_max_lengths_error);
        errorMinLength = getResources().getString(R.string.digit_null_error);
        mPresenter = new GenerateKeyPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(GenerateKeyActivity.this));
        turnOnTouchHideKeyBoard();
        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();
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
        key = findViewById(R.id.txtKey);
        generate = findViewById(R.id.btnGenerate);
        generate.setOnClickListener(this);
        save = findViewById(R.id.btnSaveKey);
        save.setOnClickListener(this);
        numDigit = findViewById(R.id.edtDigit);
        numDigit.setOnClickListener(this);
        edtName = findViewById(R.id.edtAppName);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_done_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
    super.onBackPressed();
    finish();
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSaveKey){
            hideKeyboard();
            if (!edtName.getText().toString().equals("")){

                    DataPWD newData = new DataPWD(edtName.getText().toString(), key.getText().toString(),edtName.getText().toString());
                try {
                    newData = EncrytedUtils.Encrypt(newData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FirebaseUtils.addNewField(newData);
                    List<DataPWD> tempList = mPresenter.getList();
                    tempList.add(newData);
                    mPresenter.setList(tempList);
                    UIUtils.showToast(getApplicationContext(),"Saved successfully");

            }else {
                UIUtils.showToast(getApplicationContext(),"Please enter the application name");
            }
        }
        else if (v.getId() == R.id.btnGenerate){

                String numdigit = numDigit.getText().toString();
                if (numdigit.equals("") || numdigit.equals("0")) {
                    UIUtils.showToast(getApplicationContext(), errorMinLength);
                } else if (Integer.parseInt(numdigit) >= 15) {
                    UIUtils.showToast(getApplicationContext(), errorMaxLength);
                } else {
                    key.setText(getRandomString(Integer.parseInt(numdigit)));
                }

        }
        else if (v.getId() == R.id.edtDigit){
            numDigit.setText("");
        }
    }



    public String getRandomString(int strLength){
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(strLength);
        for(int i = 0; i < strLength; i++){
            sb.append(allowedCharacter.charAt(rnd.nextInt(allowedCharacter.length())));
        }

     return sb.toString() ;
    }
}
