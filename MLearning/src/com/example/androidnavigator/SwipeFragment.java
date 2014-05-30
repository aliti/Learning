package com.example.androidnavigator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

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
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("saved_thing", "some_value");
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
                    getActivity().finish();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signInIntent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(signInIntent);
                    getActivity().finish();
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
