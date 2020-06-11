package com.sssproductions.profilecollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sssproductions.profilecollection.Adapters.MyPagerAdapterInfo;

public class MainActivity extends AppCompatActivity {

    private Button skipbtn, nxtbtn;
    private ViewPager viewPager;
    private LinearLayout linearLayoutdot;
    private TextView[]dotstv;
    private int[]layouts;
    private MyPagerAdapterInfo pagerAdapterIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //run next activity if not first time
        if(!isFirstTimeStartApp()){
            startMainActivity();
            finish();
        }

        setContentView(R.layout.activity_main);


        skipbtn = findViewById(R.id.btnskip_mainpage);
        nxtbtn = findViewById(R.id.btnnext_mainpage);
        viewPager = findViewById(R.id.viewpager_mainpage);
        linearLayoutdot = findViewById(R.id.dotlayout_mainpage);

        skipBtn_Clicked();

        nextBtn_Clicked();


        layouts = new int[]{R.layout.slider_1, R.layout.slider_2, R.layout.slider_3, R.layout.slider_4};
        pagerAdapterIntro = new MyPagerAdapterInfo( layouts,getApplicationContext());
        viewPager.setAdapter(pagerAdapterIntro);

        viewPager_Changed();

        setDotStatus(0);

    }

    private void skipBtn_Clicked() {
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startMainActivity();
            }
        });
    }

    private void nextBtn_Clicked() {
        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem()+1;
                if(currentPage<layouts.length){
                    viewPager.setCurrentItem(currentPage);
                }else{
                    startMainActivity();
                }
            }
        });

    }

    private void viewPager_Changed() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == layouts.length-1){
                    //LAST PAGE
                    nxtbtn.setText("Get Started");
                    skipbtn.setVisibility(View.GONE);
                }else{
                    nxtbtn.setText("NEXT");
                    skipbtn.setVisibility(View.VISIBLE);
                }
                setDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private boolean isFirstTimeStartApp(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag", true);
    }

    private void setFirstTimeStartStatus(boolean stt){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartFlag", stt);
        editor.apply();
    }

    private void setDotStatus(int page){
        linearLayoutdot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for (int i=0; i<dotstv.length; i++){
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#3a3a3a"));
            linearLayoutdot.addView(dotstv[i]);
        }

        //Set Current dot active
        if(dotstv.length>0){
            dotstv[page].setTextColor(Color.parseColor("#6200EE"));
        }
    }

    private void startMainActivity(){
        //TODO: comment / uncomment below line to have pager only first time
        setFirstTimeStartStatus(false);
        startActivity(new Intent(MainActivity.this,login.class));
        finish();
    }
}
