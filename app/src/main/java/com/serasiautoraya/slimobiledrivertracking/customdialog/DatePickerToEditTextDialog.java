package com.serasiautoraya.slimobiledrivertracking.customdialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.serasiautoraya.slimobiledrivertracking.helper.HelperKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    private boolean isBeforeToday;

    public DatePickerToEditTextDialog(EditText editText, Context context){
        this.editText = editText;
        this.context = context;
        dateFormatter = new SimpleDateFormat(dateFormat, Locale.US);
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
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private boolean checkBeforeToday(String date){
        //FALSE is Valid Date (Tanggal setelah tanggal hari ini)
        Date strDate = null;
        try {
            strDate = dateFormatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (new Date().after(strDate)) {
            if(DateUtils.isToday(strDate.getTime())){
                return false;
            }else{
                return true;
            }
        }
        else{
            return false;
        }


    }

    public boolean isBeforeToday() {
        return isBeforeToday;
    }
}
