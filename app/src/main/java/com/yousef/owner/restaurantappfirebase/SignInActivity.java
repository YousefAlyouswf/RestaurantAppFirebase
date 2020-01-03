package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yousef.owner.restaurantappfirebase.Model.User;
import com.yousef.owner.restaurantappfirebase.common.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

public class SignInActivity extends AppCompatActivity {


    EditText phoneEText, passEText;
    Button btnSignin;
    ProgressBar progressBar;
    com.rey.material.widget.CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phoneEText = findViewById(R.id.phoneText);
        passEText = findViewById(R.id.passText);
        checkBox = findViewById(R.id.chRembmer);
        btnSignin = findViewById(R.id.signIn2);
        progressBar = findViewById(R.id.waiting);
        progressBar.setVisibility(View.INVISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference("User");



        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(10);
                animation1.setStartOffset(100);
                animation1.setFillAfter(true);
                v.startAnimation(animation1);


                if (Common.isNetworkAvailable(getBaseContext())) {
                    try {Paper.init(SignInActivity.this);
                        if (checkBox.isChecked()) {
                            Paper.book().write(Common.USER_KEY, phoneEText.getText().toString());
                            Paper.book().write(Common.PWD_KEY, passEText.getText().toString());
                        }
                    } catch (Exception e) {
                        Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }


                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            if (phoneEText.getText().toString().equals("") || passEText.getText().toString().equals("")) {
                                Toast.makeText(SignInActivity.this, "أدخل رقم الجوال والرقم السري", Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                                //Check if user exist
                                if (dataSnapshot.child(phoneEText.getText().toString()).exists()) {
                                    //Get user info
                                    User user = dataSnapshot.child(phoneEText.getText().toString()).getValue(User.class);
                                    assert user != null;
                                    user.setPhone(phoneEText.getText().toString());
                                    if (user.getPassword().equals(passEText.getText().toString())) {

                                        Intent intent = new Intent(SignInActivity.this, Home.class);
                                        Common.currentUser = user;
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "المعلومات خاطئة", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(SignInActivity.this, "الرقم غير مسجل", Toast.LENGTH_SHORT).show();
                                }
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.INVISIBLE);

                                    }

                                }, 500);

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {

                    Toast.makeText(SignInActivity.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });


    }


}
