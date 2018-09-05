package com.example.kos.mysecrect.ui.splash;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.BaseActivity;
import com.example.kos.mysecrect.ui.homepage.HomePageActivity;
import com.example.kos.mysecrect.ui.login.LoginActivity;
import com.example.kos.mysecrect.utils.ActivityUtils;
import com.example.kos.mysecrect.utils.AlertType;
import com.example.kos.mysecrect.utils.Injections;
import com.example.kos.mysecrect.utils.MessageType;
import com.example.kos.mysecrect.utils.OGILVYLog;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity implements View.OnClickListener, SplashContract.View,android.location.LocationListener {

    private SplashPresenter mPresenter = new SplashPresenter();
    private ProgressBar mProgressBar;
    private FirebaseFirestore db;
    private static final int REQUEST_ACCESS_LOCATION_CODE = 2;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(Injections.provideSchedulerProvider(),
                Injections.provideAppDataManager(SplashActivity.this));
        initView();
        mPresenter.onAttach(this);
        mPresenter.onViewInitialized();
        initData();
        if (hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                && hasPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getLastUnknownLocation();
        } else {
            requestPermissionsSafely(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_LOCATION_CODE);
        }
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        mProgressBar = findViewById(R.id.prgBar);
    }

    @Override
    protected void initData() {

        Handler handler = new Handler();
        handler.post(() -> {
            Thread tr = new Thread(() -> {
                AdvertisingIdClient.Info adInfo;
                String id;
                try {
                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    id = adInfo.getId();
                    mPresenter.setMyDeviceId(id);
                } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
                    // Encountered a recoverable error connecting to Google Play services.
                    OGILVYLog.l(exception);
                }
            });
            tr.start();
        });
        updateProgressBar(90);
        gotoLogin();

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_LOCATION_CODE) {
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // user rejected the permission
                    getLastUnknownLocation();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastUnknownLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                //TODO:save location
                mPresenter.setLocation(location.getLatitude(),location.getLongitude());
            } else {

                showMessage("No location found", MessageType.ERROR, AlertType.TOAST);
            }
        } else if (isGPSEnabled) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                mPresenter.setLocation(location.getLatitude(),location.getLongitude());
            } else {
                // TODO: 7/26/18 locallize string xml
                showMessage("No location found", MessageType.ERROR, AlertType.TOAST);
            }
        }
    }

    @Override
    public void updateProgressBar(int count) {
        if(mProgressBar != null ){
            mProgressBar.setProgress(count);
        }
    }


    @Override
    public void gotoLogin() {
        ActivityUtils.startActivity(SplashActivity.this, LoginActivity.class,true);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
