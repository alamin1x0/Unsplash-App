package com.developeralamin.onlinephoto;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtlities {

    public static final String BASE_URL = "https://api.unsplash.com";
    public static final String API_KEY = "ZcZFhlZ5A3K6Q8tyVc4ca-VJan4CpwSgg1XN3GbOJfI";


    public static Retrofit retrofit = null;

    public static ApiInterface getApiInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(ApiInterface.class);
    }

}
