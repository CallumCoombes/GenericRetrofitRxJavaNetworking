package uk.cal.codename.genericretrofitrxjavanetworking.Client;

import android.content.res.Configuration;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Copyright 2017, Callum Coombes, All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by  Callum Coombes <coombes.callum@gmail.com>, January 2017
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    //ID to be check for for the interceptor (to add a header)
    private static String AUTH_ID_URL = "http://123.456.789.10:5000";
    private static String TAG = "RetrofitClient";

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();
                            //If the request requires an auth Id for Tether Server requests, add it in
                            if (originalRequest.url().toString().contains(AUTH_ID_URL)
                                    && !originalRequest.url().toString().contains("register_something")) { //will intercept any url not containing "register_something", but that does contain AUTH_ID_URL

                                Request request = chain.request().newBuilder().addHeader("auth-id", "123 example auth id").build(); //adds an auth id header to the request
                                Log.i(TAG, "Non-register server call detected, applying Auth ID header (" + "123 example auth id" + ") to request");

                                return chain.proceed(request);
                            }
                            return chain.proceed(originalRequest);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();

            Log.d(TAG, "new Retrofit client built");
        } else {
            Log.d(TAG, "Retrofit client exists, reusing");
        }

        return retrofit;
    }

}
