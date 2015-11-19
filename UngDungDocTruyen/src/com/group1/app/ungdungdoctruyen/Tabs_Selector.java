package com.group1.app.ungdungdoctruyen;

import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Tabs_Selector extends FragmentPagerAdapter{
	public Tabs_Selector(FragmentManager fm) {
        super(fm);
    }
	
    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Tab1 t1 = new Tab1();
                return t1;
            case 1:
                Tab2 t2 = new Tab2();
                return t2;
            case 2:
                Tab3 t3 = new Tab3();
                return t3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        Locale locale = Locale.getDefault();
        switch (position) {
            case 0:
                return "Thể loại";
            case 1:

                return "Trang chủ";
            case 2:

                return "Danh sách";
        }
        return null;
    }
}
