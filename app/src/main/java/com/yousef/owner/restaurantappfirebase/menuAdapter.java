package com.yousef.owner.restaurantappfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.yousef.owner.restaurantappfirebase.Model.Category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class menuAdapter extends FirestoreRecyclerAdapter<Category,menuAdapter.menuHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public menuAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull menuHolder holder, int position, @NonNull Category model) {

        holder.textViewName.setText(model.getName());
        Picasso.get().load(model.getImage()).into(holder.imageViewMenu);
        final Category ClickItem = model;

    }

    @NonNull
    @Override
    public menuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new menuHolder(view);
    }

    class menuHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewMenu;

        public menuHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textMenu);
            imageViewMenu = itemView.findViewById(R.id.menuImage);
        }

    }
}