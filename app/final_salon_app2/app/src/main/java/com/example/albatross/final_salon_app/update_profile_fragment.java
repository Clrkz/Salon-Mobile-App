package com.example.albatross.final_salon_app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class update_profile_fragment extends Fragment {
    Button btnNext, link_login;
    EditText etFname, etLname, etContact, etFb;
    RadioButton rdmale, rdFemale;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    UpdateHandler uh;
    RadioGroup rg;
    RadioButton radioButton;
    Button btnCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_profile, container, false);
        uh = new UpdateHandler();
        imgProfile = v.findViewById(R.id.img_profile);
        etFname = v.findViewById(R.id.et_firstname);
        etLname = v.findViewById(R.id.et_lastname);
        etContact = v.findViewById(R.id.et_contact_number);
        etFb = v.findViewById(R.id.et_facebook_username);
        rdmale = v.findViewById(R.id.rd_male);
        rdFemale = v.findViewById(R.id.rd_female);
        btnNext = v.findViewById(R.id.btn_next);
        rg = v.findViewById(R.id.rdg_gender);
        imgProfile = v.findViewById(R.id.img_profile);
        btnCancel = v.findViewById(R.id.btn_cancel);
        //loadProfileDefault();
        ImagePickerActivity.clearCache(getActivity());

        if (!uh.ImageName.equals("")) {
            loadProfile(uh.ImageName);
        } else {
            Glide.with(getActivity()).load(Server.URL + "images/" + uh.image).into(imgProfile);
        }


        etFname.setText(uh.firstname);
        etLname.setText(uh.lastname);
        etContact.setText(uh.contact);
        etFb.setText(uh.facebook);
        if (uh.gender.equals("Male")) {
            rdmale.setChecked(true);
        } else if (uh.gender.equals("Female")) {
            rdFemale.setChecked(true);
        } else {
            rdmale.setChecked(false);
            rdFemale.setChecked(false);
        }


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new myprofile_fragment()).commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uh.firstname = etFname.getText().toString();
                uh.lastname = etLname.getText().toString();
                uh.contact = etContact.getText().toString();
                uh.facebook = etFb.getText().toString();
                int selectedId = rg.getCheckedRadioButtonId();
                int abs = Math.abs(selectedId);
                if (abs != 1) {
                    radioButton = (RadioButton) v.findViewById(selectedId);
                    //  uh.gender = radioButton.getText().toString();
                    if (rdmale.isChecked()) {
                        uh.gender = "Male";
                    } else if (rdFemale.isChecked()) {
                        uh.gender = "Female";
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new update_profile_next_fragment()).commit();
                } else {
                    new AlertDialog.Builder(getActivity())
                            //  .setTitle("Failed")
                            .setMessage("Please Select Gender")
                            .setPositiveButton(android.R.string.ok, null).show();
                }
            }
        });


        return v;
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Glide.with(this).load(url)
                .into(imgProfile);
        imgProfile.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.transparent));
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
                    uh.image = uri.toString();

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
                    uh.ImageUpload = m_path + "/Images/" + m_fileid + ".png";
                    uh.ImageName = m_fileid + ".png";
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


}


