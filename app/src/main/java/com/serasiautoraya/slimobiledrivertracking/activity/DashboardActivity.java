package com.serasiautoraya.slimobiledrivertracking.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.fragment.AbsenceRequestFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.CicoRequestFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.PlanActiveOrdersFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.RequestHistoryFragment;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.listener.TextViewTouchListener;
import com.serasiautoraya.slimobiledrivertracking.util.GPSTracker;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;
import com.serasiautoraya.slimobiledrivertracking.util.SharedPrefsUtil;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private Handler mHandler;
    private int fragmentSelectedID;

    private TextView mTextViewNavNama, mTextViewNavPosisi;
    private ImageView mImageViewNavImg;
    private View mNavHeader;
    private ScheduledExecutorService scheduleTaskExecutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LocationServiceUtil.getLocationManager(DashboardActivity.this);
        HelperBridge.gps = new GPSTracker(this);
        Intent LocationService = new Intent(this, GPSTracker.class);
        this.startService(LocationService);

        scheduleTaskExecutor = Executors.newScheduledThreadPool(2);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                HelperBridge.gps.getLocation();
            }
        }, 0, 10, TimeUnit.SECONDS);

        setContentView(R.layout.activity_dashboard);
        assignNav();
        assignFragment();
        checkOutstandingOrder();
    }

    private void checkOutstandingOrder() {
        Dialog dialog = HelperUtil.getConfirmOrderDialog(DashboardActivity.this);
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationServiceUtil.getLocationManager(DashboardActivity.this).connectGoogleAPIClient();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitApp();
//            super.onBackPressed();
        }
    }

    private void exitApp() {
        HelperUtil.showConfirmationAlertDialog(getResources().getString(R.string.warn_msg_exit),
                DashboardActivity.this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        this.fragmentSelectedID = id;
        if(id != R.id.nav_change_pass && id != R.id.nav_logout){
            assignFragment();
        }else{
            goToActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Non-overide methods
    private void assignNav(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        * TODO CHANGE MENU
        * */
//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_absence_request).setVisible(false);
//        menu.findItem(R.id.nav_absence_request).setEnabled(false);

        mHandler = new Handler();
        navigationView.setCheckedItem(R.id.nav_active_order);

        mNavHeader = navigationView.getHeaderView(0);

        mImageViewNavImg = (ImageView) mNavHeader.findViewById(R.id.nav_header_img);
        mTextViewNavNama = (TextView) mNavHeader.findViewById(R.id.nav_header_name);
        mTextViewNavPosisi = (TextView) mNavHeader.findViewById(R.id.nav_header_posisi);

        mImageViewNavImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });
        mTextViewNavNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });
        mTextViewNavPosisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });

        mTextViewNavNama.setOnTouchListener(new TextViewTouchListener(this, R.color.colorPrimary, R.color.colorTextIcon));
        mTextViewNavPosisi.setOnTouchListener(new TextViewTouchListener(this, R.color.colorPrimary, R.color.colorDivider));
        mImageViewNavImg.setOnTouchListener(new View.OnTouchListener() {
            private Rect rect;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    mImageViewNavImg.setColorFilter(Color.argb(90, 255, 87 ,34));
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    mImageViewNavImg.setColorFilter(Color.argb(0, 0, 0, 0));
                }
                return false;
            }
        });

        if(HelperBridge.MODEL_LOGIN_DATA != null){
            mTextViewNavNama.setText(HelperBridge.MODEL_LOGIN_DATA.getFullName());
            String position = HelperBridge.MODEL_LOGIN_DATA.getPosition() == "Driver"? "Transporter "+HelperBridge.MODEL_LOGIN_DATA.getCompanyName(): HelperBridge.MODEL_LOGIN_DATA.getPosition()+" "+HelperBridge.MODEL_LOGIN_DATA.getCompanyName();
            mTextViewNavPosisi.setText(position);
            if(HelperBridge.MODEL_LOGIN_DATA.getPhotoFront() != null){
                if(!HelperBridge.MODEL_LOGIN_DATA.getPhotoFront().equalsIgnoreCase("")){
                    Picasso.with(DashboardActivity.this).load(HelperBridge.MODEL_LOGIN_DATA.getPhotoFront()).into(mImageViewNavImg);
                }
            }
        }
    }

    private void goToProfile(){
        HelperUtil.goToActivity(DashboardActivity.this, ProfileActivity.class, HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_PROFILE);
    }

    private void assignFragment(){
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getActiveFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_main_content, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    private Fragment getActiveFragment() {
        switch (fragmentSelectedID) {
            case R.id.nav_active_order:
                PlanActiveOrdersFragment planActiveOrdersFragment = new PlanActiveOrdersFragment();
                return planActiveOrdersFragment;
            case R.id.nav_cico_request:
                CicoRequestFragment cicoRequestFragment = new CicoRequestFragment();
                return cicoRequestFragment;
            case R.id.nav_absence_request:
                AbsenceRequestFragment absenceRequestFragment = new AbsenceRequestFragment();
                return absenceRequestFragment;
            case R.id.nav_attendance_history:
                RequestHistoryFragment attendanceHistoryFragment = new RequestHistoryFragment();
                return attendanceHistoryFragment;
//            case R.id.nav_order_history:
//                OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
//                return orderHistoryFragment;
            default:
//                return new ActiveOrderFragment();
                  return new PlanActiveOrdersFragment();
        }
    }

    private void goToActivity(){
        if(fragmentSelectedID == R.id.nav_change_pass){
            HelperUtil.goToActivity(DashboardActivity.this, ChangePasswordActivity.class, HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_CHANGE_PASS);
        }else{
            logout();
        }
    }

    private void logout() {
        SharedPrefsUtil.clearAll(this);
        Intent goLoginActivity = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(goLoginActivity);
        DashboardActivity.this.finish();
        HelperUtil.showSimpleToast("Berhasil keluar", DashboardActivity.this);
    }

}
