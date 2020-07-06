package com.example.mac.appproject_moneymanager.Actions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.Adapter.MyAdapter1;
import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.utils.BaseActivity;

public class TabActivity  extends BaseActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();

    }

    private void initView(){

        mViewPager = findViewById(R.id.vp_demo);
        mViewPager.setAdapter(new MyAdapter1(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_demo);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(getDrawable(R.mipmap.ic_tab1));
        tabLayout.getTabAt(1).setIcon(getDrawable(R.mipmap.ic_tab2));
        tabLayout.getTabAt(2).setIcon(getDrawable(R.mipmap.ic_tab3));


    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                myOwnBackPress();
            }
        }, 500);
    }

    private void myOwnBackPress() {
        if (!isFinishing()) {
            //super.onBackPressed();
            Intent intent = new Intent(TabActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }


}
