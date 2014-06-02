package com.example.androidnavigator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import com.example.androidnavigator.utils.LinePageIndicator;
import com.example.androidnavigator.utils.SessionManager;


public class MainActivity extends FragmentActivity {
    private static String TAG = "MainActivity-----";
    private SwipeFragmentAdapter mAdapter;
    private ViewPager mPager;
    private PageIndicator mIndicator;
    private static int pageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        //The look of this sample is set via a style in the manifest
        setContentView(R.layout.main);

        mAdapter = new SwipeFragmentAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (LinePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);

        mIndicator.setCurrentItem(pageIndex); //mAdapter.getCount() - 1

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        pageIndex = 3;
    }
}