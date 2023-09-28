package com.example.ltbakingtutorials;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button buttonViewRecipes, buttonDifficulty, buttonRequestRecipes, buttonAboutUs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        buttonViewRecipes = (Button) findViewById(R.id.button1);
        buttonViewRecipes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                openViewRecipes();
            }
        });

        buttonDifficulty = (Button) findViewById(R.id.button2);
        buttonDifficulty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                openDifficultyRanking();
            }
        });

        buttonRequestRecipes = (Button) findViewById(R.id.button3);
        buttonRequestRecipes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                openRequestRecipes();
            }
        });

        buttonAboutUs = (Button) findViewById(R.id.button4);
        buttonAboutUs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                openAboutUs();
            }
        });
    }
    public void openViewRecipes(){
        Intent intent = new Intent(this, ViewRecipes.class);
        startActivity(intent);
        finish();
    }
    public void openDifficultyRanking(){
        Intent intent = new Intent(this, VideoTutorials.class);
        startActivity(intent);
        finish();
    }
    public void openRequestRecipes(){
        Intent intent = new Intent(this, RequestRecipes.class);
        startActivity(intent);
        finish();
    }
    public void openAboutUs(){
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
        finish();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}