package com.murat.moviedb.util;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.murat.moviedb.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

public class ImageBindingAdapter {

    @BindingAdapter("app:url")
    public static void loadImageUrl(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(ApiConstants.IMAGE_URL_PREFIX_W342 + url)
                    .fit()
                    .into(imageView);
        }
    }
}
