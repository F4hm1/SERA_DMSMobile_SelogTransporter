package com.serasiautoraya.slimobiledrivertracking.MVP.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.MVP.CiCo.CiCoFragment;
import com.serasiautoraya.slimobiledrivertracking.MVP.Login.LoginActivity;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.activity.ChangePasswordActivity;
import com.serasiautoraya.slimobiledrivertracking.fragment.AbsenceRequestFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.CicoRequestFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.PlanActiveOrdersFragment;
import com.serasiautoraya.slimobiledrivertracking.fragment.RequestHistoryFragment;
import com.serasiautoraya.slimobiledrivertracking.util.LocationServiceUtil;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 27/03/2017.
 */

public class DashboardActivity extends TiActivity<DashboardPresenter, DashboardView> implements DashboardView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view) NavigationView  mNavigationView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private TextView mTextViewNavNama;
    private TextView mTextViewNavPosisi;
    private ImageView mImageViewNavImg;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;

    private Handler mHandler;
    private int mFragmentSelectedID;
    private View mNavHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
    }

    @Override
    public void initialize() {
        this.initializeNavigationView();
        LocationServiceUtil.getLocationManager(DashboardActivity.this);
    }

    @Override
    public void toggleLoading(boolean isLoading) {

    }

    @Override
    public void showToast(String text) {

    }

    @Override
    public void showStandardDialog(String message, String Title) {

    }

    @NonNull
    @Override
    public DashboardPresenter providePresenter() {
        return new DashboardPresenter();
    }


    /*
    * Non standard method (available only for this class but still override from View interface)
    * */

    @Override
    public void initializeMenuAccess() {
        /*
        * TODO give menu access (hide or show menu item) based on module access code
        * */
    }

    @Override
    public void changeFragment(final Fragment targetFragment) {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = targetFragment;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.nav_main_content, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }

    @Override
    public void changeActivity(Class targetActivity) {
        Intent intent = new Intent(DashboardActivity.this, targetActivity);
        startActivity(intent);
    }

    @Override
    public Fragment getActiveFragment(int idNavItem) {
        switch (idNavItem) {
            case R.id.nav_active_order:
                PlanActiveOrdersFragment planActiveOrdersFragment = new PlanActiveOrdersFragment();
                return planActiveOrdersFragment;
            case R.id.nav_cico_request:
                CiCoFragment ciCoFragment = new CiCoFragment();
                return ciCoFragment;
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
                return new PlanActiveOrdersFragment();
        }
    }

    @Override
    public Class getTargetActivityClass(int idNavItem) {
        switch (idNavItem) {
            case R.id.nav_change_pass:
                return ChangePasswordActivity.class;
            default:
                return ChangePasswordActivity.class;
        }
    }

    @Override
    public void exitApplication() {

    }

    @Override
    public void logout() {

    }


    /*
    * Below is override methods from super class (AppCompatActivity)
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
        this.mFragmentSelectedID = id;
        if(id != R.id.nav_change_pass && id != R.id.nav_logout){
            getPresenter().onNavigationItemSelectedForFragment(id);
        }else{
            getPresenter().onNavigationItemSelectedForActivity(id);
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * Non overriding methods
    * */
    public void initializeNavigationView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mHandler = new Handler();
        mNavigationView.setCheckedItem(R.id.nav_active_order);

        mNavHeader = mNavigationView.getHeaderView(0);

        mImageViewNavImg = (ImageView) mNavHeader.findViewById(R.id.nav_header_img);
        mTextViewNavNama = (TextView) mNavHeader.findViewById(R.id.nav_header_name);
        mTextViewNavPosisi = (TextView) mNavHeader.findViewById(R.id.nav_header_posisi);
    }
}
