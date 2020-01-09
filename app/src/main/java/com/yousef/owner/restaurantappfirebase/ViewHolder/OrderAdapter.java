package com.yousef.owner.restaurantappfirebase.ViewHolder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener;
import com.yousef.owner.restaurantappfirebase.Model.Requests;
import com.yousef.owner.restaurantappfirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends FirestoreRecyclerAdapter<Requests, OrderAdapter.orderHolder> {
    private ItemClickListener itemClickListener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderAdapter(@NonNull FirestoreRecyclerOptions<Requests> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull orderHolder orderHolder, int i, @NonNull Requests order) {

        orderHolder.txtOrderid.setText(getSnapshots().getSnapshot(i).getId());
        orderHolder.txtOrderstatus.setText(convertCodeToStatus(order.getStatus()));
        orderHolder.txtOrderAddress.setText(order.getAddress());
        orderHolder.txtOrderphone.setText(order.getPhone());
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

    @NonNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new OrderAdapter.orderHolder(view);
    }


    public class orderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtOrderid, txtOrderstatus, txtOrderphone, txtOrderAddress;

        public orderHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderid = itemView.findViewById(R.id.order_id);
            txtOrderstatus = itemView.findViewById(R.id.order_status);
            txtOrderphone = itemView.findViewById(R.id.order_phone);
            txtOrderAddress = itemView.findViewById(R.id.order_address);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


}

