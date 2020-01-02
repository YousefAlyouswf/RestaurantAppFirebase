package com.yousef.owner.restaurantappfirebase.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener;
import com.yousef.owner.restaurantappfirebase.Model.Order;
import com.yousef.owner.restaurantappfirebase.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Order> orderList;
    private Context context;

    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable textDrawable = TextDrawable.builder().buildRound("" + orderList.get(position), Color.RED);
        holder.img_cart_count.setImageDrawable(textDrawable);

        Locale locale = new Locale("ar", "SA");

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(orderList.get(position).getPrice())) * (Integer.parseInt(orderList.get(position).getQuantity()));
        holder.cart_price.setText(fmt.format(price));


        holder.cart_name.setText(orderList.get(position).getProdectname());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

   class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView cart_name, cart_price;
        ImageView img_cart_count;

        private ItemClickListener itemClickListener;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_name = itemView.findViewById(R.id.cart_item_name);
            cart_price = itemView.findViewById(R.id.cart_item_Price);
            img_cart_count = itemView.findViewById(R.id.cart_item_count);
        }

        public void setCart_name(TextView cart_name) {
            this.cart_name = cart_name;
        }

        @Override
        public void onClick(View v) {

        }
    }

}


/*
 extends FirestoreRecyclerAdapter<Order, CartAdapter.cartHoder>
private ItemClickListener itemClickListener;

    private List<Order> orderList = new ArrayList<>();
    private Context context;

    public CartAdapter(@NonNull FirestoreRecyclerOptions<Order> options, List<Order> orderList, Context context) {
        super(options);
        this.orderList = orderList;
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull cartHoder cartHoder, int i, @NonNull Order order) {

        TextDrawable textDrawable = TextDrawable.builder().buildRound("" + orderList.get(i).getQuantity(), Color.RED);
        cartHoder.img_cart_count.setImageDrawable(textDrawable);

        Locale locale = new Locale("en", "US");

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(orderList.get(i).getPrice())) * (Integer.parseInt(orderList.get(i).getQuantity()));
        cartHoder.cart_price.setText(fmt.format(price));


        cartHoder.cart_name.setText(orderList.get(i).getProdectName());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @NonNull
    @Override
    public cartHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartAdapter.cartHoder(view);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class cartHoder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cart_name, cart_price;
        ImageView img_cart_count;


        public cartHoder(@NonNull View itemView) {
            super(itemView);

            cart_name = itemView.findViewById(R.id.cart_item_name);
            cart_price = itemView.findViewById(R.id.cart_item_Price);
            img_cart_count = itemView.findViewById(R.id.cart_item_count);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                itemClickListener.OnClick(getSnapshots().getSnapshot(position), position, false);
            }
        }
    }
 */