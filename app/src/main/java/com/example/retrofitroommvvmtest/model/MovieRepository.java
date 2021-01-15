package com.example.retrofitroommvvmtest.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitroommvvmtest.R;
import com.example.retrofitroommvvmtest.Services.MovieApiService;
import com.example.retrofitroommvvmtest.Services.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData() {

        MovieApiService movieApiService = RetrofitInstance.getService();
        Call<General> call = movieApiService.getPopularMovies(application.getApplicationContext().getString(R.string.api_key));
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                General general = response.body();
                Log.d("454545", "onResponse: " + general.toString());
                if (general != null && general.getResults() != null) {
                    movies = (ArrayList<Movie>) general.getResults();
                    mutableLiveData.setValue(movies);
                    Log.d("454545", "onResponse: "+movies.toString());
                }
            }
            @Override
            public void onFailure(Call<General> call, Throwable t) {
            }
        });

        return mutableLiveData;
    }
}
