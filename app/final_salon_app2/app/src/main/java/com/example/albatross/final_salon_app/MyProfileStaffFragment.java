package com.example.albatross.final_salon_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class MyProfileStaffFragment extends Fragment {
    TextView et_firstname,et_contact_number,et_facebook_username,et_email,et_gender,et_users,et_address;
    Button btnSignout,btnUpdate;
    ImageView img_profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_profile_staff, container, false);
        et_firstname = v.findViewById(R.id.et_firstname);
        et_contact_number = v.findViewById(R.id.et_contact_number);
        et_facebook_username = v.findViewById(R.id.et_facebook_username);
        et_email = v.findViewById(R.id.et_email);
        et_gender = v.findViewById(R.id.et_gender);
        et_users = v.findViewById(R.id.et_users);
        et_address = v.findViewById(R.id.et_address);
        img_profile = v.findViewById(R.id.img_profile);
        btnUpdate  = v.findViewById(R.id.btnUpdate);
        btnSignout =  v.findViewById(R.id.btnSignout);
        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String u_firstname = pref.getString("u_firstname", null);
        String u_lastname = pref.getString("u_lastname", null);
        String u_address = pref.getString("u_address", null);
        String u_picture = pref.getString("u_picture", null);
        String u_dateadded = pref.getString("u_dateadded", null);
        String u_gender = pref.getString("u_gender", null);
        String u_fb_name = pref.getString("u_fb_name", null);
        String u_gmail = pref.getString("u_gmail", null);
        String u_password = pref.getString("u_password", null);
        String u_phone_number = pref.getString("u_phone_number", null);
        String u_availability = pref.getString("u_availability", null);
        String u_status = pref.getString("u_status", null);
        String u_role = pref.getString("u_role", null);
        et_firstname.setText(u_firstname+" "+u_lastname);
        et_contact_number.setText(u_phone_number);
        et_facebook_username.setText(u_fb_name);
        et_email.setText(u_gmail);
        et_gender.setText(u_gender);
        et_users.setText(u_role);
        et_address.setText(u_address);

        if(!u_picture.equals("")){
            Glide.with(getActivity()).load(Server.URL+"images/"+u_picture).into(img_profile);
            //img_profile.setVisibility(View.INVISIBLE);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateUserProfile()).commit();
            }
        });

        return v;
    }
}
