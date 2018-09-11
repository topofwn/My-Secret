package com.example.kos.mysecrect.ui.homepage;

import android.os.Bundle;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.generatekey.GenerateKeyActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.ui.manualgenerate.ManualGenerateActivity;
import com.example.kos.mysecrect.ui.pwdstore.PWDStoreActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.FirebaseUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.UIUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements View.OnClickListener{
    private HomePagePresenter mPresenter = new HomePagePresenter();
    private Button btnGenerate,btnManual,btnStore,btnSignOut,btnDelAcc,btnClrDb;
    private FirebaseFirestore db;
    private static final String USER_KEY_DATA = "USER_KEY_DATA" ;
    private UserD user;
    private TextView header;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPresenter = new HomePagePresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(HomePageActivity.this));


        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initView();
        initData();
    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            if(mDrawerLayout.isDrawerOpen(Gravity.START)){
                mDrawerLayout.closeDrawer(Gravity.START);
            }else{
                mDrawerLayout.openDrawer(Gravity.START);
            }
            break;

        default: break;


    }
    return true;
}



    @Override
    protected void initView() {
        //TODO: create navigation view
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)  {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ham);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        header = findViewById(R.id.txtHeader);
        btnGenerate = findViewById(R.id.btn1);
        btnGenerate.setOnClickListener(this);
        btnManual = findViewById(R.id.btn3);
        btnManual.setOnClickListener(this);
        btnStore = findViewById(R.id.nav_store);
        btnStore.setOnClickListener(this);
        btnSignOut = findViewById(R.id.nav_sign_out);
        btnSignOut.setOnClickListener(this);
        btnClrDb = findViewById(R.id.nav_del_data);
        btnClrDb.setOnClickListener(this);
        btnDelAcc = findViewById(R.id.nav_del_usr);
        btnDelAcc.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        Bundle data = this.getIntent().getExtras();
        if(data.get(USER_KEY_DATA) != null){
             user = (UserD) data.get(USER_KEY_DATA);
        }
        db = FirebaseFirestore.getInstance();
        header.setText(user.getEmail());
        showLoading();
        DocumentReference col = db.collection("DataPWd").document(user.getId());
        col.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserD user1 = documentSnapshot.toObject(UserD.class);
                List<DataPWD> mArray = new ArrayList<>();
                if(user1 != null){
                    if(user1.getListData() != null) {
                        mArray = user1.getListData();
                    }
                }
                mPresenter.setUser(user1);
                mPresenter.setListData(mArray);
                hideLoading();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn1){
            ActivityUtils.startActivity(HomePageActivity.this,GenerateKeyActivity.class,false,true);
        }
        else if(v.getId() == R.id.btn3){
            ActivityUtils.startActivity(HomePageActivity.this, ManualGenerateActivity.class,false,true);
        }else if(v.getId() == R.id.nav_store){
            ActivityUtils.startActivity(HomePageActivity.this,PWDStoreActivity.class,false,true);
            mDrawerLayout.closeDrawers();
        }else if(v.getId() == R.id.nav_sign_out){
            FirebaseAuth.getInstance().signOut();
            ActivityUtils.startActivity(HomePageActivity.this,LoginActivity.class,true,true);
            mDrawerLayout.closeDrawers();
        }else if(v.getId() == R.id.nav_del_data){
            //clear data
            FirebaseUtils.deleteData(mPresenter.getUser());
            mPresenter.setListData(new ArrayList<>());
            UserD mUser = mPresenter.getUser();
            mUser.setListData(new ArrayList<>());
            mPresenter.setUser(mUser);
            UIUtils.showToast(getApplicationContext(),"Data cleaned");
            mDrawerLayout.closeDrawers();
        }else if(v.getId() == R.id. nav_del_usr){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(mPresenter.getUser().getEmail(),mPresenter.getUser().getPw());
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                UIUtils.showToast(getApplicationContext(),"User deleted");
                               FirebaseFirestore.getInstance().collection("DataPWd").document(user.getUid()).delete();
                                ActivityUtils.startActivity(HomePageActivity.this,LoginActivity.class,true,true);
                            }
                        }
                    });

                }
            });
            mDrawerLayout.closeDrawers();
        }
    }


    @Override
    public void DismissDialog() {

    }


}
