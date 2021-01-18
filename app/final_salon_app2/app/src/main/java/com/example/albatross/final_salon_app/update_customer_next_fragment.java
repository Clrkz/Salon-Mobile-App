package com.example.albatross.final_salon_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class update_customer_next_fragment extends Fragment {
    Button btnback, btnSignUp;
    EditText etEmail, etPass, etCpass;
    UpdateHandler uh;
    final String url_data = Server.URL + "update_customer.php";
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    String oemail;
    JSONObject json;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_update_profile_next1, container, false);

        uh = new UpdateHandler();
        etEmail = v.findViewById(R.id.et_email);
        etPass = v.findViewById(R.id.et_password);
        etCpass = v.findViewById(R.id.et_cpassword);
        btnback = v.findViewById(R.id.btn_back);
        btnSignUp = v.findViewById(R.id.btn_update);
        etEmail.setText(uh.email);
        etPass.setText(uh.password);
        etCpass.setText(uh.cpassword);
        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uh.email = etEmail.getText().toString();
                uh.password = etPass.getText().toString();
                uh.cpassword = etCpass.getText().toString();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new update_customer_fragment()).commit();
            }
        });

        SharedPreferences pref = this.getActivity().getSharedPreferences("USER_INFO", MODE_PRIVATE);
         oemail = pref.getString("g_gmail", null);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass =  etPass.getText().toString();
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    pDialog = new ProgressDialog(getActivity());
                    pDialog.setCancelable(false);
                    pDialog.setMessage("Updating customer...");
                    showDialog();
                    String image;
                    if(uh.ImageName.equals("")){
                        image = uh.image;
                    }else{
                        new UploadFileAsync().execute("");
                        image = uh.ImageName;
                    }
                    new InsertCustomer().execute(uh.firstname,uh.lastname,uh.contact,uh.facebook,uh.gender,email,pass,image,uh.ID, UpdateHandler.email);
                }

            }
        });

        return v;
    }



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
            String id = strings[8];
            String oemail = strings[9];

            String finalURL = url_data +
                    "?firstname=" + firstname +
                    "&lastname=" + lastname +
                    "&contact=" + contact +
                    "&facebook=" + facebook +
                    "&gender=" + gender +
                    "&email=" + email +
                    "&password=" + password +
                    "&ImageName=" + ImageName +
                    "&id=" + id+
                    "&oemail=" + oemail ;
            Log.e("UPDATE DATA OF CUSTOMER", finalURL );
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
                        if (result.equalsIgnoreCase("success")){
                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("USER_INFO", MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            /// To add value in preference:
                            edt.putString("g_firstname", firstname);
                            edt.putString("g_lastname", lastname);
                            edt.putString("g_picture", ImageName);
                            edt.putString("g_gender", gender);
                            edt.putString("g_contact_number", contact);
                            edt.putString("g_fb_name", facebook);
                            edt.putString("g_gmail", email);
                            edt.commit();
                            showSuccess();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new fragment_all_customer()).commit();
                        } else if(result.equalsIgnoreCase("exist")) {
                            showExist();
                        }else{
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



    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String sourceFileUri = uh.ImageUpload;

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
                        String upLoadServerUri = Server.URL+"add_customer_image.php?";

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
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
                        .setMessage("Customer email already exist...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }

    public void showSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Success")
                        .setMessage("Profile successfully updated...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


}
