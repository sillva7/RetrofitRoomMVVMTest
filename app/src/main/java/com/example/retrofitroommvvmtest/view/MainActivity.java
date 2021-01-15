package com.example.retrofitroommvvmtest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.retrofitroommvvmtest.MovieAdapter;
import com.example.retrofitroommvvmtest.R;
import com.example.retrofitroommvvmtest.Services.MovieApiService;
import com.example.retrofitroommvvmtest.Services.RetrofitInstance;
import com.example.retrofitroommvvmtest.classes.General;
import com.example.retrofitroommvvmtest.classes.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPopularMovies();
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.design_default_color_error));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void getPopularMovies() {
        MovieApiService movieApiService = RetrofitInstance.getService();
        Call<General> call = movieApiService.getPopularMovies(getString(R.string.api_key));
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                General general = response.body();
                Log.d("454545", "onResponse: " + general.toString());
                if (general != null && general.getResults() != null) {
                    movies = (ArrayList<Movie>) general.getResults();
                    fillRecyclerView();

                }


            }

            @Override
            public void onFailure(Call<General> call, Throwable t) {

            }
        });
    }

     void fillRecyclerView() {
        recyclerView = findViewById(R.id.recView);
        adapter = new MovieAdapter(this, movies);
        adapter.setMovieList(movies);
        recyclerView.setAdapter(adapter);



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }
}