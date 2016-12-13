package com.serasiautoraya.slimobiledrivertracking.customdialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.serasiautoraya.slimobiledrivertracking.helper.HelperBridge;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;
import com.serasiautoraya.slimobiledrivertracking.helper.HelperUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Randi Dwi Nandra on 23/11/2016.
 */
public class DatePickerToEditTextDialog {
    //UI References
    private EditText editText;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    private static String dateFormat = HelperKey.USER_DATE_FORMAT;

    private boolean isBeforeToday, isInMaxRequest, isToday;

    public DatePickerToEditTextDialog(EditText editText, Context context){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+07:00"));
        this.editText = editText;
        this.context = context;
        dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        editText.setInputType(InputType.TYPE_NULL);
        editText.requestFocus();
        setDateTimeField();
    }


    private void setDateTimeField() {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this.context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date = dateFormatter.format(newDate.getTime());
                editText.setText(date);
                isBeforeToday = checkBeforeToday(date);
                isInMaxRequest = checkInMaxRequest(date);
                isToday = checkIsToday(date);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private boolean checkIsToday(String date) {
        try {
            Date strDate = dateFormatter.parse(date);
            return HelperUtil.isToday(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkInMaxRequest(String date) {
        try {
            Date nowDate = new Date();
            Date targetDate = dateFormatter.parse(date);
            long days = HelperUtil.getDaysBetween(targetDate, nowDate);

            if(days <= HelperBridge.maxRequest){
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isInMaxRequest() {
        return isInMaxRequest;
    }

    private boolean checkBeforeToday(String date){
        try {
            Date nowDate = new Date();
            Date targetDate = dateFormatter.parse(date);
            long days = HelperUtil.getDaysBetween(nowDate, targetDate);

            if(days <= 0){
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBeforeToday() {
        return isBeforeToday;
    }



}
