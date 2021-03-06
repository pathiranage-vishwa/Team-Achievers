package com.example.onlineclothingstore.item_and_category_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import com.example.onlineclothingstore.MainActivity;
import com.example.onlineclothingstore.R;
import com.example.onlineclothingstore.cart_and_order_management.AdminNewOrdersActivity;
import com.example.onlineclothingstore.user_and_payment_management.UserProfile;
import com.example.onlineclothingstore.user_and_payment_management.cardform;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class DisplayCategory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    Button btnAdd;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    FloatingActionButton floatingActionButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_display_category);

        //Main Menu
        toolbar = findViewById(R.id.toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

//        setSupportActionBar(toolbar);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        // Add Category

        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floatingbtn1);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayCategory.this, AddPhone.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CategoryModule> options =
                new FirebaseRecyclerOptions.Builder<CategoryModule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Categories"), CategoryModule.class)
                        .build();

        categoryAdapter = new CategoryAdapter(options);
        recyclerView.setAdapter(categoryAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        categoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
    }


    //to  stop app get close when pressing back key
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent int1 = new Intent(DisplayCategory.this, DisplayCategory.class);
            startActivity(int1);
        }
    }

    //Menu Item select
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_req:
                break;
            case R.id.nav_order:
                Intent int6 = new Intent(DisplayCategory.this, AdminNewOrdersActivity.class);
                startActivity(int6);
                break;
            case R.id.nav_category:
                //////////////
                String uid = FirebaseAuth.getInstance().getUid();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("Users").child(uid).child("isUser").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        int usertype = snapshot.getValue(Integer.class);
                        if(usertype == 1){
                            Intent int1 =new Intent(DisplayCategory.this, DisplayItemsUser.class);
                            startActivity(int1);
                        }
                        else{
                            Intent int2 =new Intent(DisplayCategory.this, DisplayItemAdmin.class);
                            startActivity(int2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
                //////////////

//                Intent int1 = new Intent(DisplayCategory.this, DisplayItemsUser.class);
//                startActivity(int1);
                break;
            case R.id.nav_login:
                Intent int3 = new Intent(DisplayCategory.this, AddCategory.class);
                startActivity(int3);
                break;
            case R.id.nav_profile:
                Intent int2 = new Intent(DisplayCategory.this, UserProfile.class);
                startActivity(int2);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DisplayCategory.this, MainActivity.class));
                break;
            case R.id.nav_contact:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


//
//    //search
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.search,menu);
//
//        MenuItem item= menu.findItem(R.id.search);
//        SearchView searchView =(SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                txtSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                txtSearch(query);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//
//    private void txtSearch(String str){
//
//
//        FirebaseRecyclerOptions<CategoryModule>  options=
//                new FirebaseRecyclerOptions.Builder<CategoryModule>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Categories").orderByChild("category").startAt(str).endAt(str+"~"),CategoryModule.class)
//                        .build();
//
//        categoryAdapter = new CategoryAdapter(options);
//        categoryAdapter.startListening();
//        recyclerView.setAdapter(categoryAdapter);
//    }
}