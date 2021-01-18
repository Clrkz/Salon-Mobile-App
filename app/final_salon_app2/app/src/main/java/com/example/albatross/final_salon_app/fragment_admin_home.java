package com.example.albatross.final_salon_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class fragment_admin_home extends Fragment {
    LinearLayout linearhome1, ln_customer, ln_service;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_home, container, false);
        linearhome1 = v.findViewById(R.id.linearhome1);
        ln_customer = v.findViewById(R.id.ln_customer);
        ln_service = v.findViewById(R.id.linear_service);
        linearhome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_all_users()).commit();
            }
        });
        ln_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_all_customer()).commit();
            }
        });

        ln_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_all_services()).commit();
            }
        });

        return v;
    }
}
