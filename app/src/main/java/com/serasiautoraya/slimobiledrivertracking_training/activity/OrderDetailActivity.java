package com.serasiautoraya.slimobiledrivertracking_training.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.adapter.OrderSingleList;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class OrderDetailActivity extends AppCompatActivity {

    private OrderSingleList mSelectedOrder;
    private Button mBtnLoading, mBtnUnloading, mBtnStopOrder, mBtnEndOrder, mBtnCheckpoint;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignActionBar();
        assignView();
    }

    private void assignView() {
        mSelectedOrder = HelperBridge.ORDER_CLICKED;
        TextView textView1 = (TextView) findViewById(R.id.order_title_code);
        textView1.setText(mSelectedOrder.getmSingleListCode());

        TextView textView2 = (TextView) findViewById(R.id.order_title_dest);
        textView2.setText(mSelectedOrder.getmSingleListDest());

        TextView textView3 = (TextView) findViewById(R.id.order_title_activityname);
        textView3.setText(mSelectedOrder.getmSingleListHub());

        mBtnLoading = (Button) findViewById(R.id.btn_loading);
        mBtnUnloading = (Button) findViewById(R.id.btn_unloading);
        mBtnStopOrder = (Button) findViewById(R.id.btn_stoporder);
        mBtnCheckpoint = (Button) findViewById(R.id.btn_checkpoint);
        mBtnEndOrder = (Button) findViewById(R.id.btn_endorder);

        mBtnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProveActivity = new Intent(OrderDetailActivity.this, ProveActivity.class);
                goToProveActivity.putExtra(HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_LOADING);
                startActivity(goToProveActivity);
            }
        });

        mBtnUnloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProveActivity = new Intent(OrderDetailActivity.this, ProveActivity.class);
                goToProveActivity.putExtra(HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_UNLOADING);
                startActivity(goToProveActivity);
            }
        });

        mBtnStopOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog confDialog = createDialogDetail();
                confDialog.show();
            }
        });

        mBtnCheckpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProveActivity = new Intent(OrderDetailActivity.this, ProveActivity.class);
                goToProveActivity.putExtra(HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_CHECKPOINT);
                startActivity(goToProveActivity);
            }
        });

        mBtnEndOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProveActivity = new Intent(OrderDetailActivity.this, ProveActivity.class);
                goToProveActivity.putExtra(HelperKey.EXTRA_KEY_TITLE, HelperKey.TITLE_ACTIVITY_END_ORDER);
                startActivity(goToProveActivity);
            }
        });


    }

    private void assignActionBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_detail);
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

    private Dialog createDialogDetail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
        LayoutInflater inflater = getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_stop_order_conf, null))
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        requestStopOrder();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        return dialog;
    }

    private void requestStopOrder() {
        //get text dari kolom alasan (edittext_stoporder_reason)
        Toast.makeText(this, "Data berhasil disubmit", Toast.LENGTH_LONG).show();
    }

}
