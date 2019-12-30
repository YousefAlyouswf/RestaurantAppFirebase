package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSignin();
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(10);
                animation1.setStartOffset(100);
                animation1.setFillAfter(true);
                v.startAnimation(animation1);
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
                Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });
    }

    private void intentSignin() {

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
