package com.serasiautoraya.slimobiledrivertracking.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Randi Dwi Nandra on 07/12/2016.
 */
public class ProfileActivity extends AppCompatActivity{

    private RequestQueue mqueue;

    private TextView textProfileNameFp;
    private TextView textProfilePosisiFp;
    private TextView textProfileCompanyFp;
    private TextView textProfilePoolNameFp;
    private TextView textProfileNrp;
    private TextView textProfileFullname;
    private TextView textProfileTraining;
    private TextView textProfileUsercostumer;
    private TextView textProfileDoo;
    private TextView textProfileKTPExp;
    private TextView textProfileSIMExp;
    private ImageView imgProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignActionBar();
        assignView();
        assignContent();
    }

    private void assignContent() {
        Picasso.with(ProfileActivity.this).load(HelperBridge.MODEL_LOGIN_DATA.getPhotoFront()).into(imgProfilePhoto);
        textProfileNameFp.setText(HelperBridge.MODEL_LOGIN_DATA.getFullName());
        String position = HelperBridge.MODEL_LOGIN_DATA.getPosition() == "Driver"? "Transporter": HelperBridge.MODEL_LOGIN_DATA.getPosition();
        textProfilePosisiFp.setText(position);
        textProfileCompanyFp.setText(HelperBridge.MODEL_LOGIN_DATA.getCompanyName());
        textProfilePoolNameFp.setText("Pool "+HelperBridge.MODEL_LOGIN_DATA.getPoolName());
        textProfileNrp.setText(HelperBridge.MODEL_LOGIN_DATA.getCode());
        textProfileFullname.setText(HelperBridge.MODEL_LOGIN_DATA.getFullName());
        textProfileTraining.setText(HelperBridge.MODEL_LOGIN_DATA.getPosition());
        textProfileUsercostumer.setText(HelperBridge.MODEL_LOGIN_DATA.getFullNamelLvl_1());
        textProfileDoo.setText(HelperBridge.MODEL_LOGIN_DATA.getFullNamelLvl_2());
        textProfileKTPExp.setText(HelperBridge.MODEL_LOGIN_DATA.getkTPEndDate());
        textProfileSIMExp.setText(HelperBridge.MODEL_LOGIN_DATA.getsIMEndDate());
    }

    private void assignView() {
        textProfileNameFp = (TextView) findViewById(R.id.text_profile_name_fp);
        textProfilePosisiFp = (TextView) findViewById(R.id.text_profile_posisi_fp);
        textProfileCompanyFp = (TextView) findViewById(R.id.text_profile_company_fp);
        textProfilePoolNameFp = (TextView) findViewById(R.id.text_profile_poolname_fp);
        textProfileNrp = (TextView) findViewById(R.id.text_profile_nrp);
        textProfileFullname  = (TextView) findViewById(R.id.text_profile_fullname);
        textProfileTraining = (TextView) findViewById(R.id.text_profile_training);
        textProfileUsercostumer = (TextView) findViewById(R.id.text_profile_usercostumer);
        textProfileDoo = (TextView) findViewById(R.id.text_profile_doo);
        textProfileKTPExp = (TextView) findViewById(R.id.text_profile_ktpexp);
        textProfileSIMExp = (TextView) findViewById(R.id.text_profile_simexp);

        imgProfilePhoto = (ImageView) findViewById(R.id.img_profile_photo);

        textProfileNameFp.setKeyListener(null);
        textProfilePosisiFp.setKeyListener(null);
        textProfileNrp.setKeyListener(null);
        textProfileFullname.setKeyListener(null);
        textProfileTraining.setKeyListener(null);
        textProfileUsercostumer.setKeyListener(null);
        textProfileDoo.setKeyListener(null);
        textProfileKTPExp.setKeyListener(null);
        textProfileSIMExp.setKeyListener(null);
        textProfileCompanyFp.setKeyListener(null);
        textProfilePoolNameFp.setKeyListener(null);

        mqueue = VolleyUtil.getRequestQueue();
    }

    private void assignActionBar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        String title = getIntent().getStringExtra(HelperKey.EXTRA_KEY_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
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
}
