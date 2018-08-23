package com.example.kos.mysecrect.data;

import android.content.Context;

import com.dienquang.shop.apollo.data.model.Automation;
import com.dienquang.shop.apollo.data.model.Floor;
import com.dienquang.shop.apollo.data.model.Group;
import com.dienquang.shop.apollo.data.model.LightDevice;
import com.dienquang.shop.apollo.data.model.MapLocation;
import com.dienquang.shop.apollo.data.model.WeatherData;
import com.dienquang.shop.apollo.data.networking.ApiHelper;
import com.dienquang.shop.apollo.data.networking.AppApiHelper;
import com.dienquang.shop.apollo.data.prefs.AppPreferencesHelper;
import com.dienquang.shop.apollo.data.snappy.MySnappyImpl;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by tuan.giao on 11/9/2017.
 */

public class DataManagerImpl implements DataManager {

    private static DataManagerImpl mInstance;
    private ApiHelper mApiHelper;
    private Context mContext;
    private AppPreferencesHelper mAppPreferenceHelper;
    private MySnappyImpl mySnappy;
    private List<LightDevice> lightDeviceList;
    private List<Group> groupDevices;

    public DataManagerImpl(Context context) {
        this.mAppPreferenceHelper = AppPreferencesHelper.newInstance(context);
        this.mySnappy = MySnappyImpl.newInstance(context);
        this.lightDeviceList = mySnappy.getDeviceList();
        this.groupDevices = mySnappy.getDeviceGroup();
        this.mApiHelper = AppApiHelper.getInstance();
    }

    public static DataManagerImpl getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataManagerImpl(context);
        }
        return mInstance;
    }

    @Override
    public AppPreferencesHelper getPref() {
        return mAppPreferenceHelper;
    }

    @Override
    public List<LightDevice> getEncryptedDeviceList() {
        return lightDeviceList;
    }

    @Override
    public boolean addDevice(LightDevice device) {
        if (!lightDeviceList.contains(device)) {
            lightDeviceList.add(device);
            return mySnappy.addDeviceList(lightDeviceList);
        } else {
            return false;
        }
    }

    @Override
    public List<Group> getGroupList() {
        return groupDevices;
    }

    @Override
    public boolean addGroup(Group groupDevice) {
        if (groupDevices.isEmpty()) {
            groupDevice.setId(0);
        } else {
            groupDevice.setId(groupDevices.get(groupDevices.size() - 1).getId() + 1);
        }
        groupDevices.add(groupDevice);
        return mySnappy.addGroup(groupDevices);
    }

    @Override
    public boolean removeDevice(LightDevice device) {
        lightDeviceList.remove(device);
        return mySnappy.addDeviceList(lightDeviceList);
    }

    @Override
    public boolean removeGroup(Group group) {
        groupDevices.remove(group);
        return mySnappy.addGroup(groupDevices);
    }

    @Override
    public boolean updateGroup(Group groupDevice) {
        if (groupDevices.contains(groupDevice)) {
            groupDevices.set(groupDevices.indexOf(groupDevice), groupDevice);
            return mySnappy.addGroup(groupDevices);
        }
        return false;
    }

    @Override
    public String getSleepMode() {
        return mAppPreferenceHelper.getSleepMode();
    }

    @Override
    public void setSleepMode(String sleepCommand) {
        mAppPreferenceHelper.setSleepMode(sleepCommand);
    }

    @Override
    public String getRelaxMode() {
        return mAppPreferenceHelper.getRelaxMode();
    }

    @Override
    public void setRelaxMode(String sleepCommand) {
        mAppPreferenceHelper.setRelaxMode(sleepCommand);
    }

    @Override
    public String getEntetainmentMode() {
        return mAppPreferenceHelper.getEntetainmentMode();
    }

    @Override
    public void setEntetainmentMode(String sleepCommand) {
        mAppPreferenceHelper.setEntetainmentMode(sleepCommand);
    }

    @Override
    public String getGeomancyMode() {
        return mAppPreferenceHelper.getGeomancyMode();
    }

    @Override
    public void setGeomancyMode(String sleepCommand) {
        mAppPreferenceHelper.setGeomancyMode(sleepCommand);
    }

    @Override
    public String getHealthMode() {
        return mAppPreferenceHelper.getHealthMode();
    }

    @Override
    public void setHealthMode(String sleepCommand) {
        mAppPreferenceHelper.setHealthMode(sleepCommand);
    }

    @Override
    public List<LightDevice> getDeviceList() {
        return mySnappy.getDeviceList();
    }

    @Override
    public boolean addDeviceList(List<LightDevice> lightDevice) {
        return mySnappy.addDeviceList(lightDevice);
    }

    @Override
    public List<Group> getDeviceGroup() {
        return mySnappy.getDeviceGroup();
    }

    @Override
    public boolean addGroup(List<Group> group) {
        return mySnappy.addGroup(group);
    }

    @Override
    public void setLocation(MapLocation location) {
        mySnappy.setLocation(location);
    }


    @Override
    public MapLocation getLocation() {
        return mySnappy.getLocation();
    }


    @Override
    public Observable<WeatherData> getWeatherData(String lat, String longt) {
        return mApiHelper.getWeatherData(lat, longt);
    }

    public boolean addFloor(Floor floor) {
        return mySnappy.updateFloor(floor);
    }

    @Override
    public List<Floor> getFloors() {
        return mySnappy.getFloorList();
    }

    @Override
    public boolean updateFloor(Floor floor) {
        return mySnappy.updateFloor(floor);
    }

    @Override
    public Floor getFloor(int floorId) {
        return mySnappy.getFloor(floorId);
    }

    @Override
    public boolean removeFloor(int floorId) {
        return mySnappy.removeFloor(floorId);
    }

    @Override
    public boolean addAutomation(Automation automation) {
        return mySnappy.updateAutomation(automation);
    }

    @Override
    public List<Automation> getAutomations() {
        return mySnappy.getAutomationList();
    }

    @Override
    public Automation getAutomation(int automationId) {
            return mySnappy.getAutomation(automationId);
    }

    @Override
    public boolean removeAutomation(int automationId) {
        return mySnappy.removetAutomation(automationId);
    }

    @Override
    public boolean updateAutomation(Automation automation) {
        return mySnappy.updateAutomation(automation);
    }
}
