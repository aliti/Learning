package com.example.androidnavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.androidnavigator.utils.SessionManager;

public class MLearningSplashActivity extends Activity {
    // Session Manager Class
    SessionManager session;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        startAnimating();

        // Session class instance
        session = new SessionManager(getApplicationContext());

    }

    /**
     * Helper method to start the animation on the splash screen
     */
    private void startAnimating() {
        // Fade in top title
        final TextView mlearningText = (TextView) findViewById(R.id.title_mlearning);
        ImageView bigSandWatch = (ImageView) findViewById(R.id.big_sand_watch);

        Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
        Animation customanim = AnimationUtils.loadAnimation(this, R.anim.custom_anim);

        // Transition to Main Menu when bottom title finishes animating
        fade2.setAnimationListener(new AnimationListener() {

            public void onAnimationEnd(Animation animation) {
//				MyDBHandler dbHandler = MyDBHandler.getInstance(getApplicationContext());

/*
                startActivity(new Intent(MLearningSplashActivity.this, MainActivity.class));

                overridePendingTransition(R.anim.push_left_in, R.anim.push_up_out);
                finish();
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
*/

                /**
                 * Call this function whenever you want to check user login
                 * This will redirect user to LoginActivity is he is not
                 * logged in
                 * */
                session.checkLogin();

            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

//        mlearningText.setAnimation(fade1);
        mlearningText.setAnimation(fade2);
        bigSandWatch.setAnimation(customanim);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start animating at the beginning so we get the full splash screen
        // experience
        startAnimating();
    }

}
