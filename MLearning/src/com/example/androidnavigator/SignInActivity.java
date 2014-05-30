package com.example.androidnavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.*;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidnavigator.db.DatabaseHandler;
import com.example.androidnavigator.model.UserModel;
import com.example.androidnavigator.utils.SessionManager;
import com.example.androidnavigator.validator.TextValidator;

/**
 * Copyright (c) 2008-2013, Behsazan-e-Mellat, Co. All rights reserved.
 * <p> 5/10/14, 2:51 PM </p>
 * <p/>
 * <p> @author: <a href="mailto:ali.heydari@outlook.com">Ali Heydari Moghaddam</a></p>
 */
public class SignInActivity extends Activity {
    private static String TAG = "*** SignInActivity ***";
    boolean mailValid = false;

    // Session Manager Class
    SessionManager session;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.title_sign_in);
        //getActionBar().hide();

        // Session Manager
        session = new SessionManager(getApplicationContext());

        final DatabaseHandler db = new DatabaseHandler(this);

        //The look of this sample is set via a style in the manifest
        setContentView(R.layout.sign_in);

        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtEmail.addTextChangedListener(new TextValidator(txtEmail) {
            @Override
            public void validate(TextView textView, String text) {
                if (textView.getText().length() < 1)
                    showValidateMessage(textView, Html.fromHtml(getString(R.string.msg_email_not_null)).toString());
                else if (!isValidEmail(textView.getText()))
                    showValidateMessage(textView, Html.fromHtml(getString(R.string.msg_email_not_valid)).toString());
                else
                    mailValid = true;

            }
        });

        final EditText txtPass = (EditText) findViewById(R.id.txtPAss);
        txtPass.addTextChangedListener(new TextValidator(txtPass) {
            @Override
            public void validate(TextView textView, String text) {
                if (textView.getText().length() < 1)
                    showValidateMessage(textView, Html.fromHtml(getString(R.string.msg_pass_not_null)).toString());
            }
        });

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = txtEmail.getText().toString();
                String pass = txtPass.getText().toString();

                if (userName.equals("") || pass.equals("") || !mailValid) {
                    Toast.makeText(getApplicationContext(), R.string.msg_not_null, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    UserModel loginUser = db.isValidUser(userName, pass);

                    if (loginUser!=null) {

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession(loginUser.getName(), loginUser.getEmail());

                        Intent welcomeIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(welcomeIntent);
                        finish();

                    }else
                        Toast.makeText(getApplicationContext(), R.string.msg_user_pass_invalid, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Toast.makeText(getApplicationContext(), "Back button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                this.finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return true;
    }

}

