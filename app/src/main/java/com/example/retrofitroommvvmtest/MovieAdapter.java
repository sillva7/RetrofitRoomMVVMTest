package com.example.retrofitroommvvmtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitroommvvmtest.classes.Movie;
import com.example.retrofitroommvvmtest.view.MovieDeatailActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    ArrayList<Movie> movieList;
    Context context;

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public MovieAdapter() {

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        ImageView imageView = holder.imageView;
        TextView textView = holder.textView;
        String imagePath = "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath();
        Glide.with(context)
                .load(imagePath).placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

        textView.setText(movie.getOriginalTitle());


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Movie movie = movieList.get(pos);
                        Intent intent = new Intent(context, MovieDeatailActivity.class);
                        intent.putExtra("movied", movie);
                        context.startActivity(intent);
                    }

                }
            });
        }
    }


}
