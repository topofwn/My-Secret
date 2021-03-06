package com.example.kos.mysecrect.ui.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.FirebaseUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener, RegistrationContract.View {
    private RegistrationPresenter mPresenter = new RegistrationPresenter();
    private EditText edtEmail,edtPass,edtRePass;
    private Button btnRegis;
    private FirebaseAuth mAuth;
    private static final String SEND_FROM_REGISTRATION = "SEND_FROM_REGISTRATION" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mPresenter = new RegistrationPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(RegistrationActivity.this));
        turnOnTouchHideKeyBoard();
        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();
    }






    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSignUp){
            showLoading();
            hideKeyboard();

            String email = edtEmail.getText().toString();
            String pwd = edtPass.getText().toString();
            String re_pwd = edtRePass.getText().toString();
            if(email.equals("") || pwd.equals("") || re_pwd.equals("")){
                onError("Please fill email and password completely");
            }else{
                if(!pwd.equals(re_pwd)){
                    onError("Your password and re-password aren't a same");
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideLoading();
                            if (task.isSuccessful()) {
                               gotoHomepage();
                              //  gotoLogin();
                            } else {
                                onError("Create user failed");
                            }
                        }
                    });
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;


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
        edtEmail = findViewById(R.id.edtEmail);
        edtPass =findViewById(R.id.edtPassword);
        edtRePass = findViewById(R.id.edtRePassword);
        btnRegis = findViewById(R.id.btnSignUp);
        btnRegis.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void gotoHomepage() {
        UserD user = new UserD();
        user.setPw(edtPass.getText().toString());
        user.setEmail(mAuth.getCurrentUser().getEmail());
        user.setId(mAuth.getCurrentUser().getUid());
        List<DataPWD> mArray = new ArrayList<>();
        mPresenter.setListData(mArray);
        user.setListData(mArray);
        mPresenter.setUser(user);
        FirebaseUtils.addNewField(user);
        mAuth.getCurrentUser().sendEmailVerification();
        Bundle bd = new Bundle();
        bd.putCharSequence(SEND_FROM_REGISTRATION,"registration");
        ActivityUtils.startActivityWithData(RegistrationActivity.this,LoginActivity.class,true,bd);
//        Bundle bd = new Bundle();
//        bd.putSerializable(USER_KEY_DATA,user);
//        ActivityUtils.startActivityWithData(RegistrationActivity.this,HomePageActivity.class,true,bd);
    }
    public void gotoLogin(){

    }
    @Override
    public void DismissDialog() {

    }
}
