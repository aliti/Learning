package com.example.androidnavigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class SwipeFragmentAdapter extends FragmentStatePagerAdapter {

    public SwipeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SwipeFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }


}