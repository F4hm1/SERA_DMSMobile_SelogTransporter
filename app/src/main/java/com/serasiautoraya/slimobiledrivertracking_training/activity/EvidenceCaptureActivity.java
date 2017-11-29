package com.serasiautoraya.slimobiledrivertracking_training.activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.serasiautoraya.slimobiledrivertracking_training.R;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking_training.helper.HelperUtil;
import com.serasiautoraya.slimobiledrivertracking_training.model.ExpenseObject;

import org.apmem.tools.layouts.FlowLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Randi Dwi Nandra on 02/03/2017.
 */

public class EvidenceCaptureActivity extends AppCompatActivity {

    @BindView(R.id.evidence_expandable_photos) ExpandableLinearLayout mExpandablePhotos;
    @BindView(R.id.evidence_flowlayout_photos) FlowLayout mFlowLayoutPhotos;

    @BindView(R.id.evidence_expandable_signature) ExpandableLinearLayout mExpandableSignature;
    @BindView(R.id.evidence_iv_signature) ImageView mIvSignature;

    @BindView(R.id.evidence_expandable_expense) ExpandableLinearLayout mExpandableExpense;
    @BindView(R.id.evidence_lin_expense) LinearLayout mLinExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence_capture);

        ButterKnife.bind(this);

        HelperKey.TransitionType  transitionType = (HelperKey.TransitionType) getIntent().getSerializableExtra(HelperKey.KEY_TRANSITION_TYPE);
        String toolbarTitle = "Evidence Capture";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(toolbarTitle);

//        HelperTransition.startEnterTransition(transitionType, getWindow(), EvidenceCaptureActivity.this);
    }

    private Uri mImageUri;

    /*
    * TODO Change all below code
    * */
    @OnClick(R.id.evidence_button_opencamera)
    public void openCameraOnClick(View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo;
        try
        {
            photo = this.createTemporaryFile("temp_bukti_sli", ".jpg");
            photo.delete();
        }
        catch(Exception e)
        {
            Toast.makeText(EvidenceCaptureActivity.this, "Please check SD card! Image shot is impossible!", Toast.LENGTH_SHORT).show();
            return;
        }

        mImageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, HelperKey.ACTIVITY_IMAGE_CAPTURE);
    }

    /*
    * TODO Change all below code
    * */
    @OnClick(R.id.evidence_iv_signature)
    public void ivSignatureOnClick(View view){
        Intent goToApprovalActivity = new Intent(EvidenceCaptureActivity.this, SignatureActivity.class);
        startActivityForResult(goToApprovalActivity, HelperKey.ACTIVITY_PROVE);

        mExpandablePhotos.initLayout();
        if(mExpandablePhotos.isExpanded()){
            mExpandablePhotos.expand();
        }
    }

    /*
    * TODO Change all below code
    * */
    @OnClick(R.id.evidence_iv_expense)
    public void openActivityExpenseOnClick(View view){
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent i = new Intent(EvidenceCaptureActivity.this, ExpanseCaptureActivity.class);
        i.putExtra(HelperKey.KEY_TRANSITION_TYPE, HelperKey.TransitionType.Slide);
        i.putExtra(HelperKey.KEY_TITLE_ANIM, "Capture Expanse");
        startActivityForResult(i, HelperKey.ACTIVITY_RESULT_EXPENSES, options.toBundle());
    }

//    Expand buttons group clicked
    @OnClick(R.id.evidence_button_capture)
    void captureExpandOnClick(View view){
        mExpandablePhotos.toggle();
    }

    @OnClick(R.id.evidence_button_expand_signature)
    void signatureExpandOnClick(View view){
        mExpandableSignature.toggle();
    }

    @OnClick(R.id.evidence_button_expand_expense)
    void expenseExpandOnClick(View view){
        mExpandableExpense.toggle();
    }

    /*
    * TODO Change all below code
    * */
    @OnClick(R.id.evidence_btn_submit)
    public void evidenceSubmitOnClick(View view){

    }

    @Override
    public boolean onSupportNavigateUp() {
        finishAfterTransition();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case HelperKey.ACTIVITY_PROVE:{
                if(HelperBridge.sTtdBitmap != null){
                    Bitmap ttdBitmap = HelperUtil.rotateBitmap(HelperBridge.sTtdBitmap, 90f);
                    mIvSignature.setImageBitmap(ttdBitmap);
                    mIvSignature.setBackgroundColor(Color.TRANSPARENT);
                    mIvSignature.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    mIvSignature.setAdjustViewBounds(true);
                }
                break;
            }
            case HelperKey.ACTIVITY_IMAGE_CAPTURE:{
                if (resultCode == RESULT_OK ) {
                    LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = vi.inflate(R.layout.single_thumbnail_photo, null);

                    ImageView imageView = (ImageView) v.findViewById(R.id.single_thumbnail_image);
                    this.setImageThumbnail(imageView, v);

                    mExpandablePhotos.initLayout();
                    if(mExpandablePhotos.isExpanded()){
                        mExpandablePhotos.expand();
                    }
                }
                break;
            }
            case HelperKey.ACTIVITY_RESULT_EXPENSES:{
                if(resultCode == RESULT_OK){
                    if(data != null){
//                        Uri uri = (Uri) data.getParcelableExtra("Uri");
//                        String jumlah = data.getStringExtra("Jumlah");
//                        String tipe = data.getStringExtra("Tipe");
//
                        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = vi.inflate(R.layout.single_thumbnail_expense, null);
//
                        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.evidence_lin_expense);
                        insertPoint.addView(v, 1);
                        ImageView iv = (ImageView) v.findViewById(R.id.single_thumbnail_expenses_image);
                        TextView textViewJumlah = (TextView) v.findViewById(R.id.single_thumbnail_expenses_jumlah);
                        TextView textViewTipe = (TextView) v.findViewById(R.id.single_thumbnail_expenses_tipe);
//
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                            iv.setImageBitmap(bitmap);
//                            bitmap = null;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                        ExpenseObject expenseObject = (ExpenseObject)data.getParcelableExtra("expenseObject");
                        iv.setImageBitmap(HelperUtil.getThumbnailBitmap(getContentResolver(), expenseObject.getRealBitmapUri(), 128));
                        textViewJumlah.setText(expenseObject.getJumlahPengeluaran());
                        textViewTipe.setText(expenseObject.getTipeExpenses());

                        mExpandableExpense.initLayout();
                        if(mExpandableExpense.isExpanded()){
                            mExpandableExpense.expand();
                        }



//                        HelperUtil.showSimpleToast(
//                                uri.toString()+" -- \n"+
//                                data.getStringExtra("Jumlah")+" -- \n"
//                                +data.getStringExtra("Tipe"), EvidenceCaptureActivity.this);
//                        HelperUtil.showSimpleToast(data.getStringExtra("Uri"), EvidenceCaptureActivity.this);
//                        HelperUtil.showSimpleToast(data.getStringExtra("Tipe"), EvidenceCaptureActivity.this);
                    }
                }
            }
        }
    }

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getPath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    public void setImageThumbnail(ImageView imageView, View v)
    {
        try
        {
            final Bitmap bitmapScaled = HelperUtil.saveScaledBitmap(mImageUri.getPath(), HelperUtil.getFirstImageName());

            imageView.setImageBitmap(bitmapScaled);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = HelperUtil.showImagePreview(bitmapScaled, EvidenceCaptureActivity.this);
                    dialog.show();
                }
            });

            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.evidence_flowlayout_photos);
            insertPoint.addView(v, 0);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
        }
    }
}
