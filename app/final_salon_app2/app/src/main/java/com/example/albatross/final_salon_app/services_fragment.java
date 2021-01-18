package com.example.albatross.final_salon_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class services_fragment extends Fragment {
    LinearLayout linearhair;
    LinearLayout linearfoot;
    LinearLayout linearnail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        linearhair = v.findViewById(R.id.linearhair);
        linearfoot = v.findViewById(R.id.linearfoot);
        linearnail = v.findViewById(R.id.linearnail);

        linearhair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_hair_services()).commit();

            }
        });

        linearfoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_foot_services()).commit();

            }
        });

        linearnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_nail_services()).commit();

            }
        });


        return  v;
    }
}
