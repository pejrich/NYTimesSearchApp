package com.perich.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.perich.nytimessearch.R;
import com.perich.nytimessearch.models.Filter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by perich on 3/21/16.
 */
public class FilterActivity extends AppCompatActivity {

    int DATE_PICKER_DIALOG_ID = 123;

    public Filter filter;

    public DatePicker datePicker;
    public Calendar calendar;
    public TextView dateView;
    public TextView dateLabel;
    private int year, month, day;
    public RadioButton rbOldest;
    public RadioButton rbNewest;
    public CheckBox cbFS;
    public CheckBox cbArts;
    public CheckBox cbForeign;
    public CheckBox cbSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        filter = (Filter) getIntent().getSerializableExtra("filter");
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Search Filter Settings");
        setVars();
    }

    public void setVars() {
        // DatePicker vars
        dateLabel = (TextView) findViewById(R.id.dateTextLabel);
        dateView = (TextView) findViewById(R.id.dateText);
        calendar = Calendar.getInstance();
        if (filter.startDate != null) {
            year = filter.startDate.getYear();
            month = filter.startDate.getMonth();
            day = filter.startDate.getDate();
            showDate(year, month + 1, day);
        } else {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            dateLabel.setText("Select a starting date.");
        }

        rbOldest = (RadioButton) findViewById(R.id.rbOldest);
        rbNewest = (RadioButton) findViewById(R.id.rbNewest);
        if (filter.oldest) {
            rbOldest.setChecked(true);
        } else {
            rbNewest.setChecked(true);
        }

        cbFS = (CheckBox) findViewById(R.id.cbFS);
        if (filter.newsDesk.contains("Fashion & Style")) {
            cbFS.setChecked(true);
        }
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        if (filter.newsDesk.contains("Arts")) {
            cbArts.setChecked(true);
        }
        cbForeign = (CheckBox) findViewById(R.id.cbForeign);
        if (filter.newsDesk.contains("Foreign")) {
            cbForeign.setChecked(true);
        }
        cbSports = (CheckBox) findViewById(R.id.cbSports);
        if (filter.newsDesk.contains("Sports")) {
            cbSports.setChecked(true);
        }
    }

    public void setDate(View view) {
        showDialog(DATE_PICKER_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_PICKER_DIALOG_ID) {
            return new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateLabel.setText("Articles written after ");
            showDate(year, month + 1, day);
            Date date = new Date();
            date.setYear(year);
            date.setMonth(month);
            date.setDate(day);
            filter.startDate = date;
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                                            .append(month).append("/")
                                            .append(year));
    }

    public void onSave(View view) {

        if (rbOldest.isChecked()) {
            filter.oldest = true;
        } else {
            filter.oldest = false;
        }

        filter.newsDesk = new ArrayList<>();
        if (cbFS.isChecked()) {
            filter.newsDesk.add("Fashion & Style");
        }
        if (cbArts.isChecked()) {
            filter.newsDesk.add("Arts");
        }
        if (cbForeign.isChecked()) {
            filter.newsDesk.add("Foreign");
        }
        if (cbSports.isChecked()) {
            filter.newsDesk.add("Sports");
        }

        Intent intent = new Intent();
        intent.putExtra("filter", filter);
        setResult(0, intent);
        finish();
    }
}

