package com.developeralamin.onlinephoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.developeralamin.onlinephoto.ApiUtlities.API_KEY;

public interface ApiInterface {

    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/photos")
    Call<List<ImageModel>> getImages(
            @Query("page") int page,
            @Query("per_page") int perpage
    );


    @Headers("Authorization: Client-ID "+API_KEY)
    @GET("/search/photos")
    Call<SearchModel> searchImage(
            @Query("query")String query
    );
}
