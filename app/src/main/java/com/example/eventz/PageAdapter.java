package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private int number_of_tabs;

    public PageAdapter(FragmentManager fm, int number_of_tabs) {
        super(fm);
        this.number_of_tabs = number_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab1();
            case 1:
                return new Tab2();
            case 2:
                return new Tab3();
            case 3:
                return new Tab4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return number_of_tabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
