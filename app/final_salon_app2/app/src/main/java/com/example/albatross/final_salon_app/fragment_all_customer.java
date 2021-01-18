package com.example.albatross.final_salon_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fragment_all_customer extends Fragment {
    static List<Customers> services;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private CustomersAdapter adapter;
    ConnectivityManager conMgr;
    FloatingActionButton btn_add;
    Button btn_back;
    Toolbar toolbar;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_all_customer, container, false);


        android.support.v7.widget.Toolbar toolbar = v.findViewById(R.id.toolbars);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_admin_home()).commit();
            }
        });
        btn_add = v.findViewById(R.id.btn_add);
        btn_back=v.findViewById(R.id.btn_back);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),
                        sign_up.class);
                startActivity(i);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_admin_home()).commit();
            }
        });


        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
            services = new ArrayList<>();
            getServicesFromDB(0);

            gridLayout = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(gridLayout);

            adapter = new CustomersAdapter(getActivity(), services);
            recyclerView.setAdapter(adapter);

        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Error")
                    .setMessage("No Internet Connection")
                    // .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton(android.R.string.ok, null).show();
//            progressBar.setVisibility(View.GONE);
        }
        return v;
    }


    private void getServicesFromDB(int id) {
        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... movieIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Server.URL + "customer_list.php" )
                        .build();
                Log.e("TAG", Server.URL + "customer_list.php" );
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Customers services = new Customers(
                                object.getString("guest_id_PK"),
                                object.getString("g_firstname"),
                                object.getString("g_lastname"),
                                object.getString("g_picture"),
                                object.getString("g_date_time_added"),
                                object.getString("g_contact_number"),
                                object.getString("g_fb_name"),
                                object.getString("g_gmail"),
                                object.getString("g_password"),
                                object.getString("g_status"),
                                object.getString("g_gender"));

                        fragment_all_customer.this.services.add(services);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                //  progressBar.setVisibility(View.GONE);
            }
        };

        asyncTask.execute(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
