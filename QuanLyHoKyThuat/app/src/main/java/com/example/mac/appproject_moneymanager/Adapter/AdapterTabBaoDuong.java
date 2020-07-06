package com.example.mac.appproject_moneymanager.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AdapterTabBaoDuong extends FragmentStatePagerAdapter {

    //Thu tien
    private String listTab[] = {"CHƯA BD", "ĐÃ BD", "KHÔNG ĐẠT"};
    //End Thu tien
    private Tab1_BaoDuong tab1 ;
    private Tab2_BaoDuong tab2 ;
    private Tab3_KhongDat_BaoDuong tab3 ;


    public AdapterTabBaoDuong(FragmentManager fm) {
        super(fm);
        tab1 = new Tab1_BaoDuong();
        tab2 = new Tab2_BaoDuong();
        tab3 = new Tab3_KhongDat_BaoDuong();
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
    }
}


