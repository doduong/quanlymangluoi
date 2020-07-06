package com.example.mac.appproject_moneymanager.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyAdapter1 extends FragmentStatePagerAdapter {

    //Thu tien
    private String listTab[] = {"CHƯA KS", "ĐÃ KS", "KHÔNG ĐẠT"};
    //End Thu tien
    private Tab1 tab1 ;
    private Tab2 tab2 ;
    private Tab3_KhongDat tab3 ;


    public MyAdapter1(FragmentManager fm) {
        super(fm);
        tab1 = new Tab1();
        tab2 = new Tab2();
        tab3 = new Tab3_KhongDat();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return tab1;

        }else if (position ==1) {
            return tab2;

        }else if (position ==2) {
            return tab3;

        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
        //return null;
    }
}
