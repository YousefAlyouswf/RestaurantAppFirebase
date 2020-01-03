package com.yousef.owner.restaurantappfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.yousef.owner.restaurantappfirebase.Interface.ItemClickListener;
import com.yousef.owner.restaurantappfirebase.Model.Category;
import com.yousef.owner.restaurantappfirebase.ViewHolder.menuAdapter;
import com.yousef.owner.restaurantappfirebase.common.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity {
    FirebaseFirestore db;
    TextView UserName;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirestoreRecyclerOptions<Category> recyclerOptions;
    private CollectionReference menuRef;
    private menuAdapter adapter;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();
        menuRef = db.collection("category");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Cart.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_menu, R.id.nav_cart, R.id.nav_order,
                R.id.nav_logOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        if (Common.isNetworkAvailable(getBaseContext())) {
            //set name for user
            View headerView = navigationView.getHeaderView(0);
            UserName = headerView.findViewById(R.id.username);
            UserName.setText(Common.currentUser.getName());
            loadMenu();
        } else {
            Toast.makeText(Home.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
        }


    }


    private void loadMenu() {
        Query query = menuRef.orderBy("name");


        recyclerOptions = new FirestoreRecyclerOptions.Builder<Category>().setQuery(query, Category.class).build();
        adapter = new menuAdapter(recyclerOptions);

        //RecyclerView LayoutManger
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(DocumentSnapshot documentSnapshot, int position, boolean isLongClick) {
                String id = documentSnapshot.getId();

                String path = documentSnapshot.getReference().getPath();
                Intent intent = new Intent(Home.this, FoodList.class);

                intent.putExtra("CategoryId", id);
                intent.putExtra("CategoryPath", path);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {

        if (Common.isNetworkAvailable(getBaseContext())) {
            super.onStart();
            adapter.startListening();
        } else {
            Toast.makeText(Home.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Home.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {

        if (Common.isNetworkAvailable(getBaseContext())) {
            super.onStop();
            adapter.startListening();
        } else {
            Toast.makeText(Home.this, "لا يوجد إتصال بالانترنت", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}

