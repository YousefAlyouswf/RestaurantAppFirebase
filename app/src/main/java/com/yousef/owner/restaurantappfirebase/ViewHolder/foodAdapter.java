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
import com.yousef.owner.restaurantappfirebase.Model.Food;
import com.yousef.owner.restaurantappfirebase.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class foodAdapter extends FirestoreRecyclerAdapter<Food,foodAdapter.foodHolder> {

    private ItemClickListener itemClickListener;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public foodAdapter(@NonNull FirestoreRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull foodHolder foodholder, int i, @NonNull Food food) {
        foodholder.foodName.setText(food.getName());
        Picasso.get().load(food.getImage()).into(foodholder.foodImage);

    }

    @NonNull
    @Override
    public foodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new foodAdapter.foodHolder(view);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    class foodHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView foodName;
        ImageView foodImage;



        public foodHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.foodname);
            foodImage = itemView.findViewById(R.id.foodImage);
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
