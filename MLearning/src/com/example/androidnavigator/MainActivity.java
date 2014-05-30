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
    SwipeFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;

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

    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("saved_thing", "some_value");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState() : " + savedInstanceState.getString("saved_thing"));
    }

}