package com.example.onlineclothingstore.item_and_category_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.onlineclothingstore.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayCategory.this,AddCategory.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CategoryModule>  options=
                new FirebaseRecyclerOptions.Builder<CategoryModule>()
                  .setQuery(FirebaseDatabase.getInstance().getReference().child("Categories"),CategoryModule.class)
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


    //search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);

        MenuItem item= menu.findItem(R.id.search);
        SearchView searchView =(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void txtSearch(String str){


        FirebaseRecyclerOptions<CategoryModule>  options=
                new FirebaseRecyclerOptions.Builder<CategoryModule>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Categories").orderByChild("category").startAt(str).endAt(str+"~"),CategoryModule.class)
                        .build();

        categoryAdapter = new CategoryAdapter(options);
        categoryAdapter.startListening();
        recyclerView.setAdapter(categoryAdapter);
    }
}