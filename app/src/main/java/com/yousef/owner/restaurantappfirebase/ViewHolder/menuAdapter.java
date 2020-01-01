package com.yousef.owner.restaurantappfirebase.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener;
import com.yousef.owner.restaurantappfirebase.Model.Category;
import com.yousef.owner.restaurantappfirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class menuAdapter extends FirestoreRecyclerAdapter<Category, menuAdapter.menuHolder> {

    private ItemClickListener itemClickListener;

    public menuAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final menuHolder holder, int position, @NonNull Category model) {

        holder.textViewName.setText(model.getName());
        Picasso.get().load(model.getImage()).into(holder.imageViewMenu);
//        final Category ClickItem = model;
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void OnClick(DocumentSnapshot view, int position, boolean isLongClick) {
//              //  Intent intent = new Intent(getApplicationContext, FoodList.class);
//                Home home = new Home();
//
//                home.intent();
//            }
//        });

    }

    @NonNull
    @Override
    public menuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new menuHolder(view);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class menuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        ImageView imageViewMenu;


        public menuHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textMenu);
            imageViewMenu = itemView.findViewById(R.id.menuImage);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            // itemClickListener.OnClick(v, getAdapterPosition(), false);
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                itemClickListener.OnClick(getSnapshots().getSnapshot(position), position, false);
            }
        }

    }
}
