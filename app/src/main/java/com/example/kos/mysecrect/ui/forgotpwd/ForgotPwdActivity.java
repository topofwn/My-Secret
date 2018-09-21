package com.example.kos.mysecrect.ui.forgotpwd;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.UIUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPwdActivity extends BaseActivity implements  View.OnClickListener{
   private ForgotPwdPresenter mPresenter = new ForgotPwdPresenter();
   private EditText mail;
   private Button btnSend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_forgot_pwd);
        mPresenter = new ForgotPwdPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(ForgotPwdActivity.this));
        turnOnTouchHideKeyBoard();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initView();
        initData();
    }


    @Override
    protected void initView() {
     mail = findViewById(R.id.edtMailFG);
     btnSend = findViewById(R.id.btnSend);
     btnSend.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void DismissDialog() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSend){
            if (!mail.getText().toString().equals("")) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(mail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    UIUtils.showToast(ForgotPwdActivity.this, "Sent reset email to your mail. Please check out your inbox");
                                    ActivityUtils.startActivity(ForgotPwdActivity.this, LoginActivity.class,true,true);
                                }
                            }
                        });
            }else{
                UIUtils.showToast(ForgotPwdActivity.this,"Please fill your email address");
            }
        }
    }
}
