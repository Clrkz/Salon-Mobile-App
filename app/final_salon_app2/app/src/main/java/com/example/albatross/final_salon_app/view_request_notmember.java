package com.example.albatross.final_salon_app;

import android.content.Context;
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
import android.widget.EditText;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class view_request_notmember extends Fragment {
    EditText datebook,name,service,member,price,dateappoint;
    Button btn_update,btn_back,image,btn_decline;
    final String url_update = Server.URL + "accept_booking.php";
    String uids;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_request_member, container, false);
        datebook = v.findViewById(R.id.datebook);
        name = v.findViewById(R.id.name);
        service = v.findViewById(R.id.service);
        member = v.findViewById(R.id.member);
        price = v.findViewById(R.id.price);
        dateappoint = v.findViewById(R.id.dateappoint);
        btn_update = v.findViewById(R.id.btn_update);
        btn_back = v.findViewById(R.id.btn_back);
        image = v.findViewById(R.id.image);
if(Integer.parseInt(BookingHandler.price)<100){
    image.setVisibility(View.INVISIBLE);
}else{
    image.setVisibility(View.VISIBLE);
}



        btn_decline= v.findViewById(R.id.btn_decline);
        datebook.setText( "Date of Book: " +BookingHandler.dateadded);
        name.setText(BookingHandler.name);
        service.setText("Service: "+ BookingHandler.service);
        member.setText("Not Member");
        price.setText("Price: "+ BookingHandler.price);
        dateappoint.setText("Date of Appointment: " +BookingHandler.date + " " + BookingHandler.time);
        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String role = pref.getString("u_role", null);
        uids =  pref.getString("u_information_id_PK", null);
        if(role.equals("Admin")){
            btn_update.setVisibility(View.GONE);
            btn_decline.setVisibility(View.GONE);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_payment()).commit();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "0";
new UpdateUser().execute(status);
            }
        });

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "2";
                new UpdateUser().execute(status);
            }
        });
        return  v;
    }
    public class UpdateUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String uid = uids;
            String bid = BookingHandler.id;
            String status = strings[0];
            String finalURL = url_update +
                    "?uid=" + uid +
                    "&bid=" + bid +
                    "&status=" + status ;
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
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_bookings()).commit();

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
                        .setMessage("Successful...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


}
