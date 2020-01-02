package com.yousef.owner.restaurantappfirebase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yousef.owner.restaurantappfirebase.Database.Database;
import com.yousef.owner.restaurantappfirebase.Model.Order;
import com.yousef.owner.restaurantappfirebase.Model.Requests;
import com.yousef.owner.restaurantappfirebase.ViewHolder.CartAdapter;
import com.yousef.owner.restaurantappfirebase.common.Common;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView total;
    Button placeOrder;

    List<Order> cart;
    CartAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Requests");

        //init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        //Text and Button
        total = findViewById(R.id.total);
        placeOrder = findViewById(R.id.btnPlaceOrder);

        loadListView();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
                animation1.setDuration(10);
                animation1.setStartOffset(100);
                animation1.setFillAfter(true);
                v.startAnimation(animation1);

                showAlertDailog();

            }
        });


    }

    private void showAlertDailog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Cart.this);
        alert.setTitle("One More Step!");
        alert.setMessage("Enter Your Address");

        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editAddress.setLayoutParams(lp);
        alert.setView(editAddress);
        alert.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Requests requests = new Requests(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAddress.getText().toString(),
                        total.getText().toString(),
                        cart
                );

                //Submit to firebase
                request.child(String.valueOf(System.currentTimeMillis())).setValue(requests);

                //Delete Cart List
                new Database(Cart.this).cleanCart();

                Toast.makeText(Cart.this, "Thank You", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void loadListView() {

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, Cart.this);
        recyclerView.setAdapter(adapter);

        int totalInt = 0;
        for (Order order : cart) {
            totalInt += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }
        //Calculate total Price
        Locale locale = new Locale("ar", "SA");

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        total.setText(fmt.format(totalInt));


    }
}
