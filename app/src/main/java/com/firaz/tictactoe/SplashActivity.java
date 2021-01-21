package com.firaz.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT=5000;

    Animation topAnimation,bottomAnimation,middleAnimation;
    View first,second,third,fourth,fifth,sixth;
    TextView title,slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        middleAnimation=AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        first=findViewById(R.id.first_line);
        second=findViewById(R.id.second_line);
        third=findViewById(R.id.third_line);
        fourth=findViewById(R.id.fourth_line);
        fifth=findViewById(R.id.fifth_line);
        sixth=findViewById(R.id.sixth_line);

        title=findViewById(R.id.title);
        slogan=findViewById(R.id.tagLine);


        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        title.setAnimation(middleAnimation);
        slogan.setAnimation(bottomAnimation);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this,MainMenu.class));
                    finish();
                }
            }
        };

        thread.start();
    }
}