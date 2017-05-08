package com.serasiautoraya.slimobiledrivertracking.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;

/**
 * Created by Randi Dwi Nandra on 06/12/2016.
 */
public class FatigueInterviewActivity extends AppCompatActivity {
    private RadioGroup mRgQuest1, mRgQuest2, mRgQuest3, mRgQuest4, mRgQuest5;
    private Button mFiSubmit;
    private boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignActionBar();
        assignView();
    }

    private void assignView() {
        result = true;
        mRgQuest1 = (RadioGroup) findViewById(R.id.rg_fi_quest1);
        mRgQuest2 = (RadioGroup) findViewById(R.id.rg_fi_quest2);
        mRgQuest3 = (RadioGroup) findViewById(R.id.rg_fi_quest3);
        mRgQuest4 = (RadioGroup) findViewById(R.id.rg_fi_quest4);
        mRgQuest5 = (RadioGroup) findViewById(R.id.rg_fi_quest5);
        mFiSubmit = (Button) findViewById(R.id.btn_fi_submit);

        mFiSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                    (mRgQuest1.getCheckedRadioButtonId() == R.id.rb_fi_quest1_tidak) ||
                    (mRgQuest2.getCheckedRadioButtonId() == R.id.rb_fi_quest2_tidak) ||
                    (mRgQuest3.getCheckedRadioButtonId() == R.id.rb_fi_quest3_tidak) ||
                    (mRgQuest4.getCheckedRadioButtonId() == R.id.rb_fi_quest4_tidak) ||
                    (mRgQuest5.getCheckedRadioButtonId() == R.id.rb_fi_quest5_tidak)){

                    result = false;
                }else{
                    result = true;
                }

                String tempHasilFatigue = "Siap Jalan";
                if(result == false){
                    tempHasilFatigue = "Kelelahan";
                }

                Dialog dialogConfirmation = createDialog2Buttons();
                dialogConfirmation.show();

                TextView textViewAns1 = (TextView) dialogConfirmation.findViewById(R.id.dialog_fi_ans1);
                TextView textViewAns2 = (TextView) dialogConfirmation.findViewById(R.id.dialog_fi_ans2);
                TextView textViewAns3 = (TextView) dialogConfirmation.findViewById(R.id.dialog_fi_ans3);
                TextView textViewAns4 = (TextView) dialogConfirmation.findViewById(R.id.dialog_fi_ans4);
                TextView textViewAns5 = (TextView) dialogConfirmation.findViewById(R.id.dialog_fi_ans5);

                textViewAns1.setText(((RadioButton)findViewById(mRgQuest1.getCheckedRadioButtonId())).getText().toString());
                textViewAns2.setText(((RadioButton)findViewById(mRgQuest2.getCheckedRadioButtonId())).getText().toString());
                textViewAns3.setText(((RadioButton)findViewById(mRgQuest3.getCheckedRadioButtonId())).getText().toString());
                textViewAns4.setText(((RadioButton)findViewById(mRgQuest4.getCheckedRadioButtonId())).getText().toString());
                textViewAns5.setText(((RadioButton)findViewById(mRgQuest5.getCheckedRadioButtonId())).getText().toString());


//                HelperUtil.showConfirmationAlertDialog("Hasil interview: " + tempHasilFatigue, FatigueInterviewActivity.this, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });

            }
        });
    }

    private void assignActionBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fatigue_interview);
        String title = getIntent().getStringExtra(HelperKey.EXTRA_KEY_TITLE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private Dialog createDialog2Buttons(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FatigueInterviewActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_fatigue_confirmation, null))
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        submitResult();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private void submitResult() {
        
    }

}
