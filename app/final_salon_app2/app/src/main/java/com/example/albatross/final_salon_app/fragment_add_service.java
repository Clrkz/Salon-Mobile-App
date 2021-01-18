package com.example.albatross.final_salon_app;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class fragment_add_service extends Fragment implements AdapterView.OnItemSelectedListener {
    Button btn_cancel, btn_add;
    Toolbar toolbar;
    addServiceHandler ph;
    ConnectivityManager conMgr;
    static EditText et_servicename, et_price, et_duration;
    static Spinner sp_services;
    Button btnAdd;
    final String url_update = Server.URL + "add_salon_services.php";
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_service, container, false);

        ph = new addServiceHandler();
        btnAdd = v.findViewById(R.id.btn_add);
        et_servicename = v.findViewById(R.id.et_servicename);
        et_price = v.findViewById(R.id.et_price);
        et_duration = v.findViewById(R.id.et_duration);
        sp_services= v.findViewById(R.id.sp_services);
        btn_cancel = v.findViewById(R.id.btn_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String servicetype = sp_services.getSelectedItem().toString();
                String servicename = et_servicename.getText().toString();
                String price = et_price.getText().toString();
                String duration = et_duration.getText().toString();
                new UpdateUser().execute(servicetype,servicename,price, duration);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_all_services()).commit();
            }
        });


        return v;
    } // ----

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class UpdateUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String servicetype = strings[0];
            String servicename = strings[1];
            String price = strings[2];
            String duration = strings[3];

            String finalURL = url_update +
                    "?service_type=" + servicetype +
                    "&service_name=" + servicename +
                    "&service_price=" + price +
                    "&service_duration=" + duration ;
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

                            showSuccess();getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_all_services()).commit();

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

    public void showExist() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("User already exists...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
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
                        .setMessage("Service successfully added...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
    public void showToast(final String Text){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
