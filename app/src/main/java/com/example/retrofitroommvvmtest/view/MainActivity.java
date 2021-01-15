package com.example.retrofitroommvvmtest.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.retrofitroommvvmtest.MovieAdapter;
import com.example.retrofitroommvvmtest.R;
import com.example.retrofitroommvvmtest.model.Movie;
import com.example.retrofitroommvvmtest.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ArrayList<Movie> moviesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);
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
        mainActivityViewModel.getAllMovies().observe(this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> resultList) {
                        moviesList = (ArrayList<Movie>) resultList;
                        fillRecyclerView();
                    }
                });
    }

     void fillRecyclerView() {
        recyclerView = findViewById(R.id.recView);
        adapter = new MovieAdapter(this, moviesList);
        adapter.setMovieList(moviesList);
        recyclerView.setAdapter(adapter);



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
         swipeRefreshLayout.setRefreshing(false);

     }
}