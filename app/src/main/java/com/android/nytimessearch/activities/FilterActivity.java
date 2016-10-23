package com.android.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.nytimessearch.R;
import com.android.nytimessearch.helpers.DatePickerFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String sortOption;
    String date;
    Boolean checkedArtsItem;
    Boolean checkedSportsItem;
    Boolean checkedFashionItem;
    String newsDesk = null;
    HashMap<String,Boolean> filters = new HashMap<String,Boolean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void saveFilters(View view) {
        Spinner comboBox = (Spinner)findViewById(R.id.sortDropdown);
        sortOption=comboBox.getSelectedItem().toString();
        TextView dateSelected=(TextView)findViewById(R.id.date);
        DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat targetFormat = new SimpleDateFormat("yyyyMMdd");
        CheckBox artsCheckbox = (CheckBox)findViewById(R.id.arts);
        CheckBox sportsCheckbox = (CheckBox)findViewById(R.id.sports);
        CheckBox fashionCheckbox = (CheckBox)findViewById(R.id.fashion);
        if (artsCheckbox.isChecked() == true) {
            newsDesk = (newsDesk ==  null) ? artsCheckbox.getText().toString() + " ": artsCheckbox.getText().toString()+" ";
        }
        if (sportsCheckbox.isChecked() == true) {
            newsDesk = (newsDesk ==  null) ? sportsCheckbox.getText().toString() + " ": (newsDesk + sportsCheckbox.getText().toString() + " ");
        }
        if (fashionCheckbox.isChecked() == true) {
            newsDesk = (newsDesk ==  null) ? fashionCheckbox.getText().toString() : newsDesk + fashionCheckbox.getText().toString();
        }
        try {
            date = targetFormat.format(originalFormat.parse(dateSelected.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent i = new Intent();
        i.putExtra("sortOption", sortOption);
        i.putExtra("date", date);
        i.putExtra("newsDesk", newsDesk.trim());
//        i.putExtra("filtersMap", filters);
        setResult(RESULT_OK,i);
        finish();
    }
    public void selectDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        Date date = c.getTime();
        TextView dateView = (TextView)findViewById(R.id.date);
        dateView.setText(new SimpleDateFormat("MM/dd/yyyy").format(date));
    }
}
