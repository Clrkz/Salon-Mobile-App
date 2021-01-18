package com.example.albatross.final_salon_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class UpdateUserProfileNext extends Fragment {
    EditText et_contact_number1, et_fb, et_email, et_password, et_cpassword;
    Button btn_back, btn_update;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    final String url_data = Server.URL + "update_user.php";
    String u_role;
    String u_gmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_users_next, container, false);
        et_contact_number1 = v.findViewById(R.id.et_contact_number);
        et_fb = v.findViewById(R.id.et_fb);
        et_email = v.findViewById(R.id.et_email);
        et_password = v.findViewById(R.id.et_password);
        et_cpassword = v.findViewById(R.id.et_cpassword);
        btn_back = v.findViewById(R.id.btn_back);
        btn_update = v.findViewById(R.id.btn_update);


        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", MODE_PRIVATE);

        String id = pref.getString("u_information_id_PK", null);
        String u_firstname = pref.getString("u_firstname", null);
        String u_lastname = pref.getString("u_lastname", null);
        String u_address = pref.getString("u_address", null);
        String u_picture = pref.getString("u_picture", null);
        String u_dateadded = pref.getString("u_dateadded", null);
        String u_gender = pref.getString("u_gender", null);
        String u_fb_name = pref.getString("u_fb_name", null);
        u_gmail = pref.getString("u_gmail", null);
        String u_password = pref.getString("u_password", null);
        String u_phone_number = pref.getString("u_phone_number", null);
        String u_availability = pref.getString("u_availability", null);
        String u_status = pref.getString("u_status", null);
        u_role = pref.getString("u_role", null);

        if (addUsersHandler.contact.equals("")) {
            et_contact_number1.setText(u_phone_number);
        } else {
            et_contact_number1.setText(addUsersHandler.contact);
        }

        if (addUsersHandler.facebook.equals("")) {
            et_fb.setText(u_fb_name);
        } else {
            et_fb.setText(addUsersHandler.facebook);
        }


        if (addUsersHandler.email.equals("")) {
            et_email.setText(u_gmail);
        } else {
            et_email.setText(addUsersHandler.email);
        }

        et_password.setText(addUsersHandler.password);
        et_cpassword.setText(addUsersHandler.cpassword);


        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_contact_number1.getText())) {
                    et_contact_number1.setError("Contact number");
                    et_contact_number1.requestFocus();
                } else if (TextUtils.isEmpty(et_fb.getText())) {
                    et_fb.setError("Facebook");
                    et_fb.requestFocus();
                } else if (TextUtils.isEmpty(et_email.getText())) {
                    et_email.setError("Email or username");
                    et_email.requestFocus();
                }
                /*else if (TextUtils.isEmpty(et_password.getText())) {
                    et_password.setError("Password");
                    et_password.requestFocus();
                } else if (TextUtils.isEmpty(et_cpassword.getText())) {
                    et_cpassword.setError("Confirm Password");
                    et_cpassword.requestFocus();
                }*/
                else if (et_password.getText().toString().equals(et_cpassword.getText().toString())) {
                    String email = et_email.getText().toString();
                    String pass = et_password.getText().toString();
                    //    addUsersHandler.contact = et_contact_number1.getText().toString();
                    //   addUsersHandler.facebook = et_fb.getText().toString();
                    String contact = et_contact_number1.getText().toString();
                    String facebook = et_fb.getText().toString();
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        pDialog = new ProgressDialog(getActivity());
                        pDialog.setCancelable(false);
                        pDialog.setMessage("Updating user...");
                        showDialog();
                        new UploadFileAsync().execute("");
                        if (addUsersHandler.ImageUpload.equals("")) {
                            addUsersHandler.ImageName = u_picture;
                        }
                        new InsertCustomer().execute(addUsersHandler.firstname, addUsersHandler.lastname, addUsersHandler.address, addUsersHandler.ImageName, addUsersHandler.gender, facebook, email, pass, contact, addUsersHandler.role, id, u_gmail);
                    }
                } else {
                    showToast("Password not match");
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUsersHandler.contact = et_contact_number1.getText().toString();
                addUsersHandler.facebook = et_fb.getText().toString();
                addUsersHandler.email = et_email.getText().toString();
                addUsersHandler.password = et_password.getText().toString();
                addUsersHandler.cpassword = et_cpassword.getText().toString();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateUserProfile()).commit();
            }
        });


        return v;
    }

    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String sourceFileUri = addUsersHandler.ImageUpload;

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

            String firstname = strings[0]; //addUsersHandler.firstname,
            String lastname = strings[1]; //addUsersHandler.lastname,
            String address = strings[2]; //addUsersHandler.address,
            String ImageName = strings[3]; //addUsersHandler.ImageName,
            String gender = strings[4]; //addUsersHandler.gender,
            String facebook = strings[5]; //facebook,
            String email = strings[6]; //email,
            String password = strings[7]; //pass,
            String contact = strings[8]; // addUsersHandler.contact,
            String role = strings[9]; //contact,
            String id = strings[10]; //addUsersHandler.role,
            String oemail = strings[11];
            //id


            String finalURL = url_data +
                    "?firstname=" + firstname +
                    "&lastname=" + lastname +
                    "&contact=" + contact +
                    "&facebook=" + facebook +
                    "&gender=" + gender +
                    "&email=" + email +
                    "&password=" + password +
                    "&address=" + address +
                    "&role=" + role +
                    "&ImageName=" + ImageName +
                    "&id=" + id +
                    "&oemail=" + oemail;


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
                            // showToast("Pet successfully added");
                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            edt.putString("u_firstname", firstname);
                            edt.putString("u_lastname", lastname);
                            edt.putString("u_address", address);
                            edt.putString("u_picture", ImageName);
                            edt.putString("u_gender", gender);
                            edt.putString("u_fb_name", facebook);
                            edt.putString("u_gmail", email);
                            edt.putString("u_phone_number", contact);
                            edt.putString("u_role", role);
                            edt.commit();

                            addUsersHandler.firstname = "";
                            addUsersHandler.lastname = "";
                            addUsersHandler.contact = "";
                            addUsersHandler.facebook = "";
                            addUsersHandler.address = "";
                            addUsersHandler.gender = "";
                            addUsersHandler.email = "";
                            addUsersHandler.password = "";
                            addUsersHandler.role = "";
                            addUsersHandler.cpassword = "";
                            addUsersHandler.ImageUpload = "";
                            addUsersHandler.ImageName = "";
                            addUsersHandler.image = "";
                            showToast("Successfully updated...");


                            if (u_role.equals(role)) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new fragment_admin_home()).commit();
                            } else {
                                SaveSharedPreference.setLoggedIn(getActivity().getApplicationContext(), false);
                                pref = getActivity().getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                                edt = pref.edit();
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
                                login.flag = true;
                                Intent i = new Intent(getActivity(),
                                        login.class);
                                startActivity(i);
                            }

                        } else if (result.equalsIgnoreCase("exist")) {
                            showExist();
                        } else {
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

    public void showToast(final String Text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDialogs() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("Error has occured, Try again...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showExist() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Error")
                        .setMessage("User email already exist...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

}
