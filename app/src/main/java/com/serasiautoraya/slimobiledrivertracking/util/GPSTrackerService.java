package com.serasiautoraya.slimobiledrivertracking.util;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.serasiautoraya.slimobiledrivertracking.activity.LoginActivity;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Randi Dwi Nandra on 09/02/2017.
 */
public class GPSTrackerService extends Service implements LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static GPSTrackerService instance = null;
    private static final int UPDATE_INTERVAL = 1000 * 10; //in milliseconds
    private static final int UPDATE_INTERVAL_FASTEST = 1000 * 5; //in milliseconds
    private Geocoder mGeocoder;
    private GoogleApiClient mGoogleApiClient = null;
    private LocationRequest mLocationRequest;
    private Location sLocation;
    private Context mTemporaryContext;

    /*
    * TODO Delete this
    * */
    private static String TAG = "GPSTrackerService";

    public GPSTrackerService(Context context) {
        Log.d(TAG, "1: GPSTrackerService");
        this.mTemporaryContext = context;
        Log.d(TAG, "2: GPSTrackerService");
        initGoogleApiClient(context);
        Log.d(TAG, "3: GPSTrackerService");
    }

    public GPSTrackerService(){

    }

    @Override
    public void onCreate() {
//        getLocationManager(AppInit.getAppContext());
        Log.d(TAG, "CREATED SERVICES");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getLocationManager(AppInit.getAppContext());
        Log.d(TAG, "STARTED SERVICES");
        return START_STICKY;
    }

    private void initGoogleApiClient(Context context) {
        Log.d(TAG, "1: initGoogleApiClient");
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(UPDATE_INTERVAL_FASTEST);
        this.mGeocoder = new Geocoder(context, Locale.getDefault());

        if (mGoogleApiClient == null) {
            Log.d(TAG, "2: initGoogleApiClient");
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Log.d(TAG, "3: initGoogleApiClient");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            Log.d(TAG, "4: initGoogleApiClient");
        }

        Log.d(TAG, "5: initGoogleApiClient");
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "6: initGoogleApiClient");
            return;
        }
    }

    public void connectGoogleAPIClient(){
        if (mGoogleApiClient != null) {
            if(!mGoogleApiClient.isConnected()){
                mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Singleton implementation
     * @return
     */
    public static GPSTrackerService getLocationManager(Context context) {
        Log.d(TAG, "1: getLocationManager");
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        if(result != ConnectionResult.SUCCESS) {
            Log.d(TAG, "2: getLocationManager");
            if(googleAPI.isUserResolvableError(result)) {
                Log.d(TAG, "3: getLocationManager");
                googleAPI.getErrorDialog((LoginActivity) context, result, 9001).show();
            }
        }
        Log.d(TAG, "4: getLocationManager");
        if (instance == null) {
            Log.d(TAG, "5: getLocationManager");
            instance = new GPSTrackerService(context);
            Log.d(TAG, "6: getLocationManager");
        }
        return instance;
    }

    public boolean isGPSEnabled(){
        LocationManager lm = (LocationManager) mTemporaryContext.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled( LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "1: onConnected");
        if (ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "2: onConnected");
            return;
        }
        Log.d(TAG, "3: onConnected");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "4: onConnected");
        sLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.d(TAG, "5: onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "1: onConnectionSuspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Loc updated: apsopaso");
        if(location != null){
            this.sLocation = location;
            Context mContext = AppInit.getAppContext();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(Calendar.getInstance().getTime());
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LAT, sLocation.getLatitude()+"");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LONG, sLocation.getLongitude()+"");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_ADDRESS, getLastLocationName()+"");
            SharedPrefsUtil.apply(mContext, "CUR_TIME", time);
            String latitude = SharedPrefsUtil.getString(mContext, HelperKey.KEY_LOC_LAT, "");
            String longitude = SharedPrefsUtil.getString(mContext, HelperKey.KEY_LOC_LONG, "");
            String waktu = SharedPrefsUtil.getString(mContext, "CUR_TIME", "");

            Log.d(TAG, "Loc updated: "+waktu+" oo "+latitude+" - "+sLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("LOHIN_TAG", "connect failed: "+connectionResult.getErrorCode());
        if(connectionResult.getErrorCode() != ConnectionResult.SUCCESS) {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            if(googleAPI.isUserResolvableError(connectionResult.getErrorCode())) {
                googleAPI.getErrorDialog((LoginActivity) mTemporaryContext, connectionResult.getErrorCode(), 9001).show();
            }
        }
    }

    private void updateLocation(){
        if (ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(mTemporaryContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        sLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public Location getLastLocation(){
        updateLocation();
        if(sLocation != null){
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Context mContext = AppInit.getAppContext();
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LAT, sLocation.getLatitude()+"");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_LONG, sLocation.getLongitude()+"");
            SharedPrefsUtil.apply(mContext, HelperKey.KEY_LOC_ADDRESS, getLastLocationName()+"");
            return sLocation;
        }else {
            return null;
        }
    }

    public String getLastLocationName(){
        String address = "";
        List<Address> addresses;
        try {
            addresses = mGeocoder.getFromLocation(sLocation.getLatitude(),
                    sLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                for (int i=0;i<addresses.get(0).getMaxAddressLineIndex();i++){
                    address += "\n"+addresses.get(0).getAddressLine(i)+" ";
                }
            }
        } catch (IOException e) {
            address = "err";
            e.printStackTrace();
        }
        return address;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /*TODO
    * TODO: Update location hanya dilakukan ketika ada journey (start order), nyalakan service (apabila belum nyala)
    * * TODO: Matikan service apabila end order (apabila belum mati & tidak ada order lain yg sedang berjalan)
    *
    * */
}
