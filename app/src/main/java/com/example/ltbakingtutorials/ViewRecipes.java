package com.example.ltbakingtutorials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewRecipes extends AppCompatActivity {
    private ImageButton Backbtn;
    private ArrayList<ImageModel> imageModelArrayList;

    ArrayList<Recipes> mylist;
    RecipesAdapter recipesAdapter;
    RecyclerView newrecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_recipes);

        newrecyclerView = findViewById(R.id.recyclerView);
        newrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Recipes> options =
                new FirebaseRecyclerOptions.Builder<Recipes>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Recipes"), Recipes.class)
                        .build();
        recipesAdapter = new RecipesAdapter(options);
        newrecyclerView.setAdapter(recipesAdapter);



        Backbtn = (ImageButton) findViewById(R.id.backBtn);
        Backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                backtoMainMenu();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        recipesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recipesAdapter.stopListening();
    }

    /**private void clearAll() {
     if(imageModelArrayList != null){

     imageModelArrayList.clear();
     if (recyclerImageAdapter != null){
     recyclerImageAdapter.notifyDataSetChanged();
     }
     }
     imageModelArrayList = new ArrayList<>();
     }*/

    public void backtoMainMenu(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}