package com.example.moviepedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.moviepedia.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAddMovieBtnClicked(View view) {
        Intent goToAddMovieActivity = new Intent(MainActivity.this, AddMovieActivity.class);
        startActivity(goToAddMovieActivity);
    }

    public void onViewMoviesBtnClicked(View view) {
        Intent goToViewMoviesActivity = new Intent(MainActivity.this, ViewMoviesActivity.class);
        startActivity(goToViewMoviesActivity);
    }
}