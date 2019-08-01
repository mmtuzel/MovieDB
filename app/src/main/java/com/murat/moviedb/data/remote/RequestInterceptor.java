package com.murat.moviedb.data.remote;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("api_key", ApiConstants.API_KEY)
                .build();

        request = request.newBuilder().url(httpUrl).build();

        return chain.proceed(request);
    }
}
