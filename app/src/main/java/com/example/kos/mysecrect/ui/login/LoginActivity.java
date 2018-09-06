package com.example.kos.mysecrect.ui.login;

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
import com.example.kos.mysecrect.ui.registration.RegistrationActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.UIUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends BaseActivity implements View.OnClickListener,LoginContract.View{
    private static final String SEND_FROM_REGISTRATION = "SEND_FROM_REGISTRATION" ;
    private LoginPresenter mPresenter = new LoginPresenter();
    private EditText edtEmail, edtPass;
    private Button btnSignIn,btnSignUp;
    private FirebaseAuth mAuth;
    private static final String USER_KEY_DATA = "USER_KEY_DATA" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(LoginActivity.this));
        turnOnTouchHideKeyBoard();
        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        mAuth = FirebaseAuth.getInstance();
        Bundle data =getIntent().getExtras();

        if (mAuth.getCurrentUser() != null){
           if(mAuth.getCurrentUser().isEmailVerified()) {
               gotoHomePage();
           }
        }
        initData();
    }




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignUp){
            hideKeyboard();
            gotoRegistration();
        }else if(v.getId() == R.id.btnSignIn){
            hideKeyboard();
            SignInWithEmailAndPass();
        }
    }

    private void SignInWithEmailAndPass() {
        String email = edtEmail.getText().toString();
        String pwd = edtPass.getText().toString();
        if(email.equals("") || pwd.equals("")){
            onError("Please fill email and password");
        }else{
            mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        if(!mAuth.getCurrentUser().isEmailVerified()){
                            UIUtils.showToast(LoginActivity.this,"Please verify account");
                        }else {
                            gotoHomePage();
                        }
                    } else {
                        onError("Authentication Failed");

                    }
                }
            });
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
        edtPass = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void gotoRegistration() {
        ActivityUtils.startActivity(LoginActivity.this, RegistrationActivity.class,true);
    }

    @Override
    public void gotoHomePage() {
        UserD user = new UserD();
        mAuth = FirebaseAuth.getInstance();

            user.setEmail(mAuth.getCurrentUser().getEmail());
            user.setId(mAuth.getCurrentUser().getUid());
            user.setListData(mPresenter.getListData());
            mPresenter.setUser(user);
            Bundle bd = new Bundle();
            bd.putSerializable(USER_KEY_DATA, user);
            ActivityUtils.startActivityWithData(LoginActivity.this, HomePageActivity.class, true, bd);

    }

    @Override
    public void DismissDialog() {

    }
}
