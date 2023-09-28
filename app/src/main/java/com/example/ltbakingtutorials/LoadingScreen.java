package com.example.ltbakingtutorials;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LoadingScreen extends AppCompatActivity {
    MediaPlayer soundEffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loading_screen);

        soundEffect= MediaPlayer.create(LoadingScreen.this,R.raw.oventimersoundeffect);
        soundEffect.start();
        Thread timer= new Thread(){
            public void run(){
                try{
                    sleep(1600);
                }catch(InterruptedException e){
                    e.printStackTrace();

                }finally{
                    nextActivity();
                }}} ;

        timer.start();
    }
    public void nextActivity(){
        Intent intent = new Intent(this,LoginForm.class);
        startActivity(intent);
        finish();
    }
}