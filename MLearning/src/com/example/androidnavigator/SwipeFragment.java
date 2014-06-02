package com.example.androidnavigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public final class SwipeFragment extends Fragment {
    private int position = 0;

    public static SwipeFragment newInstance(int position) {
        SwipeFragment fragment = new SwipeFragment();
        fragment.position = position;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //http://stackoverflow.com/questions/7836250/saving-ui-on-orientation-change-onsaveinstancestate-not-working-as-expected-if/8488107#8488107
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        layout.setGravity(Gravity.CENTER);

        String content = "";

        if (position == 0)
            content = "mLearning\n<swipe";
        else if (position == 1)
            content = "Isfahan";
        else if (position == 2)
            content = "Shiraz";

        if (position <= 2) {

            TextView lblContent = new TextView(getActivity());
            lblContent.setText(content);
            lblContent.setTextSize(20 * getResources().getDisplayMetrics().density);
            lblContent.setGravity(Gravity.CENTER);
            lblContent.setPadding(20, 20, 20, 20);
            layout.addView(lblContent);

        } else {

            Button btnLogin = createButton("Sign In");
            Button btnCreateAccount = createButton("Create Account");

            GridLayout gridLayout = createGridLayout(2, 1);
            gridLayout.addView(btnCreateAccount);
            gridLayout.addView(btnLogin);

            //add gridlayout to linearlayout
            layout.addView(gridLayout);

            btnCreateAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent createAccountIntent = new Intent(getActivity(), CreateAccountActivity.class);
                    startActivity(createAccountIntent);
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInIntent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(signInIntent);
                }
            });
        }

        return layout;
    }

    private GridLayout createGridLayout(int column, int row) {
        GridLayout gridLayout = new GridLayout(getActivity());
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);
        return gridLayout;
    }

    private Button createButton(String label) {
        Button btn = new Button(getActivity());
        btn.setText(label);
        return btn;
    }


}
