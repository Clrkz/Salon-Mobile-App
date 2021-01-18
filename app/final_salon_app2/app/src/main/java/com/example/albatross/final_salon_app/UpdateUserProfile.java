package com.example.albatross.final_salon_app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class UpdateUserProfile extends Fragment {
    Button btn_cancel, btn_next;
    TextView et_firstname, et_lastname, et_contact_number;
    RadioButton rd_admin, rd_staff, rd_male, rd_female;
    RadioGroup radioGroup;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    @BindView(R.id.img_profile)
    ImageView img_profile;
    RadioGroup rg;
    RadioButton radioButton;
    RadioGroup rg1;
    RadioButton radioButton1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update_users, container, false);

        btn_cancel = v.findViewById(R.id.btn_cancel);
        btn_next = v.findViewById(R.id.btn_next);
        et_firstname = v.findViewById(R.id.et_firstname);
        et_lastname = v.findViewById(R.id.et_lastname);
        et_contact_number = v.findViewById(R.id.et_contact_number);
        img_profile = v.findViewById(R.id.img_profile);
        rg = v.findViewById(R.id.rdg_gender);
        rd_male = v.findViewById(R.id.rd_male);
        rd_female = v.findViewById(R.id.rd_female);
        rg1 = v.findViewById(R.id.rdg_users);
        rd_admin = v.findViewById(R.id.rd_admin);
        rd_staff = v.findViewById(R.id.rd_staff);
        img_profile = v.findViewById(R.id.img_profile);
        ImagePickerActivity.clearCache(getActivity());
        radioGroup = (RadioGroup) v.findViewById(R.id.rdg_gender);


        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageClick();
            }
        });


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


        if (addUsersHandler.firstname.equals("")) {
            et_firstname.setText(u_firstname);
        } else {
            et_firstname.setText(addUsersHandler.firstname);
        }

        if (addUsersHandler.lastname.equals("")) {
            et_lastname.setText(u_lastname);
        } else {
            et_lastname.setText(addUsersHandler.lastname);
        }

        if (addUsersHandler.address.equals("")) {
            et_contact_number.setText(u_address);
        } else {
            et_contact_number.setText(addUsersHandler.address);
        }

        if (addUsersHandler.gender.equals("")) {
            if (u_gender.equals("Male")) {
                rd_male.setChecked(true);
            } else if (u_gender.equals("Female")) {
                rd_female.setChecked(true);
            }
        } else {
            if (addUsersHandler.gender.equals("Male")) {
                rd_male.setChecked(true);
            } else if (addUsersHandler.gender.equals("Female")) {
                rd_female.setChecked(true);
            }
        }

        if (addUsersHandler.role.equals("")) {

            if (u_role.equals("Admin")) {
                rd_admin.setChecked(true);
            } else if (u_role.equals("Staff")) {
                rd_staff.setChecked(true);
            }
        } else {

            if (addUsersHandler.role.equals("Admin")) {
                rd_admin.setChecked(true);
            } else if (addUsersHandler.role.equals("Staff")) {
                rd_staff.setChecked(true);
            }
        }


        if (addUsersHandler.ImageUpload.equals("")) {
            if (!u_picture.equals("")) {
                Glide.with(getActivity()).load(Server.URL + "images/" + u_picture).into(img_profile);
                //img_profile.setVisibility(View.INVISIBLE);
            }
        } else {
            if (!addUsersHandler.image.equals("")) {
                loadProfile(addUsersHandler.image);
            }
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_firstname.getText())) {
                    et_firstname.setError("First name");
                    et_firstname.requestFocus();
                }
                else if (TextUtils.isEmpty(et_lastname.getText())) {
                    et_lastname.setError("Last name");
                    et_lastname.requestFocus();
                }
                else if (TextUtils.isEmpty(et_contact_number.getText())) {
                    et_contact_number.setError("Address");
                    et_contact_number.requestFocus();
                }
                else {
                    addUsersHandler.firstname = et_firstname.getText().toString();
                    addUsersHandler.lastname = et_lastname.getText().toString();
                    addUsersHandler.address = et_contact_number.getText().toString();
                    int selectedId = rg.getCheckedRadioButtonId();
                    int selectedRole = rg1.getCheckedRadioButtonId();
                    int abs = Math.abs(selectedId);
                    int abs2 = Math.abs(selectedRole);
                    if (abs2 == 1) {
                        new AlertDialog.Builder(getActivity())
                                //  .setTitle("Failed")
                                .setMessage("Please Select Role")
                                .setPositiveButton(android.R.string.ok, null).show();
                    } else if (abs == 1) {
                        new AlertDialog.Builder(getActivity())
                                // .setTitle("Failed")
                                .setMessage("Please Select Gender")
                                .setPositiveButton(android.R.string.ok, null).show();
                    } else {
                        radioButton = (RadioButton) getActivity().findViewById(selectedId);
                        addUsersHandler.gender = radioButton.getText().toString();
                        radioButton1 = (RadioButton) getActivity().findViewById(selectedRole);
                        addUsersHandler.role = radioButton1.getText().toString();
                        //  Toast.makeText(getActivity(), ""+addUsersHandler.role, Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpdateUserProfileNext()).commit();
                    }

                }
            }
        });

        return v;
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
                    addUsersHandler.image = uri.toString();
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
                    addUsersHandler.ImageUpload = m_path + "/Images/" + m_fileid + ".png";
                    addUsersHandler.ImageName = m_fileid + ".png";
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
