package com.yousef.owner.restaurantappfirebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener;
import com.yousef.owner.restaurantappfirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textMenuView;
    public ImageView imageMenuView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        textMenuView = itemView.findViewById(R.id.textMenu);
        imageMenuView = itemView.findViewById(R.id.menuImage);
        itemView.setOnClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v, getAdapterPosition(), false);
    }
}
