package com.example.albatross.final_salon_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {
    Button btnSignUpForm, btnLogin, btnCancel;
    EditText etEmail, etPassword;
    SignupHandler sh;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    final String url_Login = Server.URL + "login_user.php";
    JSONObject json;
public static boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Form");
if(flag){
    showAccess();
    flag = false;
}
        sh = new SignupHandler();
        sh.image = "";
        sh.firstname = "";
        sh.lastname = "";
        sh.contact = "";
        sh.facebook = "";
        sh.gender = "";
        sh.email = "";
        sh.password = "";
        sh.cpassword = "";
        sh.ImageUpload = "";
        sh.ImageName = "";
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        etEmail = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSignUpForm = findViewById(R.id.btn_signupForm);
       /*etEmail.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etEmail.getRight() - etEmail.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        etEmail.setText("");
                        return true;
                    }
                }
                return false;
            }
        });*/



        btnSignUpForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), sign_up.class));
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();

                if (TextUtils.isEmpty(etEmail.getText())) {
                    etEmail.setError("Enter username");
                    etEmail.requestFocus();
                } else if (TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.setError("Enter password");
                    etPassword.requestFocus();
                } else {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        pDialog = new ProgressDialog(login.this);
                        pDialog.setCancelable(false);
                        pDialog.setMessage("Logging in...");
                        showDialog();
                        new LoginUser().execute(Email, Password);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }



  /* public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), sign_up.class));
        finish();
    }*/

    /*
    public class LoginUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("g_gmail", Email)
                    .add("g_password", Password)
                    .build();
            Request request = new Request.Builder()
                    .url(url_Login)
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();
                    json = new JSONObject(result);
                    int success = json.getInt("success");
                    if(success==1){
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref.edit();
                        /// To add value in preference:
                        String guest_id_PK = json.getString("guest_id_PK");
                        String g_firstname = json.getString("g_firstname");
                        String g_lastname = json.getString("g_lastname");
                        String g_picture =json.getString("g_picture");
                        String g_date_time_added =  json.getString("g_date_time_added");
                        String g_gender = json.getString("g_gender");
                        String g_contact_number = json.getString("g_contact_number");
                        String g_fb_name =json.getString("g_fb_name");
                        String g_gmail = json.getString("g_gmail");
                        String g_status = json.getString("g_status");

                        edt.putString("guest_id_PK", guest_id_PK);
                        edt.putString("g_firstname", g_firstname);
                        edt.putString("g_lastname", g_lastname);
                        edt.putString("g_picture", g_picture);
                        edt.putString("g_date_time_added", g_date_time_added);
                        edt.putString("g_gender", g_gender);
                        edt.putString("g_contact_number", g_contact_number);
                        edt.putString("g_fb_name", g_fb_name);
                        edt.putString("g_gmail", g_gmail);
                        edt.putString("g_status",g_status);
                        edt.commit();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new myprofile_fragment()).commit();
                        finish();
                    }else if(success==0){
                        showFailed();
                    }
                }else{
                    showError();
                }
                hideDialog();
            }catch (Exception e){
                hideDialog();
                showError();
                e.printStackTrace();
            }
            return null;
        }

    }
*/
    public class LoginUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
            Log.e("EMAIL PASS", Email + " " + Password);
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("g_gmail", Email)
                    .add("g_password", Password)
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
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref.edit();
                        /// To add value in preference:
                        String guest_id_PK = json.getString("guest_id_PK");
                        String g_firstname = json.getString("g_firstname");
                        String g_lastname = json.getString("g_lastname");
                        String g_picture = json.getString("g_picture");
                        String g_date_time_added = json.getString("g_date_time_added");
                        String g_gender = json.getString("g_gender");
                        String g_contact_number = json.getString("g_contact_number");
                        String g_fb_name = json.getString("g_fb_name");
                        String g_gmail = json.getString("g_gmail");
                        String g_status = json.getString("g_status");

                        edt.putString("guest_id_PK", guest_id_PK);
                        edt.putString("g_firstname", g_firstname);
                        edt.putString("g_lastname", g_lastname);
                        edt.putString("g_picture", g_picture);
                        edt.putString("g_date_time_added", g_date_time_added);
                        edt.putString("g_gender", g_gender);
                        edt.putString("g_contact_number", g_contact_number);
                        edt.putString("g_fb_name", g_fb_name);
                        edt.putString("g_gmail", g_gmail);
                        edt.putString("g_status", g_status);
                        edt.putString("u_role", "");
                        edt.commit();
                        // showToast(id+ ": "+pass);
                        Intent i = new Intent(login.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();
                    } else if (success == 2) {
                        SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref.edit();
                        /// To add value in preference:
                        String u_information_id_PK = json.getString("u_information_id_PK");
                        String u_firstname = json.getString("u_firstname");
                        String u_lastname = json.getString("u_lastname");
                        String u_address = json.getString("u_address");
                        String u_picture = json.getString("u_picture");
                        String u_dateadded = json.getString("u_dateadded");
                        String u_gender = json.getString("u_gender");
                        String u_fb_name = json.getString("u_fb_name");
                        String u_gmail = json.getString("u_gmail");
                        String u_password = json.getString("u_password");
                        String u_phone_number = json.getString("u_phone_number");
                        String u_availability = json.getString("u_availability");
                        String u_status = json.getString("u_status");
                        String u_role = json.getString("u_role");

                        edt.putString("u_information_id_PK", u_information_id_PK);
                        edt.putString("u_firstname", u_firstname);
                        edt.putString("u_lastname", u_lastname);
                        edt.putString("u_address", u_address);
                        edt.putString("u_picture", u_picture);
                        edt.putString("u_dateadded", u_dateadded);
                        edt.putString("u_gender", u_gender);
                        edt.putString("u_fb_name", u_fb_name);
                        edt.putString("u_gmail", u_gmail);
                        edt.putString("u_password", u_password);
                        edt.putString("u_phone_number", u_phone_number);
                        edt.putString("u_availability", u_availability);
                        edt.putString("u_status", u_status);
                        edt.putString("u_role", u_role);
                        edt.commit();
                        // showToast(id+ ": "+pass);
                        Intent i = new Intent(login.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();

                    } else if (success == 0) {
                        showFailed();
                    }
                } else {
                    showError();
                }
                hideDialog();
            } catch (Exception e) {
                hideDialog();
                showError();
                e.printStackTrace();
            }
            return null;
        }

    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void showToast(int n) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(login.this)
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
                new AlertDialog.Builder(login.this)
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
                new AlertDialog.Builder(login.this)
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(login.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showAccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(login.this)
                        .setTitle("Role updated")
                        .setMessage("You have to login again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
}