package com.yousef.owner.restaurantappfirebase.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener2;
import com.yousef.owner.restaurantappfirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderid, txtOrderstatus, txtOrderphone, txtOrderAddress;

    private ItemClickListener2 itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderid = itemView.findViewById(R.id.order_id);
        txtOrderstatus = itemView.findViewById(R.id.order_status);
        txtOrderphone = itemView.findViewById(R.id.order_phone);
        txtOrderAddress = itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener2 itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v, getAdapterPosition(), false);
    }
}
