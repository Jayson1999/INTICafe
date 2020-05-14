package com.example.androidassignment2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
//Splash class to display Splash screen on App Startup
public class SplashActivity extends AppCompatActivity {

    private ImageView image;
    private TextView tv;
    public static int SPLASH_TIME_OUT=2800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Hide the title bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        image = (ImageView) findViewById(R.id.splashlogo);
        tv = (TextView) findViewById(R.id.splashTV);

        //initialize a zooming animation based on animation resources from anim
        Animation hyperspaceJump = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.zoom);
        //Conduct animation
        image.startAnimation(hyperspaceJump);
        tv.startAnimation(hyperspaceJump);

        //Provide short delay of time based on SPLASH_TIME_OUT value before Navigating to Main Page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }


}
