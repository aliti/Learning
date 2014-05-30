package com.example.androidnavigator.validator;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Copyright (c) 2008-2013, Behsazan-e-Mellat, Co. All rights reserved.
 * <p> 5/18/14, 2:46 PM </p>
 * <p/>
 * <p> @author: <a href="mailto:ali.heydari@outlook.com">Ali Heydari Moghaddam</a></p>
 */
public class ValidatorUtils {

    public boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void showValidateMessage(TextView textView, String message) {
        int ecolor = Color.BLACK; // whatever color you want

        ForegroundColorSpan fgcspan = new ForegroundColorSpan(ecolor);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);

        textView.requestFocus();
        textView.setError(ssbuilder);
    }


}
