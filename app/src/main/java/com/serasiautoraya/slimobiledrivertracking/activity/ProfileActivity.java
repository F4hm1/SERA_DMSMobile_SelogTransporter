package com.serasiautoraya.slimobiledrivertracking.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.model.VolleyUtil;

/**
 * Created by Randi Dwi Nandra on 07/12/2016.
 */
public class ProfileActivity extends AppCompatActivity{

    private RequestQueue mqueue;

    //XML Element
    private TextView textProfileNameFp;
    private TextView textProfilePosisiFp;
    private TextView textProfileNoequipment;
    private TextView textProfileNrp;
    private TextView textProfileFullname;
    private TextView textProfileTgllahir;
    private TextView textProfileJeniskelamin;
    private TextView textProfileKebangsaan;
    private TextView textProfileStatusmarital;
    private TextView textProfileAgama;
    private TextView textProfilePersonalsubarea;
    private TextView textProfileJabatan;
    private TextView textProfileEmail;
    private TextView textProfileUsercostumer;
    private TextView textProfileDoo;
    private ImageButton imgProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignActionBar();
        assignView();
    }

    private void assignView() {
        textProfileNameFp = (TextView) findViewById(R.id.text_profile_name_fp);
        textProfilePosisiFp = (TextView) findViewById(R.id.text_profile_posisi_fp);
        textProfileNoequipment = (TextView) findViewById(R.id.text_profile_noequipment);
        textProfileNrp = (TextView) findViewById(R.id.text_profile_nrp);
        textProfileFullname  = (TextView) findViewById(R.id.text_profile_fullname);
        textProfileTgllahir = (TextView) findViewById(R.id.text_profile_tgllahir);
        textProfileJeniskelamin = (TextView) findViewById(R.id.text_profile_jeniskelamin);
        textProfileKebangsaan = (TextView) findViewById(R.id.text_profile_kebangsaan);
        textProfileStatusmarital = (TextView) findViewById(R.id.text_profile_statusmarital);
        textProfileAgama = (TextView) findViewById(R.id.text_profile_agama);
        textProfilePersonalsubarea = (TextView) findViewById(R.id.text_profile_personalsubarea);
        textProfileJabatan = (TextView) findViewById(R.id.text_profile_jabatan);
        textProfileEmail = (TextView) findViewById(R.id.text_profile_email);
        textProfileUsercostumer = (TextView) findViewById(R.id.text_profile_usercostumer);
        textProfileDoo = (TextView) findViewById(R.id.text_profile_doo);
        imgProfilePhoto = (ImageButton) findViewById(R.id.img_profile_photo);

        textProfileNameFp.setKeyListener(null);
        textProfilePosisiFp.setKeyListener(null);
        textProfileNoequipment.setKeyListener(null);
        textProfileNrp.setKeyListener(null);
        textProfileFullname.setKeyListener(null);
        textProfileTgllahir.setKeyListener(null);
        textProfileJeniskelamin.setKeyListener(null);
        textProfileKebangsaan.setKeyListener(null);
        textProfileStatusmarital.setKeyListener(null);
        textProfileAgama.setKeyListener(null);
        textProfilePersonalsubarea.setKeyListener(null);
        textProfileJabatan.setKeyListener(null);
        textProfileEmail.setKeyListener(null);
        textProfileUsercostumer.setKeyListener(null);
        textProfileDoo.setKeyListener(null);

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
}
