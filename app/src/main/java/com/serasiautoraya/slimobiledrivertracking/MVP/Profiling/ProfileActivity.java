package com.serasiautoraya.slimobiledrivertracking.MVP.Profiling;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.squareup.picasso.Picasso;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Randi Dwi Nandra on 10/04/2017.
 */

public class ProfileActivity extends TiActivity<ProfilePresenter, ProfileView> implements ProfileView {

    @BindView(R.id.text_profile_name_fp) TextView mTvProfileNameFp;
    @BindView(R.id.text_profile_posisi_fp) TextView mTvProfilePosisiFp;
    @BindView(R.id.text_profile_company_fp) TextView mTvProfileCompanyFp;
    @BindView(R.id.text_profile_poolname_fp) TextView mTvProfilePoolNameFp;
    @BindView(R.id.text_profile_nrp) TextView mTvProfileNrp;
    @BindView(R.id.text_profile_fullname) TextView mTvProfileFullname;
//        @BindView(R.id.text_profile_training) TextView mTvProfileTraining;
    @BindView(R.id.text_profile_usercostumer) TextView mTvProfileUsercostumer;
    @BindView(R.id.text_profile_doo) TextView mTvProfileDoo;
    @BindView(R.id.text_profile_ktpexp) TextView mTvProfileKTPExp;
    @BindView(R.id.text_profile_simexp) TextView mTvProfileSIMExp;
    @BindView(R.id.text_title_sim) TextView mTvTitleSIMExp;
    @BindView(R.id.img_profile_photo) ImageView mIvProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }


    @Override
    public void initialize() {
        this.initializeActionBar();
        this.initiailzeTvListener();
        getPresenter().loadProfileData();
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
    public ProfilePresenter providePresenter() {
        return new ProfilePresenter();
    }

    private void initializeActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Profile");
    }

    private void initiailzeTvListener() {
        mTvProfileNameFp.setKeyListener(null);
        mTvProfilePosisiFp.setKeyListener(null);
        mTvProfileNrp.setKeyListener(null);
        mTvProfileFullname.setKeyListener(null);
//        mTvProfileTraining.setKeyListener(null);
        mTvProfileUsercostumer.setKeyListener(null);
        mTvProfileDoo.setKeyListener(null);
        mTvProfileKTPExp.setKeyListener(null);
        mTvProfileSIMExp.setKeyListener(null);
        mTvProfileCompanyFp.setKeyListener(null);
        mTvProfilePoolNameFp.setKeyListener(null);
    }

    @Override
    public void setProfileContent(String nameFp, String posisiFp, String companyFp, String poolNameFp, String nrp, String fullname, String userCostumer, String doo, String kTPExp, String sIMExp, String sIMType) {
        mTvProfileNameFp.setText(nameFp);
        mTvProfilePosisiFp.setText(posisiFp);
        mTvProfileCompanyFp.setText(companyFp);
        mTvProfilePoolNameFp.setText(poolNameFp);
        mTvProfileNrp.setText(nrp);
        mTvProfileFullname.setText(fullname);
        mTvProfileUsercostumer.setText(userCostumer);
        mTvProfileDoo.setText(doo);
        mTvProfileKTPExp.setText(kTPExp);
        mTvProfileSIMExp.setText(sIMExp);
        mTvTitleSIMExp.setText(sIMType);
    }

    @Override
    public void setProfilePhoto(String url) {
        Picasso.with(ProfileActivity.this).load(url).into(mIvProfilePhoto);
    }
}
