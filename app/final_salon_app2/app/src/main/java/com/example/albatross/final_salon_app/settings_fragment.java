package com.example.albatross.final_salon_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TimePicker;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class settings_fragment extends Fragment {
    EditText et_fhr,et_thr,et_cdate;
    DatePickerDialog.OnDateSetListener date;
    Button btn_next;
    final String url_Login = Server.URL + "get_settings.php";
    JSONObject json;
    final Calendar myCalendar = Calendar.getInstance();
    final String url_update = Server.URL + "update_settings.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);

        et_fhr = v.findViewById(R.id.et_fhr);
        et_thr = v.findViewById(R.id.et_thr);
        et_cdate = v.findViewById(R.id.et_cdate);
        btn_next = v.findViewById(R.id.btn_next);

        et_fhr.setText(SettingHandler.fhr);
        et_thr.setText(SettingHandler.thr);
        et_cdate.setText(SettingHandler.cdate);

        et_fhr.setOnClickListener(new View.OnClickListener() {
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
                        et_fhr.setText( hourOfDay + ":" + mm_precede + minute + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        et_thr.setOnClickListener(new View.OnClickListener() {
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
                        et_thr.setText( hourOfDay + ":" + mm_precede + minute + AM_PM);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        et_cdate.setOnClickListener(new View.OnClickListener() {
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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fhr = et_fhr.getText().toString();
                String thr = et_thr.getText().toString();
                String cdate = et_cdate.getText().toString();
                new UpdateUser().execute( fhr,thr,cdate);
            }
        });


        return  v;
    }
    public void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_cdate.setText(sdf.format(myCalendar.getTime()));

    }

    public class UpdateUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String fhr = strings[0];
            String thr = strings[1];
            String cdate = strings[2];

            String finalURL = url_update +
                    "?fhr=" + fhr +
                    "&thr=" + thr +
                    "&cdate=" + cdate  ;
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
                          //  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fragment()).commit();

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
                        .setMessage("Settings Successfully Updated...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public class LoginUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(url_Login)
                    .post(formBody)
                    .build();
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    //   showToast(success);
                    if (success == 1) {
                        et_fhr.setText(json.getString("hfrom"));
                        et_thr.setText(json.getString("hto"));
                        et_cdate.setText(json.getString("date_close"));
                    } else if (success == 0) {
                        showFailed();
                    }
                } else {
                    showError();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }
    public void showToast(int n) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Email or Password incorrect..." + n)
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showFailed() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")


                        .setMessage("Email or Password incorrect...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showError() {
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

}
