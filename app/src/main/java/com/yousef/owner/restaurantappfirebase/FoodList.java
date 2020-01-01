package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener;
import com.yousef.owner.restaurantappfirebase.Model.Food;
import com.yousef.owner.restaurantappfirebase.ViewHolder.foodAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodList extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private CollectionReference menuRef;
    private foodAdapter adapter;
    String categoryID = "";
    String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        categoryID = getIntent().getStringExtra("CategoryId");
        path = getIntent().getStringExtra("CategoryPath");
        menuRef=db.collection("Foods");
        loadFood();
    }

    private void loadFood() {


        if(getIntent() != null){
            categoryID = getIntent().getStringExtra("CategoryId");
            if(!categoryID.isEmpty()&& categoryID != null){
                loadListFood(categoryID);
            }
        }



    }

    private void loadListFood(String categoryid) {
        Query query = menuRef.whereEqualTo("MenuId",categoryid);

        FirestoreRecyclerOptions<Food> recyclerOptions =
                new FirestoreRecyclerOptions.Builder<Food>().setQuery(query,
                Food.class).build();
        adapter = new foodAdapter(recyclerOptions);

        //RecyclerView LayoutManger;
        recyclerView = findViewById(R.id.recyclerFood);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(DocumentSnapshot documentSnapshot, int position, boolean isLongClick) {
                String id = documentSnapshot.getId();

                //String path = documentSnapshot.getReference().getPath();
                Intent intent = new Intent(FoodList.this, FoodDetails.class);

                intent.putExtra("foodID", id);
                //intent.putExtra("CategoryPath", path);
                startActivity(intent);
            }
        });
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
