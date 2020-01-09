package com.yousef.owner.restaurantappfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yousef.owner.restaurantappfirebase.Database.Database;
import com.yousef.owner.restaurantappfirebase.Model.Order;
import com.yousef.owner.restaurantappfirebase.Model.Requests;
import com.yousef.owner.restaurantappfirebase.ViewHolder.CartAdapter;
import com.yousef.owner.restaurantappfirebase.common.Common;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    FirebaseFirestore database;
    CollectionReference request;

String thisID=String.valueOf(System.currentTimeMillis());
    Button placeOrder;

    List<Order> cart;
    CartAdapter adapter;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            deleteItem(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (Common.isNetworkAvailable(getBaseContext())) {
            database = FirebaseFirestore.getInstance();
            request = database.collection("Requests");

            //init
            recyclerView = findViewById(R.id.listCart);
            recyclerView.setHasFixedSize(true);
            manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);

            //Text and Button
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
                    if (cart.size() > 0) {
                        showAlertDailog();
                    } else {
                        Toast.makeText(Cart.this, "السلة فارغه", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        } else {
            Toast.makeText(Cart.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Cart.this, MainActivity.class);
            startActivity(intent);
        }


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
                        placeOrder.getText().toString(),
                        cart
                );

                //Submit to firebase
                request.document(String.valueOf(System.currentTimeMillis())).set(requests);

                //Delete Cart List
                new Database(Cart.this).cleanCart();

                Toast.makeText(Cart.this, "يتم الان تحضير طلبك", Toast.LENGTH_SHORT).show();
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
        adapter.notifyDataSetChanged();
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        int totalInt = 0;
        for (Order order : cart) {
            totalInt += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }
        //Calculate total Price
        Locale locale = new Locale("ar", "SA");

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        //total.setText();
        placeOrder.setText(fmt.format(totalInt));

    }


    private void deleteItem(int order) {
        cart.remove(order);

        new Database(this).cleanCart();

        for (Order item : cart) {
            new Database(this).addToCart(item);
        }
        loadListView();
    }


}
