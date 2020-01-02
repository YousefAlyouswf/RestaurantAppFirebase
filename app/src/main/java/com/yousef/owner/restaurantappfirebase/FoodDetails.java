package com.yousef.owner.restaurantappfirebase;

import android.os.Bundle;
import android.view.View;
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
import com.yousef.owner.restaurantappfirebase.Database.Database;
import com.yousef.owner.restaurantappfirebase.Model.Food;
import com.yousef.owner.restaurantappfirebase.Model.Order;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetails extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ElegantNumberButton numberButton;
    FloatingActionButton btnCart;
    TextView foodDes;
    TextView foodName;
    TextView foodPrice;
    ImageView foodImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String foodID = "";
    Food currentFood;

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


        btnCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    new Database(getBaseContext()).addToCart(new Order(
                            foodID,
                            currentFood.getName(),
                            numberButton.getNumber(),
                            currentFood.getPrice(),
                            currentFood.getDiscount()
                    ));
                    Toast.makeText(FoodDetails.this, "تم إظافة الطلب", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

        });


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
                currentFood = documentSnapshot.toObject(Food.class);

                Picasso.get().load(currentFood.getImage()).into(foodImage);
                collapsingToolbarLayout.setTitle(currentFood.getName());

                foodPrice.setText(currentFood.getPrice());
                foodName.setText(currentFood.getName());
                foodDes.setText(currentFood.getDescription());
            }
        });

    }

}
