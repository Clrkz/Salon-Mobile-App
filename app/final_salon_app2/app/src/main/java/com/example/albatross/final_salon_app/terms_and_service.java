package com.example.albatross.final_salon_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class terms_and_service extends AppCompatActivity {
Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_service);
        getSupportActionBar().setTitle("Terms of services");

       btnback = (Button)findViewById(R.id.btn_back);
       btnback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), signup_form2.class));
               finish();
           }
       });


    }
}
