package com.yousef.owner.restaurantappfirebase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class testActivity extends AppCompatActivity {

    TextView testTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//
////        List<Order> cart = new Database(this).getCarts();
//
//        testTest = findViewById(R.id.testText);
//        StringBuilder builder = new StringBuilder();
//        for (Order order : cart) {
//            builder.append(order.getProdectname() + "\n");
//        }
//
//        testTest.setText(builder.toString());


    }
}
