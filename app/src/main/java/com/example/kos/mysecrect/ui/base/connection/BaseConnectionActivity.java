package com.example.kos.mysecrect.ui.base.connection;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.dienquang.shop.apollo.AppConst;
import com.dienquang.shop.apollo.R;
import com.dienquang.shop.apollo.data.model.Group;
import com.dienquang.shop.apollo.data.services.UartService;
import com.dienquang.shop.apollo.ui.base.BaseActivity;
import com.dienquang.shop.apollo.ui.listdevice.ListDeviceActivity;
import com.dienquang.shop.apollo.utils.AlertType;
import com.dienquang.shop.apollo.utils.Injections;
import com.dienquang.shop.apollo.utils.MessageType;
import com.dienquang.shop.apollo.utils.OGILVYLog;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.io.UnsupportedEncodingException;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;
import static com.dienquang.shop.apollo.AppConst.UART_PROFILE_CONNECTED;
import static com.dienquang.shop.apollo.AppConst.UART_PROFILE_DISCONNECTED;
import static com.dienquang.shop.apollo.utils.AppConstants.SCAN_PERIOD;

/**
 * Created by tuan.giao on 1/22/2018.
 */

public class BaseConnectionActivity extends BaseActivity implements BluetoothAdapter.LeScanCallback, ServiceConnection {

    protected int BLUETOOTH_REQUEST_CODE = 11;
    protected UartService mService = null;
    protected int mState = UART_PROFILE_DISCONNECTED;
    protected String mainMAC = "";
    protected BluetoothAdapter mBluetoothAdapter;
    private boolean foundDevice;
    private Handler mHandler;
    private boolean mBound = false;
    private boolean isShowingDialog;

    protected boolean shouldPromptConnectWhenNotFoundMac = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainMAC = Injections.provideMainMAC();
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        handler = new Handler();
        initService();
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {
    }

    protected void initService() {
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, this, Context.BIND_AUTO_CREATE);

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    @Override
    protected void onDestroy() {
        OGILVYLog.logTuan("on Stop","yes", BaseConnectionActivity.class);
        super.onStop();
        try {
            if (mBound) {
                unbindService(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
        }
    }

    protected void startToScanDevices() {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (!hasPermission(Manifest.permission.BLUETOOTH) || !hasPermission(BLUETOOTH_ADMIN)
                || !hasPermission(ACCESS_COARSE_LOCATION)) {
            requestPermissionsSafely(new String[]{BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_COARSE_LOCATION}, BLUETOOTH_REQUEST_CODE);
        } else {
            checkToScanDevices();
        }
    }

    private void checkToScanDevices() {
        if (mBluetoothAdapter == null) {
            onError(R.string.ble_not_supported);
            finish();
            return;
        } else {
            foundDevice = false;
            scanLeDevice(true);
        }
    }

    private void scanLeDevice(final boolean enable) {
        mHandler = new Handler();
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(() -> {
                mBluetoothAdapter.stopLeScan(BaseConnectionActivity.this);
                if (!foundDevice) {
                    hideLoading();
                    showMessage("Không tìm thấy thiết bị", MessageType.ERROR, AlertType.TOAST);
                }
            }, SCAN_PERIOD);

            mBluetoothAdapter.startLeScan(BaseConnectionActivity.this);
        } else {
            mBluetoothAdapter.stopLeScan(BaseConnectionActivity.this);
        }
    }


    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            hideLoading();
            String action = intent.getAction();
            final Intent mIntent = intent;
            OGILVYLog.logTuan("BroadcastReceiver", action, BaseConnectionActivity.class);
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(() -> {
                    // TODO: 6/4/18 show group list light device
                    mState = UART_PROFILE_CONNECTED;
//                    Toast.makeText(getApplicationContext(), "COnnected", Toast.LENGTH_SHORT).show();

                });
            }
            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(() -> {
                    //setUiState();
                    // TODO: 6/4/18 hide group list light device
                    mState = AppConst.UART_PROFILE_DISCONNECTED;
                    handleBluetoothConnectAtStart();

//                    Toast.makeText(getApplicationContext(), "UART_PROFILE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                });
            }
            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
            }
            //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(() -> {
                    try {
                    } catch (Exception e) {
                    }
                });
            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
                //showMessage("Device doesn't support UART. Disconnecting");
                mService.disconnect();
            }
        }
    };

    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] bytes) {
        runOnUiThread(() -> addDevice(bluetoothDevice, rssi));
    }

    private void addDevice(BluetoothDevice device, int rssi) {
        OGILVYLog.logTuan("on add device", rssi, ListDeviceActivity.class);
        String deviceName = device.getName();
        if (!TextUtils.isEmpty(deviceName)) {
            boolean nameMatched = deviceName.startsWith("DW")
                    || deviceName.startsWith("BR") || deviceName.startsWith("DB");
//        devRssiValues.put(device.getAddress(), rssi);
            if (nameMatched) {
                mBluetoothAdapter.stopLeScan(BaseConnectionActivity.this);
                mainMAC = device.getAddress();
                Injections.updateMainMAC(mainMAC);
                foundDevice = true;
                if (mService != null) {
                    showLoading();
                    mService.connect(mainMAC);
                }
            }
        }
    }

    protected void handleBluetoothConnectAtStart() {
        // : 8/11/18 ask to open bluetooth if not ();
        if (!isShowingDialog) {
            if (mBluetoothAdapter == null) {
                // Device does not support Bluetooth
                // TODO: 8/11/18 error device not support bluetooth
            } else {
                if (mBluetoothAdapter.isEnabled()) {
                    // : 8/11/18 prompt to connect one device
                    isShowingDialog = true;
                    new BottomDialog.Builder(this)
                            .setTitle(getString(R.string.please_connect_a_device))
                            .setNegativeText(getString(R.string.no))
                            .setPositiveText(getString(R.string.connect))
                            .onNegative(bottomDialog -> {
                                isShowingDialog = false;
                                bottomDialog.dismiss();
                            })
                            .onPositive(bottomDialog -> {
                                // : 8/11/18 connect one device
                                isShowingDialog = false;
                                if (!mainMAC.isEmpty()) {
                                    if (mService.getmConnectionState() != UartService.STATE_CONNECTED) {
                                        mService.connect(mainMAC);
                                        showLoading();
                                    } else {
                                        hideLoading();
                                        mState = AppConst.UART_PROFILE_CONNECTED;
                                    }
                                } else {
                                    startToScanDevices();
                                }

                            })
                            .show();
                } else {
                    // : 8/11/18 prompt enable bluetooth
                    showMessage(R.string.please_open_bluetooth, MessageType.ERROR, AlertType.INFO_DIALOG);
                }
            }
        }

    }

    protected void sendCommand(@NonNull String command) throws UnsupportedEncodingException {
        if (mService != null && mState == UART_PROFILE_CONNECTED) {
            mService.writeRXCharacteristic(command.getBytes("UTF-8"));
        } else {
            handleBluetoothConnectAtStart();
        }
    }

    Handler handler;

    protected void sendCommandToGroupWithPrefix(String command, @NonNull Group group) {
        if (mService != null && mState == UART_PROFILE_CONNECTED) {
            handler.post(() -> {
                for (String item : group.getChildMacsAsGroup()) {
                    try {
                        String message = command + item;
                        sendCommand(message);
                        Thread.sleep(150);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            handleBluetoothConnectAtStart();
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mService = ((UartService.LocalBinder) iBinder).getService();
        mBound =true;
        if (!mService.initialize()) {
            finish();
        } else if (!mainMAC.isEmpty()) {
            if (mService.getmConnectionState() != UartService.STATE_CONNECTED) {
                mService.connect(mainMAC);
            } else {
                hideLoading();
                mState = AppConst.UART_PROFILE_CONNECTED;
            }
        } else if (shouldPromptConnectWhenNotFoundMac) {
            handleBluetoothConnectAtStart();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mService = null;
        mBound =false;
    }
}
