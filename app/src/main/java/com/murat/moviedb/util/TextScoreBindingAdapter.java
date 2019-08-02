package com.murat.moviedb.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TextScoreBindingAdapter {

    // movie list source triggers 3 times because of 3 different observers
    // and movie detail's initial score value 0.0, so this is a workaround
    @BindingAdapter("app:adjustTextScore")
    public static void adjustTextScore(TextView textView, String score) {
        if (TextUtils.isEmpty(textView.getText()) || textView.getText().toString().equals("0.0")) {
            Log.d("adjustTextScore", "score: " + score);
            if (textView.getText().toString().equals("0.0")) {
                textView.setText("");
            }
            String[] each = score.split("\\.");
            String bigOne = each[0];
            SpannableString spannableString = new SpannableString(bigOne);
            spannableString.setSpan(new RelativeSizeSpan(1.6f), 0, bigOne.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.append(spannableString);
            textView.append(".");
            textView.append(each[1]);
        }
    }
}
