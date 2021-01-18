package com.example.albatross.final_salon_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class signup_form2 extends AppCompatActivity {
    Button btnback, btnSignUp;
    CheckBox chk_agreed;
    EditText etEmail, etPass, etCpass;
    TextView txtAgree;
    SignupHandler sh;
    final String url_data = Server.URL + "insert_customer.php";
    ConnectivityManager conMgr;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form2);
        getSupportActionBar().setTitle("Signup Form");
        sh = new SignupHandler();
        txtAgree = findViewById(R.id.agree);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        etCpass = findViewById(R.id.et_cpassword);
        btnback = findViewById(R.id.btn_back);
        btnSignUp = findViewById(R.id.btn_signup);
        etEmail.setText(sh.email);
        etPass.setText(sh.password);
        etCpass.setText(sh.cpassword);
        chk_agreed = (CheckBox) findViewById(R.id.chk_agree);
        conMgr = (ConnectivityManager) signup_form2.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sh.email = etEmail.getText().toString();
                sh.password = etPass.getText().toString();
                sh.cpassword = etCpass.getText().toString();
                startActivity(new Intent(getApplicationContext(), sign_up.class));
                finish();
            }
        });

        txtAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), terms_and_service.class));
                finish();
            }
        });

        chk_agreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_agreed.isChecked()) {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                    btnSignUp.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         String email = etEmail.getText().toString();
                                                         String pass = etPass.getText().toString();
                                                         if (TextUtils.isEmpty(etEmail.getText())) {
                                                             etEmail.setError("Email or username");
                                                             etEmail.requestFocus();
                                                         } else if (TextUtils.isEmpty(etPass.getText())) {
                                                             etPass.setError("Password");
                                                             etPass.requestFocus();
                                                         } else if (TextUtils.isEmpty(etCpass.getText())) {
                                                             etCpass.setError("Confirm Password");
                                                             etCpass.requestFocus();
                                                         } else if (!etPass.getText().toString().equals(etCpass.getText().toString())) {
                                                             Toast.makeText(signup_form2.this, "Password not match", Toast.LENGTH_SHORT).show();

                                                         } else {
                                                             if (conMgr.getActiveNetworkInfo() != null
                                                                     && conMgr.getActiveNetworkInfo().isAvailable()
                                                                     && conMgr.getActiveNetworkInfo().isConnected()) {
                                                                 pDialog = new ProgressDialog(signup_form2.this);
                                                                 pDialog.setCancelable(false);
                                                                 pDialog.setMessage("Adding customer...");
                                                                 showDialog();
                                                                 new UploadFileAsync().execute("");
                                                                 new InsertCustomer().execute(sh.firstname, sh.lastname, sh.contact, sh.facebook, sh.gender, email, pass, sh.ImageName);
                                                             }
                                                               }
                                                     }
                                                 }
                    );
                } else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

        });

    }


    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String sourceFileUri = sh.ImageUpload;

                int serverResponseCode = 0;
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(sourceFileUri);

                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = Server.URL + "add_customer_image.php?";

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("bill", sourceFileUri);

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn
                                .getResponseMessage();

                        if (serverResponseCode == 200) {

                            // messageText.setText(msg);
                            //Toast.makeText(ctx, "File Upload Complete.",
                            //      Toast.LENGTH_SHORT).show();

                            // recursiveDelete(mDirectory1);

                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        // dialog.dismiss();
                        e.printStackTrace();

                    }
                    // dialog.dismiss();

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }////////END OF IMAGE UPLOAD

    public class InsertCustomer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {


            String firstname = strings[0];
            String lastname = strings[1];
            String contact = strings[2];
            String facebook = strings[3];
            String gender = strings[4];
            String email = strings[5];
            String password = strings[6];
            String ImageName = strings[7];

            String finalURL = url_data +
                    "?firstname=" + firstname +
                    "&lastname=" + lastname +
                    "&contact=" + contact +
                    "&facebook=" + facebook +
                    "&gender=" + gender +
                    "&email=" + email +
                    "&password=" + password +
                    "&ImageName=" + ImageName;
            Log.e("INSERT DATA OF CUSTOMER", finalURL);
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
                            //showToast("Pet successfully added");

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                             }else if(result.equalsIgnoreCase("exist")) {
                            showExist();
                        }else  {
                             showDialogs();
                        }
                    }
                } catch (Exception e) {
                    showDialogs();
                    e.printStackTrace();
                }
            } catch (Exception e) {
                showDialogs();
                e.printStackTrace();
            }

            hideDialog();
            return null;
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void showDialogs() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(signup_form2.this)
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showExist() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(signup_form2.this)
                        .setTitle("Error")
                        .setMessage("Customer already exist...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }
    public void showMessage() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(signup_form2.this)
                        .setTitle("Not a member")
                        .setMessage("You're not a member...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

}
