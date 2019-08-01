package com.murat.moviedb.data.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiService INSTANCE;

    private ApiClient() {}

    public static ApiService getApiService() {
        if (INSTANCE == null) {
            synchronized (ApiClient.class) {
                if (INSTANCE == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiConstants.BASE_URL)
                            .client(buildClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    INSTANCE = retrofit.create(ApiService.class);
                }
            }
        }
        return INSTANCE;
    }

    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(new RequestInterceptor());
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }
}
