package com.example.albatross.final_salon_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class book_member<et_time> extends Fragment {
    EditText et_date,et_time;
    DatePickerDialog.OnDateSetListener date;
    Button btn_next,btn_cancel;
    final String url_update = Server.URL + "book_member.php";
    TextView tvfhr,tvthr,tvcdate;
    final Calendar myCalendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_book_member, container, false);
        et_date = v.findViewById(R.id.et_date);
        et_time = v.findViewById(R.id.et_time);
        btn_next= v.findViewById(R.id.btn_next);
        btn_cancel= v.findViewById(R.id.btn_cancel);
        tvfhr= v.findViewById(R.id.tvfhr);
        tvthr= v.findViewById(R.id.tvthr);
        tvcdate= v.findViewById(R.id.tvcdate);

        tvfhr.setText("Booking Start Hour: " + SettingHandler.fhr);
        tvthr.setText("Booking End Hour: " + SettingHandler.thr);
        tvcdate.setText("Booking Close Date: " + SettingHandler.cdate);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String AM_PM = " AM";
                        String mm_precede = "";
                        if (hourOfDay >= 12) {
                            AM_PM = " PM";
                            if (hourOfDay >=13 && hourOfDay < 24) {
                                hourOfDay -= 12;
                            }
                            else {
                                hourOfDay = 12;
                            }
                        } else if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }
                        if (minute < 10) {
                            mm_precede = "0";
                        }
                        et_time.setText( hourOfDay + ":" + mm_precede + minute + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_date.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Booking Date is Required", Toast.LENGTH_SHORT).show();
                } else if (et_time.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Booking Time is Required", Toast.LENGTH_SHORT).show();
                } else {
                    String pdate = et_date.getText().toString();
                    String ptime = et_time.getText().toString();
                    new UpdateUser().execute(Bookhandler.customerID, Bookhandler.membership, Bookhandler.serviceId, pdate, ptime);
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_hair_services()).commit();
  }
        });
        return  v;
    }

    public void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_date.setText(sdf.format(myCalendar.getTime()));

    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String AM_PM = " AM";
        String mm_precede = "";
        if (hourOfDay >= 12) {
            AM_PM = " PM";
            if (hourOfDay >=13 && hourOfDay < 24) {
                hourOfDay -= 12;
            }
            else {
                hourOfDay = 12;
            }
        } else if (hourOfDay == 0) {
            hourOfDay = 12;
        }
        if (minute < 10) {
            mm_precede = "0";
        }
        et_time.setText( hourOfDay + ":" + mm_precede + minute + AM_PM);
    }



    public class UpdateUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String customerID = strings[0];
            String membership = strings[1];
            String serviceId = strings[2];
            String pdate = strings[3];
            String ptime = strings[4];

            String finalURL = url_update +
                    "?cid=" + customerID +
                    "&mem=" + membership +
                    "&sid=" + serviceId +
                    "&date=" + pdate +
                    "&time=" + ptime ;
            Log.e("",finalURL);
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(finalURL)
                        .get()
                        .build();
                Response response = null;

                try {
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string().trim();
                        if (result.equalsIgnoreCase("success")) {
                            showSuccess();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fragment()).commit();

                        } else {
                            showDialogs();
                        }
                    }
                } catch (Exception e) {
                    showDialogs();
                    e.printStackTrace();
                }
            }catch (Exception e){
                showDialogs();
                e.printStackTrace();
            }
            return null;
        }
    }
    public void showDialogs() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Success")
                        .setMessage("Successfully booked...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


}
