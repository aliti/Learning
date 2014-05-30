package com.example.androidnavigator.validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

/**
 * Copyright (c) 2008-2013, Behsazan-e-Mellat, Co. All rights reserved.
 * <p> 5/12/14, 3:13 PM </p>
 * <p/>
 * <p> @author: <a href="mailto:ali.heydari@outlook.com">Ali Heydari Moghaddam</a></p>
 */
public abstract class TextValidator extends ValidatorUtils implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    final public void afterTextChanged(Editable editable) {
        String text = textView.getText().toString();
        validate(textView, text);
    }
}
