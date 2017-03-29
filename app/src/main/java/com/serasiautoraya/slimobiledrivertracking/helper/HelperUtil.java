package com.serasiautoraya.slimobiledrivertracking.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.serasiautoraya.slimobiledrivertracking.R;
import com.serasiautoraya.slimobiledrivertracking.activity.DashboardActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Randi Dwi Nandra on 18/11/2016.
 */
public class HelperUtil {

    public static <T> T getMyObject(Object object, Class<T> cls){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), cls);
    }

    public static void goToActivity(Activity activityFrom, Class activityTo){
        Intent changeActivity = new Intent(activityFrom, activityTo);
        activityFrom.startActivity(changeActivity);
    }

    public static void goToActivity(Activity activityFrom, Class activityTo, String key, String value){
        Intent changeActivity = new Intent(activityFrom, activityTo);
        changeActivity.putExtra(key, value);
        activityFrom.startActivity(changeActivity);
    }

    public static String getValueStringArrayXML(String[] arrKey, String[] arrVal, String key){
        String val = "";
        for (int i = 0; i < arrKey.length; i++) {
            if(arrKey[i].equals(key)){
                val = arrVal[i];
            }
        }
        return val;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static String getFirstImageName(){
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return HelperUrl.SAVE_DIRECTORY + "bukti_1_" + date + ".jpg";
    }

    public static String getSecondImageName(){
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return HelperUrl.SAVE_DIRECTORY + "bukti_2_" + date + ".jpg";
    }

    public static boolean saveImageToDirectory(Bitmap bitmap, String storedPath){
        boolean result = true;
        try {
            FileOutputStream mFileOutStream = new FileOutputStream(storedPath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();
        } catch (Exception e) {
            Log.v("IMAGE_TAG", e.toString());
            result = false;
        }
        return result;
    }

    public static Bitmap saveScaledBitmap(String storedPath, String targetPath){
        int desiredWidth = HelperKey.SAVED_IMAGE_DESIRED_WITDH;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(storedPath, options);

        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;

        if(desiredWidth > srcWidth) {
            desiredWidth = srcWidth;
        }

        int inSampleSize = 1;
        while(srcWidth / 2 > desiredWidth){
            srcWidth /= 2;
            srcHeight /= 2;
            inSampleSize *= 2;
        }

        float desiredScale = (float) desiredWidth / srcWidth;

        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = inSampleSize;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap sampledSrcBitmap = BitmapFactory.decodeFile(storedPath, options);

        Matrix matrix = new Matrix();
        matrix.postScale(desiredScale, desiredScale);
        Bitmap scaledBitmap = Bitmap.createBitmap(sampledSrcBitmap, 0, 0, sampledSrcBitmap.getWidth(), sampledSrcBitmap.getHeight(), matrix, true);
        sampledSrcBitmap = null;

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(targetPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//        scaledBitmap = null;
        return scaledBitmap;
    }

    public static void showConfirmationAlertDialog(CharSequence msg, Context context, DialogInterface.OnClickListener onClickListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Perhatian");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YA", onClickListener);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void showSimpleToast(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    private static ProgressDialog progressDialog;
    public static void showProgressDialog(Context context, String msg){
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    public static void showSimpleAlertDialog(String msg, Context context){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void showSimpleAlertDialogCustomTitle(String msg, Context context, String title){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void showSimpleAlertDialogCustomAction(String msg, Context context, DialogInterface.OnClickListener onClickListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Peringatan");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "YA",
                onClickListener);
        alertDialog.show();
    }

    public static boolean isDateBefore(String firstDate, String secondDate){
        //FALSE is Valid Date (Tanggal berakhir setelah tanggal mulai)
        SimpleDateFormat sdf = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        Date fDate = null;
        Date sDate = null;
        Calendar fCal = Calendar.getInstance();
        Calendar sCal = Calendar.getInstance();
        try {
            fDate = sdf.parse(firstDate);
            fCal.setTime(fDate);
            sDate = sdf.parse(secondDate);
            sCal.setTime(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (fDate.after(sDate)) {
            if(fCal.get(Calendar.YEAR) == sCal.get(Calendar.YEAR) &&
                    fCal.get(Calendar.MONTH) == sCal.get(Calendar.MONTH) &&
                    fCal.get(Calendar.DATE) == sCal.get(Calendar.DATE)
                    ){
                return false;
            }else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    public static void showConfirmationAlertDialogNoCancel(CharSequence msg, Context context, DialogInterface.OnClickListener onClickListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Perhatian!");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", onClickListener);
        alertDialog.show();
    }

    public static Calendar getCalendarVersion(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    public static long getDaysBetween(Date startDate, Date endDate) {
        Calendar sDate = getCalendarVersion(startDate);
        Calendar eDate = getCalendarVersion(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static boolean isToday(Date date) {
        Calendar sDate = getCalendarVersion(date);
        Calendar eDate = getCalendarVersion(new Date());

        if(!sDate.after(eDate) && !sDate.before(eDate)){
            return true;
        }else {
            return false;
        }
    }

    public static String getServerFormDate(String userFormDate){
        SimpleDateFormat serverDateFormat = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat userDateFormat = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        try {
            Date resDate = userDateFormat.parse(userFormDate);
            return serverDateFormat.format(resDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getUserFormDate(String serverFormDate){
        SimpleDateFormat serverDateFormat = new SimpleDateFormat(HelperKey.SERVER_DATE_FORMAT, Locale.getDefault());
        SimpleDateFormat userDateFormat = new SimpleDateFormat(HelperKey.USER_DATE_FORMAT, Locale.getDefault());
        try {
            Date resDate = serverDateFormat.parse(serverFormDate);
            return userDateFormat.format(resDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getMonthName(String monthNum, Context context){
        switch (monthNum){
            case "01":
                return context.getResources().getString(R.string.bulan_01);
            case "02":
                return context.getResources().getString(R.string.bulan_02);
            case "03":
                return context.getResources().getString(R.string.bulan_03);
            case "04":
                return context.getResources().getString(R.string.bulan_04);
            case "05":
                return context.getResources().getString(R.string.bulan_05);
            case "06":
                return context.getResources().getString(R.string.bulan_06);
            case "07":
                return context.getResources().getString(R.string.bulan_07);
            case "08":
                return context.getResources().getString(R.string.bulan_08);
            case "09":
                return context.getResources().getString(R.string.bulan_09);
            case "10":
                return context.getResources().getString(R.string.bulan_10);
            case "11":
                return context.getResources().getString(R.string.bulan_11);
            case "12":
                return context.getResources().getString(R.string.bulan_12);
            default:
                return monthNum;
        }
    }

    public static Dialog getConfirmOrderDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_confirmation, null))
                .setPositiveButton("Diterima", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showImagePreview(Bitmap srcBmp, Activity activity){
        View view = activity.getLayoutInflater().inflate( R.layout.dialog_image_preview, null);

        ImageView postImage = (ImageView) view.findViewById(R.id.iv_container);
        postImage.setImageBitmap(srcBmp);

        Dialog builder = new Dialog(activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    public static Bitmap getThumbnailBitmap(ContentResolver contentResolver, Uri uri, int size) {
        Bitmap original = null;
        try {
            original = MediaStore.Images.Media.getBitmap(contentResolver, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (original != null) {
            return ThumbnailUtils.extractThumbnail(original, size, size);
        } else {
            return original;
        }
    }

}

