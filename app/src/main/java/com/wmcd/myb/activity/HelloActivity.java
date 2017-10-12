package com.wmcd.myb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wmcd.myb.R;
import com.wmcd.myb.adapter.HelloAdapter;
import com.wmcd.myb.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/6 0006.
 */

public class HelloActivity extends BaseActivity {
    @Bind(R.id.vp_hello)
    ViewPager vpHello;
    @Bind(R.id.skip_hello)
    ImageView skipHello;
    @Bind(R.id.hello)
    RelativeLayout rl_hello;
    @Bind(R.id.tv_time_hello)
    TextView tvTimeHello;
    private List<View> imgs;
    private int[] pics = {R.drawable.hello2, R.drawable.hello3, R.drawable.hello1};
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.hello_activity);
        ButterKnife.bind(this);
        initView();
        vpHello.setAdapter(new HelloAdapter(imgs));
        vpHello.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    timer.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goToMaintop();
                        }
                    }, 3000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void goToMaintop() {
        timer.cancel();
        Intent i = new Intent(HelloActivity.this,
                MainTopActivity.class);

       /*  AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(1000);//设置动画持续时间
        rl_hello.setAnimation(animation);*/
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
       /* animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
        //animation.start();

        sharedPreferences = getSharedPreferences("MakeupNet",
                Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("hasenter", true);
        editor.commit();
    }

    private void initView() {
        ImageView iv;
        imgs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            iv = new ImageView(HelloActivity.this);
            iv.setImageResource(pics[i]);
            imgs.add(iv);
        }
    }

    @OnClick(R.id.skip_hello)
    public void onClick() {
        goToMaintop();
    }


    private CountDownTimer timer = new CountDownTimer(3100, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvTimeHello.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            tvTimeHello.setEnabled(true);
            tvTimeHello.setText("欢迎使用");
        }
    };
}
