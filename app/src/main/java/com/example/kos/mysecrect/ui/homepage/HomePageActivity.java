package com.example.kos.mysecrect.ui.homepage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.kos.mysecrect.utils.IOUtils;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.UIUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;

public class HomePageActivity extends BaseActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String USER_KEY_DATA = "USER_KEY_DATA";
    private HomePagePresenter mPresenter = new HomePagePresenter();
    private Button btnGenerate, btnManual, btnStore, btnSignOut, btnDelAcc, btnClrDb;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private UserD user;
    private TextView header;
    private DrawerLayout mDrawerLayout;
    private ImageButton avatar;

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
                if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.closeDrawer(Gravity.START);
                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
                break;

            default:
                break;


        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserD usr = mPresenter.getUser();
        updateUI(usr);
    }


    @Override
    protected void initView() {

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ham);
        }
        mDrawerLayout = findViewById(R.id.drawer_layout);
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

        avatar = findViewById(R.id.imgIcon);
        avatar.setOnClickListener(this);

    }

    @Override
    protected void initData() {
//        Bundle data = this.getIntent().getExtras();
//        if (data.get(USER_KEY_DATA) != null) {
//            user = (UserD) data.get(USER_KEY_DATA);
//            FirebaseUtils.addNewField(user);
//        }
//        db = FirebaseFirestore.getInstance();
//        header.setText(user.getEmail());
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        showLoading();
        DocumentReference col = db.collection("DATA").document(mAuth.getUid());
        col.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserD user1 = documentSnapshot.toObject(UserD.class);
                List<DataPWD> mArray = new ArrayList<>();
                if (user1 != null) {
                    if (user1.getListData() != null) {
                        mArray = user1.getListData();
                    }
                    mPresenter.setUser(user1);
                    mPresenter.setListData(mArray);
                    updateUI(user1);
                }

                hideLoading();
            }
        });

    }

    private void updateUI(UserD user1) {
        if (!user1.getBitmap().equals("")) {
            avatar.setImageBitmap(IOUtils.decodeBase64ToBitmap(user1.getBitmap()));
        }
        header.setText(user1.getEmail());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            ActivityUtils.startActivity(HomePageActivity.this, GenerateKeyActivity.class, false, true);
        } else if (v.getId() == R.id.btn3) {
            ActivityUtils.startActivity(HomePageActivity.this, ManualGenerateActivity.class, false, true);
        } else if (v.getId() == R.id.nav_store) {
            ActivityUtils.startActivity(HomePageActivity.this, PWDStoreActivity.class, false, true);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            ActivityUtils.startActivity(HomePageActivity.this, LoginActivity.class, true, true);
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.nav_del_data) {
            //clear data

            mPresenter.setListData(new ArrayList<>());
            UserD mUser = mPresenter.getUser();
            mUser.setListData(new ArrayList<>());
            mPresenter.setUser(mUser);
            FirebaseUtils.deleteData(mUser);
            UIUtils.showToast(getApplicationContext(), "Data cleaned");
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.nav_del_usr) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(mPresenter.getUser().getEmail(), mPresenter.getUser().getPw());
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                UIUtils.showToast(getApplicationContext(), "User deleted");
                                FirebaseFirestore.getInstance().collection("DATA").document(user.getUid()).delete();
                                ActivityUtils.startActivity(HomePageActivity.this, LoginActivity.class, true, true);
                            }
                        }
                    });

                }
            });
            mDrawerLayout.closeDrawers();
        } else if (v.getId() == R.id.imgIcon) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    requestPermissionsSafely(new String[]{CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            updateUserProfile(imageBitmap);
        }
    }

    private void updateUserProfile(Bitmap imageBitmap) {
        UserD user = mPresenter.getUser();
        user.setBitmap(IOUtils.encodeBitmapToBase64(imageBitmap));
        avatar.setImageBitmap(imageBitmap);
        mPresenter.setUser(user);
        FirebaseUtils.addNewField(user);
    }


    @Override
    public void DismissDialog() {

    }


}
