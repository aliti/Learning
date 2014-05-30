package com.example.androidnavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.androidnavigator.db.DatabaseHandler;
import com.example.androidnavigator.model.UserModel;
import com.example.androidnavigator.validator.TextValidator;

import java.util.List;

/**
 * Copyright (c) 2008-2013, Behsazan-e-Mellat, Co. All rights reserved.
 * <p> 5/8/14, 1:53 PM </p>
 * <p/>
 * <p> @author: <a href="mailto:ali.heydari@outlook.com">Ali Heydari Moghaddam</a></p>
 */
public class CreateAccountActivity extends Activity {
    private static String TAG = "*** CreateAccountActivity ***";
    boolean mailValid = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.title_create_account);
        //getActionBar().hide();

        //The look of this sample is set via a style in the manifest
        setContentView(R.layout.create_account);

        Button btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        final DatabaseHandler db = new DatabaseHandler(this);

        final EditText txtName = (EditText) findViewById(R.id.txtName);
        txtName.addTextChangedListener(new TextValidator(txtName) {
            @Override
            public void validate(TextView textView, String text) {
                if (textView.getText().length() < 1)
                    showValidateMessage(textView, Html.fromHtml(getString(R.string.msg_name_not_null)).toString());
            }
        });

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

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = false;

                String name = txtName.getText().toString();
                String email = txtEmail.getText().toString();
                String pass = txtPass.getText().toString();

                if (name.equals("") || email.equals("") || pass.equals("") || !mailValid) {
                    Toast.makeText(getApplicationContext(), R.string.msg_not_null, Toast.LENGTH_LONG).show();
                    return;
                } else
                    result = db.addUser(new UserModel(name, email, pass));

                if (result){
                    Toast.makeText(getApplicationContext(), R.string.msg_user_create, Toast.LENGTH_LONG).show();

                    Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(signInIntent);
                    finish();

                }else
                    Toast.makeText(getApplicationContext(), R.string.msg_user_not_create, Toast.LENGTH_LONG).show();


                //todo: (aliti-930229) this block is for check only; you can remove it
                List<UserModel> users = db.getAllUsers();
                for (UserModel user : users) {
                    String log = "Id: " + user.getId() + " ,Name: " + user.getName() + " ,Email: " + user.getEmail();
                    Log.d(TAG, log);
                }

            }


        }

        );

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
