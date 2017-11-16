package com.kryvuy.weatherapp.position;

import android.Manifest.permission;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.start.Constant;

/**
 * Created by Volodymyr on 11/15/2017.
 */

public class GPSTracker extends Service implements LocationListener {
    private Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public boolean canGetLocation = false;

    Location mLocation;
    double latitude;
    double longitude;

    protected LocationManager mLocationManager;


    public  GPSTracker(){

    }

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }


    public void getLocation() {

        try {
            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            //getting GPS status
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //getting network status
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                mLocation.setLatitude(49.8350147);
                mLocation.setLongitude(24.0075948);
                //no network privider is enabled
            }else{
                this.canGetLocation = true;
                //first get Location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(mContext, permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        mLocationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                Constant.MIN_TIME_BW_GPS_UPDATE,
                                Constant.MIN_DISTANCE_CHANGE_GPS_FOR_UPDATE,
                                this);
                        Log.d(MainActivity.LOG_TAG, "getLocation: Network Provide");
                        if (mLocationManager != null) {
                            mLocation = mLocationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (mLocation != null) {
                                latitude = mLocation.getLatitude();
                                longitude = mLocation.getLongitude();
                            }
                        }
                    }
                }
                    //if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (mLocation == null) {
                            mLocationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    Constant.MIN_TIME_BW_GPS_UPDATE,
                                    Constant.MIN_DISTANCE_CHANGE_GPS_FOR_UPDATE,
                                    this);
                            Log.d(MainActivity.LOG_TAG, "getLocation: GPS get Location");
                            if (mLocationManager != null) {
                                mLocation = mLocationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (mLocation != null) {
                                    latitude = mLocation.getLatitude();
                                    longitude = mLocation.getLongitude();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public double getLatitude(){
        if (mLocation != null){
            latitude = mLocation.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if (mLocation != null){
            longitude = mLocation.getLongitude();
        }
        return longitude;
    }

    public void stopUsingGPS(){
        if (mLocation != null){
            mLocationManager.removeUpdates(GPSTracker.this);
        }
    }
}
