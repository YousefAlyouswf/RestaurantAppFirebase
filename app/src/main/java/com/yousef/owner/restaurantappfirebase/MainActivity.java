package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    Button signin, signup;
    TextView logotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin = findViewById(R.id.signIn);
        signup = findViewById(R.id.signUp);
        logotext = findViewById(R.id.textLogo);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ebnile.ttf");
        logotext.setTypeface(typeface);


//if user checkedbox go to Home Directly
        Paper.init(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(10);
                animation1.setStartOffset(100);
                animation1.setFillAfter(true);
                v.startAnimation(animation1);
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }


        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(10);
                animation1.setStartOffset(100);
                animation1.setFillAfter(true);
                v.startAnimation(animation1);
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pass = Paper.book().read(Common.PWD_KEY);
        if (user != null && pass != null) {
            if (!user.isEmpty() && !pass.isEmpty()) {
                login(user, pass);
            }
        }

    }

    private void login(final String user, final String pass) {
        if (Common.isNetworkAvailable(getBaseContext())) {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference reference = firebaseDatabase.getReference("User");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    if (user.equals("") || pass.equals("")) {
                        Toast.makeText(MainActivity.this, "أدخل رقم الجوال والرقم السري", Toast.LENGTH_SHORT).show();
                    } else {
                        //Check if user exist
                        if (dataSnapshot.child(user).exists()) {
                            //Get user info
                            User user1 = dataSnapshot.child(user).getValue(User.class);
                            assert user1 != null;
                            user1.setPhone(user);
                            if (user1.getPassword().equals(pass)) {

                                Intent intent = new Intent(MainActivity.this, Home.class);
                                Common.currentUser = user1;
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "المعلومات خاطئة", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "الرقم غير مسجل", Toast.LENGTH_SHORT).show();
                        }


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {

            Toast.makeText(MainActivity.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();

        }
    }


}
