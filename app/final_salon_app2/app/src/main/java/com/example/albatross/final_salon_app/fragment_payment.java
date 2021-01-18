package com.example.albatross.final_salon_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fragment_payment extends Fragment {
    static List<Booking> services;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private BookingAdapter adapter;
    ConnectivityManager conMgr;
    ProgressBar progressBar;
    Button btn_back;
    Toolbar toolbar;
    ImageView imgProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_payment, container, false);
        imgProfile = v.findViewById(R.id.img_profile);
        if (!BookingHandler.image.equals("")) {
            Glide.with(getActivity()).load(Server.URL + "images/" + BookingHandler.image).into(imgProfile);
            Log.e("IMAGE ",Server.URL + "images/" + BookingHandler.image);
        }

        return v;
    }


}
