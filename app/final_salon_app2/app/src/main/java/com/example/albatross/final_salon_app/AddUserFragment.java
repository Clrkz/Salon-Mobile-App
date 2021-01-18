package com.example.albatross.final_salon_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static android.content.Context.MODE_PRIVATE;

public class AddUserFragment extends Fragment {
    TextView et_email, et_firstname, et_lastname, et_contact_number, et_facebook_username, et_gender;
    Button btnUpdate, btnSignout;
    ImageView imgProfile;
    UpdateHandler uh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myprofiles, container, false);


        et_email = v.findViewById(R.id.et_email);
        et_firstname = v.findViewById(R.id.et_firstname);
        et_lastname = v.findViewById(R.id.et_lastname);
        et_contact_number = v.findViewById(R.id.et_contact_number);
        et_facebook_username = v.findViewById(R.id.et_facebook_username);
        et_gender = v.findViewById(R.id.et_gender);
        btnSignout = v.findViewById(R.id.btnSignout);
        imgProfile = v.findViewById(R.id.img_profile);
        btnUpdate = v.findViewById(R.id.btnUpdate);
        uh = new UpdateHandler();

        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", MODE_PRIVATE);
        String guest_id_PK = pref.getString("guest_id_PK", null);
        String g_firstname = pref.getString("g_firstname", null);
        String g_lastname = pref.getString("g_lastname", null);
        String g_picture = pref.getString("g_picture", null);
        String g_date_time_added = pref.getString("g_date_time_added", null);
        String g_gender = pref.getString("g_gender", null);
        String g_contact_number = pref.getString("g_contact_number", null);
        String g_fb_name = pref.getString("g_fb_name", null);
        String g_gmail = pref.getString("g_gmail", null);
        String g_status = pref.getString("g_status", null);
        et_firstname.setText(g_firstname);
        et_lastname.setText(g_lastname);
        et_email.setText(g_gmail);
        et_contact_number.setText(g_contact_number);
        et_facebook_username.setText(g_fb_name);
        et_gender.setText(g_gender);
        Glide.with(getActivity()).load(Server.URL + "images/" + g_picture).into(imgProfile);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uh.image = g_picture;
                uh.firstname = g_firstname;
                uh.lastname = g_lastname;
                uh.contact = g_contact_number;
                uh.facebook = g_fb_name;
                uh.gender = g_gender;
                uh.email = g_gmail;
                uh.ID = guest_id_PK;

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new update_profile_fragment()).commit();
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Do you want to logout?")
                        // .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                SaveSharedPreference.setLoggedIn(getActivity(), false);
                                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                                SharedPreferences.Editor edt = pref.edit();
                                edt.putString("guest_id_PK", null);
                                edt.putString("g_firstname", null);
                                edt.putString("g_lastname", null);
                                edt.putString("g_picture", null);
                                edt.putString("g_date_time_added", null);
                                edt.putString("g_gender", null);
                                edt.putString("g_contact_number", null);
                                edt.putString("g_fb_name", null);
                                edt.putString("g_gmail", null);
                                edt.putString("g_status", null);
                                edt.commit();
                                Intent i = new Intent(getActivity(),
                                        MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();



            }//////
        });

        return v;

    }



}




