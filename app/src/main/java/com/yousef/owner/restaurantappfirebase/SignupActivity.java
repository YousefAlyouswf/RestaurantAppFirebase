package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.os.Bundle;
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

public class SignupActivity extends AppCompatActivity {

    EditText phoneText, nameText, passText;
    Button btnReg;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phoneText = findViewById(R.id.phoneText);
        nameText = findViewById(R.id.nameText);
        passText = findViewById(R.id.passText);
        btnReg = findViewById(R.id.signUn2);
        progressBar = findViewById(R.id.waiting);
        progressBar.setVisibility(View.INVISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference("User");

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(10);
                animation1.setStartOffset(100);
                animation1.setFillAfter(true);
                v.startAnimation(animation1);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        //Check if user exist
                        if (dataSnapshot.child(phoneText.getText().toString()).exists()) {
                            Toast.makeText(SignupActivity.this, "الرقم مسجل مسبقا", Toast.LENGTH_SHORT).show();

                        } else {
                            User user = new User(nameText.getText().toString(), passText.getText().toString());
                            reference.child(phoneText.getText().toString()).setValue(user);
                            Toast.makeText(SignupActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                            Common.currentUser=user;
                            Intent intent = new Intent(SignupActivity.this,Home.class);
                            startActivity(intent);
                        }

                        progressBar.setVisibility(View.INVISIBLE);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
