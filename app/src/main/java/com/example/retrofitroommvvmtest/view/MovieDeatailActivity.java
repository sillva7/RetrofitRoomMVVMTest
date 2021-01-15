package com.example.retrofitroommvvmtest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofitroommvvmtest.R;
import com.example.retrofitroommvvmtest.model.Movie;

public class MovieDeatailActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView detailImage;
    private TextView title;
    private TextView rank;
    private TextView overview;
    String TAG = "454545";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_deatail);
        detailImage = findViewById(R.id.detailImage);
        title = findViewById(R.id.title);
        rank = findViewById(R.id.rank);
        overview = findViewById(R.id.overview);
        Intent intent = getIntent();
//        Log.d(TAG, "onCreate: " + intent.hasExtra("movied"));
//        movie = intent.getParcelableExtra("movied");


        if(intent != null && intent.hasExtra("movied"))
        {
            movie = intent.getParcelableExtra("movied");
            String imagePath = "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath();
            Glide.with(this)
                    .load(imagePath)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(detailImage);
            title.setText(movie.getOriginalTitle());
            rank.setText(movie.getVoteAverage()+"");
            overview.setText(movie.getOverview());

        }

    }
}