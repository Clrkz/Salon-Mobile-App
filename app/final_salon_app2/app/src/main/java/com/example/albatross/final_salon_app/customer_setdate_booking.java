package com.example.albatross.final_salon_app;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class customer_setdate_booking extends Fragment {
    boolean success = false;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    static EditText etPetBday, etSpecies, etPetBreed, etPetName, etPetWeight;
    static Spinner etPetGender;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    Button btnSave;
    ImageView ivPetPhoto;
    String ownerID;
    EditText etDate, etTime;
    UpdateHandler ph;
    TimePickerDialog timePickerDialog1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_customer_setdate_booking, container, false);
        View v = inflater.inflate(R.layout.fragment_customer_setdate_booking, container, false);



        etDate = v.findViewById(R.id.et_date);
        etTime = v.findViewById(R.id.et_time);
       // etDate.setText(ph.etDate);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePickerDialog1 = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedhour, int selectedminute) {
                        etTime.setText(String.format("%02d:%02d",selectedhour,selectedminute));
                    }
                }, hour, minute,false);
                timePickerDialog1.setTitle("Select Time");
                timePickerDialog1.show();
            }
        });

        return v;
    }

    public void updateLabel(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));

    }


    /*
    etPetBday.setText(ph.petBirthday);

    etPetBday.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    });*/
}
