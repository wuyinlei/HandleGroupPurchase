package yinlei.com.handlegrouppurchase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import yinlei.com.handlegrouppurchase.ui.GuideActivity;

public class SplashActivity extends AppCompatActivity {

  /*  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View target = findViewById(R.id.rl);

        ObjectAnimator animator = ObjectAnimator.ofFloat(target,"alpha",0.0f,1.0f);
        animator.setDuration(2000);//动画执行的时间

        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                boolean isFirst = sp.getBoolean("isFirst", true);
                Intent intent = new Intent();
                if (isFirst) {
                    sp.edit().putBoolean("isFirst", false).commit();
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);

                finish();
            }
        });

        //第一种启动方式
       /* mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                boolean isFirst = sp.getBoolean("isFirst", true);
                Intent intent = new Intent();
                if (isFirst) {
                    sp.edit().putBoolean("isFirst", false).commit();
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }

                startActivity(intent);

                finish();
            }
        }, 3000);*/


    }
}
