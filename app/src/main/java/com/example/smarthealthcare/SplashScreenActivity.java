package com.example.smarthealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView mSplashText;
    private ImageView mSplashLogo;
    private ImageView mSplashcenter;
    private ImageView mSplashcenter2;
    private Animation uptodown;
    private Animation downtoup;
    private Animation center;
    private Animation center2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mSplashText = (ImageView) findViewById(R.id.splash_logo1);
        mSplashLogo = (ImageView) findViewById(R.id.splash_logo);
        mSplashcenter=findViewById(R.id.splash_logo3);
        mSplashcenter2=findViewById(R.id.splash_logo4);
        center=AnimationUtils.loadAnimation(this,R.anim.center);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        center2=AnimationUtils.loadAnimation(this,R.anim.center2);
        mSplashText.setAnimation(downtoup);
        mSplashLogo.setAnimation(uptodown);
        mSplashcenter.setAnimation(center);
        mSplashcenter2.setAnimation(center2);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally
                {
                    Intent main_Intent= new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(main_Intent);
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }

}