package com.example.retrofitroommvvmtest.Services;

import com.example.retrofitroommvvmtest.classes.General;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<General> getPopularMovies(@Query("api_key") String apiKey);


}
