package com.example.albatross.final_salon_app;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class book_not_member_next<et_time> extends Fragment {
    EditText et_code;
    DatePickerDialog.OnDateSetListener date;
    Button btn_next, btn_cancel;
    final String url_update = Server.URL + "book_not_member.php";
    ImageView img_profile;
    Context context;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;

    final Calendar myCalendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_not_member_next, container, false);
        et_code = v.findViewById(R.id.et_code);
        btn_next = v.findViewById(R.id.btn_next);
        btn_cancel = v.findViewById(R.id.btn_cancel);
        img_profile = v.findViewById(R.id.img_profile);

        if (!Bookhandler.image.equals("")) {
            loadProfile(Bookhandler.image);
        }
        if (!Bookhandler.code.equals("")) {
            et_code.setText(Bookhandler.code);
        }

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_code.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Transaction Code is Required",Toast.LENGTH_SHORT).show();
                }else if(Bookhandler.ImageName.equals("")){
                    Toast.makeText(getActivity(),"Transaction Photo is Required",Toast.LENGTH_SHORT).show();
                }else {


                Bookhandler.code = et_code.getText().toString();
                new UploadFileAsync().execute("");
                new UpdateUser().execute(
                        Bookhandler.customerID,
                        Bookhandler.membership,
                        Bookhandler.serviceId,
                        Bookhandler.date,
                        Bookhandler.time,
                        Bookhandler.code,
                        Bookhandler.ImageName
                );
            }}
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bookhandler.code = et_code.getText().toString();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new book_not_member()).commit();
            }
        });
        return v;
    }


    public class UpdateUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String customerID = strings[0];
            String membership = strings[1];
            String serviceId = strings[2];
            String pdate = strings[3];
            String ptime = strings[4];
            String code = strings[5];
            String image = strings[6];

            String finalURL = url_update +
                    "?cid=" + customerID +
                    "&mem=" + membership +
                    "&sid=" + serviceId +
                    "&date=" + pdate +
                    "&time=" + ptime +
                    "&code=" + code +
                    "&image=" + image;
            Log.e("", finalURL);
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
                            Bookhandler.customerID = "";
                            Bookhandler.membership = "";
                            Bookhandler.serviceId = "";
                            Bookhandler.ImageName = "";
                            Bookhandler.ImageUpload = "";
                            Bookhandler.image = "";
                            Bookhandler.code = "";
                            Bookhandler.date = "";
                            Bookhandler.time = "";

                            showSuccess();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new home_fragment()).commit();

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
            return null;
        }
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

    public void showSuccess() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Success")
                        .setMessage("Successfully booked...")
                        .setPositiveButton(android.R.string.ok, null).show();
            }
        });
    }


    /////////////////////UCROP START
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        Glide.with(this).load(url)
                .into(img_profile);
        img_profile.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Glide.with(this).load(R.drawable.add_image_icon).into(img_profile);
        img_profile.setColorFilter(ContextCompat.getColor(getActivity(), R.color.profile_default_tint));
    }

    @OnClick({R.id.img_profile})
    void onProfileImageClick() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap m_thePic = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    // loading profile image from local cache
                    loadProfile(uri.toString());
                    Bookhandler.image = uri.toString();
                    //Saving crop image
                    Bundle m_extras = data.getExtras();
                    // get the cropped bitmap
                    //Bitmap m_thePic = m_extras.getParcelable("sfd");
                    String m_path = Environment.getExternalStorageDirectory().toString();
                    File m_imgDirectory = new File(m_path + "/Images/");
                    if (!m_imgDirectory.exists()) m_imgDirectory.mkdir();
                    OutputStream m_fOut = null;
                    File m_file = new File(m_path);
                    m_file.delete();
                    String m_fileid = System.currentTimeMillis() + "";
                    m_file = new File(m_path, "/Images/" + m_fileid + ".png");
                    ///Toast.makeText(getActivity(), ""+imagePath, Toast.LENGTH_SHORT).show();
                    Bookhandler.ImageUpload = m_path + "/Images/" + m_fileid + ".png";
                    Bookhandler.ImageName = m_fileid + ".png";
                    //Log.e("ID: ", m_fileid+ ".png");
                    try {
                        if (!m_file.exists()) m_file.createNewFile();
                        m_fOut = new FileOutputStream(m_file);
                        Bitmap m_bitmap = m_thePic.copy(Bitmap.Config.ARGB_8888, true);
                        m_bitmap.compress(Bitmap.CompressFormat.PNG, 100, m_fOut);
                        m_fOut.flush();
                        m_fOut.close();
                        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                                m_file.getAbsolutePath(), m_file.getName(), m_file.getName());
                    } catch (Exception p_e) {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    /////////////////////UCROP STOP
    private class UploadFileAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String sourceFileUri = Bookhandler.ImageUpload;
Log.e("IMAGE UPLAOD", Bookhandler.ImageUpload);
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

    }
}