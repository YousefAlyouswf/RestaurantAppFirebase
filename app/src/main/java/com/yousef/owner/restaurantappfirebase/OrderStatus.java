package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yousef.owner.restaurantappfirebase.Model.Requests;
import com.yousef.owner.restaurantappfirebase.ViewHolder.OrderAdapter;
import com.yousef.owner.restaurantappfirebase.common.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderStatus extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private CollectionReference orderRef;
    private OrderAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

//Init Firebase

        orderRef = db.collection("Requests");


        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Common.isNetworkAvailable(getBaseContext())) {
            loadOrders(Common.currentUser.getPhone());
        } else {
            Toast.makeText(OrderStatus.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderStatus.this, MainActivity.class);
            startActivity(intent);
        }


    }


    private void loadOrders(String phone) {

        FirestoreRecyclerOptions<Requests> options =
                new FirestoreRecyclerOptions.Builder<Requests>()
                        .setQuery(orderRef.whereEqualTo("phone", phone), Requests.class)
                        .build();

        adapter = new OrderAdapter(options);

//        adapter = new FirestoreRecyclerAdapter<Requests, OrderAdapter.orderHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull OrderAdapter.orderHolder orderHolder, int i, @NonNull Requests requests) {
//                orderHolder.txtOrderid.setText(adapter.getSnapshots().getSnapshot(i).getId());
//                orderHolder.txtOrderstatus.setText(convertCodeToStatus(requests.getStatus()));
//                orderHolder.txtOrderAddress.setText(requests.getAddress());
//                orderHolder.txtOrderphone.setText(requests.getPhone());
//            }
//
//
//            @NonNull
//            @Override
//            public OrderAdapter.orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
//                return new OrderAdapter(view);
//            }
//        };
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
