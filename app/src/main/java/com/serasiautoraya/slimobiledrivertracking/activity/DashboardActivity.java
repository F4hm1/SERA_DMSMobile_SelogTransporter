package com.serasiautoraya.slimobiledrivertracking.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.fragment.AbsenceRequestFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.AttendanceHistoryFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.CicoRequestFragment;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private Handler mHandler;
    private int fragmentSelectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SET no title bro
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LocationServiceUtil.getLocationManager(DashboardActivity.this);

        setContentView(R.layout.activity_dashboard);
        assignNav();
        assignFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        this.fragmentSelectedID = id;

//        if(id != R.id.nav_change_pass && id != R.id.nav_logout && id != R.id.nav_temp_confirmation){
//            assignFragment();
//        }else{
//            if(id != R.id.nav_temp_confirmation){
//                goToActivity();
//            }else{
//                createDialog2Buttons();
//                dialog.show();
//            }
//        }

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

        mHandler = new Handler();
        navigationView.setCheckedItem(R.id.nav_cico_request);
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
//            case R.id.nav_active_order:
//                ActiveOrderFragment activeOrderFragment = new ActiveOrderFragment();
//                return activeOrderFragment;
            case R.id.nav_cico_request:
                CicoRequestFragment cicoRequestFragment = new CicoRequestFragment();
                return cicoRequestFragment;
            case R.id.nav_absence_request:
                AbsenceRequestFragment absenceRequestFragment = new AbsenceRequestFragment();
                return absenceRequestFragment;
            case R.id.nav_attendance_history:
                AttendanceHistoryFragment attendanceHistoryFragment = new AttendanceHistoryFragment();
                return attendanceHistoryFragment;
//            case R.id.nav_order_history:
//                OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
//                return orderHistoryFragment;
            default:
//                return new ActiveOrderFragment();
                  return new CicoRequestFragment();
        }
    }

    private void goToActivity(){
//            Toast.makeText(this, "Activity goo", Toast.LENGTH_LONG).show();
        if(fragmentSelectedID == R.id.nav_change_pass){
//            Intent goLoginActivity = new Intent(DashboardActivity.this, ChangePasswordActivity.class);
//            startActivity(goLoginActivity);
//            DashboardActivity.this.finish();
            HelperUtil.goToActivity(DashboardActivity.this, ChangePasswordActivity.class, HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_CHANGE_PASS);
        }else{
            Toast.makeText(this, "Logouting", Toast.LENGTH_LONG).show();
        }
    }


    private Dialog dialog;
    private void createDialog2Buttons(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_confirmation, null))
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        dialog = builder.create();
    }

}
