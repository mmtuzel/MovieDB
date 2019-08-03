package com.murat.moviedb.util;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.murat.moviedb.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

public class ImageBindingAdapter {

    @BindingAdapter("app:smallImageUrl")
    public static void loadSmallImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(ApiConstants.IMAGE_URL_PREFIX_W185 + url)
                    .fit()
                    .into(imageView);
        }
    }

    @BindingAdapter("app:mediumImageUrl")
    public static void loadMediumImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(ApiConstants.IMAGE_URL_PREFIX_W342 + url)
                    .fit()
                    .into(imageView);
        }
    }

    @BindingAdapter("app:largeImageUrl")
    public static void loadLargeImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(ApiConstants.IMAGE_URL_PREFIX_W500 + url)
                    .fit()
                    .into(imageView);
        }
    }
}
