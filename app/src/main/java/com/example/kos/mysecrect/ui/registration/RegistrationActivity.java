package com.example.kos.mysecrect.ui.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.homepage.HomePageActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener, RegistrationContract.View {
    private RegistrationPresenter mPresenter = new RegistrationPresenter();
    private EditText edtEmail,edtPass,edtRePass;
    private Button btnRegis;
    private FirebaseAuth mAuth;
    private static final String USER_KEY_DATA = "USER_KEY_DATA" ;

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
            hideKeyboard();
            //TODO: send verify email and create user
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
                            if (task.isSuccessful()) {
                                gotoHomepage();
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
    protected void initView() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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
        user.setEmail(mAuth.getCurrentUser().getEmail());
        user.setId(mAuth.getCurrentUser().getUid());
        user.setListData(mPresenter.getListData());
        mPresenter.setUser(user);
        Bundle bd = new Bundle();
        bd.putSerializable(USER_KEY_DATA,user);
        ActivityUtils.startActivityWithData(RegistrationActivity.this,HomePageActivity.class,true,bd);
    }
}
