package com.example.albatross.final_salon_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    final String url_Login = Server.URL + "get_settings.php";
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LoginUser().execute();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View headView = navigationView.getHeaderView(0);
        SharedPreferences pref = this.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String role = pref.getString("u_role", null);


        String users_image = pref.getString("u_picture", null);
        String customer_image = pref.getString("g_picture", null);
        ImageView login_link = headView.findViewById(R.id.nav_head_pic);

        /* sync images */

        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {

            Log.e("LOGIN STATUS: ", String.valueOf(SaveSharedPreference.getLoggedStatus(getApplicationContext())));
            if (role.equals("Admin") || role.equals("Staff")) {
                ///Glide.with(MainActivity.this).load(Server.URL + "images/" + users_image).into(login_link);
            } else {
                ///Glide.with(MainActivity.this).load(Server.URL + "images/" + customer_image).into(login_link);
            }
        } else {
            login_link.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.loginf));
        }

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
                    if (role.equals("Admin") || role.equals("Staff")) {
                        //  Log.e("ROLE2: ",role);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new MyProfileFragment()).commit();
                        drawer.closeDrawer((int) Gravity.LEFT);
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new myprofile_fragment()).commit();
                        drawer.closeDrawer((int) Gravity.LEFT);
                    }
                } else {
                    Intent link_to_login = new Intent(MainActivity.this, login.class);
                    startActivity(link_to_login);
                    finish();
                }
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_home_admin).setVisible(false);
            nav_Menu.findItem(R.id.nav_settings).setVisible(false);
            nav_Menu.findItem(R.id.nav_history).setVisible(true);
            nav_Menu.findItem(R.id.nav_customer_appointment).setVisible(false);
            nav_Menu.findItem(R.id.nav_approve_appointment).setVisible(false);
            nav_Menu.findItem(R.id.nav_appointment).setVisible(false);
        }
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            if (role.equals("")) {
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.nav_history).setVisible(true);
                navMenu.findItem(R.id.nav_home_admin).setVisible(false);
                navMenu.findItem(R.id.nav_settings).setVisible(false);
                navMenu.findItem(R.id.nav_customer_appointment).setVisible(true);
                navMenu.findItem(R.id.nav_approve_appointment).setVisible(false);

            } else if (role.equals("Admin")) {
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.nav_home_admin).setVisible(true);
                navMenu.findItem(R.id.nav_settings).setVisible(true);
                navMenu.findItem(R.id.nav_customer_appointment).setVisible(false);
                navMenu.findItem(R.id.nav_approve_appointment).setVisible(true);
                navMenu.findItem(R.id.nav_home).setVisible(false);
                navMenu.findItem(R.id.nav_appointment).setVisible(true);
                navMenu.findItem(R.id.nav_services).setVisible(false);
                navMenu.findItem(R.id.nav_history).setVisible(true);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_admin_home()).commit();
                navigationView.setCheckedItem(R.id.nav_home_admin);


            } else if (role.equals("Staff")) {
                Menu navMenu = navigationView.getMenu();
                navMenu.findItem(R.id.nav_home).setVisible(true);
                navMenu.findItem(R.id.nav_services).setVisible(false);
                navMenu.findItem(R.id.nav_settings).setVisible(false);
                navMenu.findItem(R.id.nav_appointment).setVisible(true);
                navMenu.findItem(R.id.nav_home_admin).setVisible(false);
                navMenu.findItem(R.id.nav_history).setVisible(true);

                navMenu.findItem(R.id.nav_customer_appointment).setVisible(false);
                navMenu.findItem(R.id.nav_approve_appointment).setVisible(true);

            }
        } else {
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_logout).setVisible(false);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_admin_home()).commit();
                // finish();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fragment()).commit();
                // finish();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new settings_fragment()).commit();
                /// finish();
                break;
            case R.id.nav_services:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new services_fragment()).commit();
                // finish();
                break;
                case R.id.nav_customer_appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_my_book()).commit();
                // finish();
                break;
            case R.id.nav_approve_appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_bookings_accepted()).commit();
                // finish();
                break;
            case R.id.nav_appointment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_bookings()).commit();
                // finish();
                break;
            case R.id.nav_about_us:
                // Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), about_us.class));
                finish();
                break;
            case R.id.nav_contact_information:
               // Toast.makeText(this, "Contact Information", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), contact_information.class));
                finish();
                break;
            case R.id.nav_terms_of_service:
                startActivity(new Intent(getApplicationContext(), termsofservices.class));
                finish();
                break;
                case R.id.nav_history:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_history()).commit();
                    // finish();
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Do you want to logout?")
                        // .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                SaveSharedPreference.setLoggedIn(getApplicationContext(), false);
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
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
                                edt.putString("u_information_id_PK", null);
                                edt.putString("u_firstname", null);
                                edt.putString("u_lastname", null);
                                edt.putString("u_address", null);
                                edt.putString("u_picture", null);
                                edt.putString("u_dateadded", null);
                                edt.putString("u_gender", null);
                                edt.putString("u_fb_name", null);
                                edt.putString("u_gmail", null);
                                edt.putString("u_password", null);
                                edt.putString("u_phone_number", null);
                                edt.putString("u_availability", null);
                                edt.putString("u_status", null);
                                edt.putString("u_role", null);
                                edt.commit();
                                Intent i = new Intent(MainActivity.this,
                                        MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                        SettingHandler.fhr = json.getString("hfrom");
                        SettingHandler.thr = json.getString("hto");
                        SettingHandler.cdate = json.getString("date_close");
                        //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new settings_fragment()).commit();

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("Email or Password incorrect..." + n)
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")


                        .setMessage("Email or Password incorrect...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


}
