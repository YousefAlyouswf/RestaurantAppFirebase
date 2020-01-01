package com.yousef.owner.restaurantappfirebase;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.yousef.owner.restaurantappfirebase.Model.Food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FoodDetails extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ElegantNumberButton numberButton;
    FloatingActionButton btnCart;
    TextView foodDes;
    TextView foodName;
    TextView foodPrice;
    ImageView foodImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    String foodID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        //init textAndButtons
        numberButton = findViewById(R.id.numberBtn);
        foodDes = findViewById(R.id.food_descritipn);
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.food_price);
        foodImage = findViewById(R.id.imgfoodDetails);
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        btnCart = findViewById(R.id.btnCart2);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsAppBar);
       // toolbar = findViewById(R.id.toolBarDetails);
        loadFood();
        try {


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadFood() {


        if (getIntent() != null) {
            foodID = getIntent().getStringExtra("foodID");
            if (!foodID.isEmpty() && foodID != null) {
                LoadDes(foodID);
            }
        }
    }

    private void LoadDes(String foodID) {

        DocumentReference docRef = db.collection("Foods").document(foodID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Food food = documentSnapshot.toObject(Food.class);

                Picasso.get().load(food.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(food.getName());

                foodPrice.setText(food.getPrice());
                foodName.setText(food.getName());
                foodDes.setText(food.getDescription());
            }
        });

    }

}
