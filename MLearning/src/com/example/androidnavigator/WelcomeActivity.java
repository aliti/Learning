package com.example.androidnavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.example.androidnavigator.utils.SessionManager;

import java.util.HashMap;

/**
 * Copyright (c) 2008-2013, Behsazan-e-Mellat, Co. All rights reserved.
 * <p> 5/17/14, 12:56 PM </p>
 * <p/>
 * <p> @author: <a href="mailto:ali.heydari@outlook.com">Ali Heydari Moghaddam</a></p>
 */
public class WelcomeActivity extends Activity {

    // Session Manager Class
    SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.welcome);

        // Session class instance
        session = new SessionManager(getApplicationContext());


        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        TextView txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtWelcome.setText(" Welcome  " + email);

        Button btnSignOut = (Button) findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });

    }
}