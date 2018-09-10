package com.example.kos.mysecrect.ui.homepage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.generatekey.GenerateKeyActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.ui.manualgenerate.ManualGenerateActivity;
import com.example.kos.mysecrect.ui.pwdstore.PWDStoreActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{
    private HomePagePresenter mPresenter = new HomePagePresenter();
    private Button btnGenerate,btnManual;
    private FirebaseFirestore db;
    private static final String USER_KEY_DATA = "USER_KEY_DATA" ;
    private UserD user;
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
        navigationView.setNavigationItemSelectedListener(this);

        btnGenerate = findViewById(R.id.btn1);
        btnGenerate.setOnClickListener(this);
        btnManual = findViewById(R.id.btn3);
        btnManual.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle data = this.getIntent().getExtras();
        if(data.get(USER_KEY_DATA) != null){
             user = (UserD) data.get(USER_KEY_DATA);
        }
        db = FirebaseFirestore.getInstance();


        showLoading();
        DocumentReference col = db.collection("DataPWd").document(user.getId());
        col.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserD user1 = documentSnapshot.toObject(UserD.class);
                List<DataPWD> mArray = new ArrayList<>();
                if(user1.getListData() != null){
                   mArray = user1.getListData();
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
        }


    }


    @Override
    public void DismissDialog() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_store:
                ActivityUtils.startActivity(HomePageActivity.this,PWDStoreActivity.class,true,true);
                break;
            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                ActivityUtils.startActivity(HomePageActivity.this,LoginActivity.class,true,true);
                break;
            case R.id.nav_del_data:
                break;
            case R.id.nav_del_usr:
                break;
            default:break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }
}
