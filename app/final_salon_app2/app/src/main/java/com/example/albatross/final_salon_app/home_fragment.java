package com.example.albatross.final_salon_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class home_fragment extends Fragment {
   Button btn_services;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_services, container, false);
        btn_services = v.findViewById(R.id.btn_services);
        SharedPreferences pref = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String role = pref.getString("u_role", null);
        if (SaveSharedPreference.getLoggedStatus(getActivity().getApplicationContext())) {

            if (role.equals("Admin") || role.equals("Staff")) {
                btn_services.setVisibility(View.INVISIBLE);
            } else {
                btn_services.setVisibility(View.VISIBLE);
            }

        }


        btn_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new services_fragment()).commit();
            }
        });

        return  v;
    }
}
