package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yousef.owner.restaurantappfirebase.Model.Requests;
import com.yousef.owner.restaurantappfirebase.ViewHolder.OrderViewHolder;
import com.yousef.owner.restaurantappfirebase.common.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStatus extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

//Init Firebase
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("Requests");


            recyclerView = findViewById(R.id.listOrder);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            if (Common.isNetworkAvailable(getBaseContext())) {
            loadOrders(Common.currentUser.getPhone());
        } else {
            Toast.makeText(OrderStatus.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderStatus.this,MainActivity.class);
                startActivity(intent);
        }


    }

    private void loadOrders(String phone) {

        FirebaseRecyclerOptions<Requests> options =
                new FirebaseRecyclerOptions.Builder<Requests>()
                        .setQuery(reference.orderByChild("phone").equalTo(phone), Requests.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull Requests requests) {
                orderViewHolder.txtOrderid.setText(adapter.getRef(i).getKey());
                orderViewHolder.txtOrderstatus.setText(convertCodeToStatus(requests.getStatus()));
                orderViewHolder.txtOrderAddress.setText(requests.getAddress());
                orderViewHolder.txtOrderphone.setText(requests.getPhone());
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
                return new OrderViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {

        if (status.equals("0")) {
            return "يتم اعداد الطلب";
        } else if (status.equals("1")) {
            return "الطلب في طريقه اليك";
        } else {
            return "لقد تم توصيل الطلب";
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
