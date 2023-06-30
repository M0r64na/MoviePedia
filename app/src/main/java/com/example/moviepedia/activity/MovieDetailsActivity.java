package com.example.moviepedia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviepedia.R;
import com.example.moviepedia.dto.MovieDetails;
import com.example.moviepedia.util.DatabaseHelper;

public class MovieDetailsActivity extends AppCompatActivity {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private int _id;
    private MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        TextView name = findViewById(R.id.namePro);
        TextView genre = findViewById(R.id.genrePro);
        TextView director = findViewById(R.id.directorPro);
        TextView screenplay = findViewById(R.id.screenplayPro);
        ScrollView description = findViewById(R.id.descriptionPro);

        helper = new DatabaseHelper(MovieDetailsActivity.this);

        try {
            db = helper.getReadableDatabase();

            _id = getIntent().getIntExtra("_id", -1);
            movieDetails = DatabaseHelper.getMovieById(db, _id);

            name.setText(movieDetails.getName());
            genre.setText(movieDetails.getGenre());
            director.setText(movieDetails.getDirector());
            screenplay.setText(movieDetails.getScreenplay());

            TextView temp = new TextView(MovieDetailsActivity.this);
            temp.setText(movieDetails.getDescription());
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            description.removeAllViews();
            description.addView(temp);
        }
        catch(SQLException ex) {
            Toast.makeText(MovieDetailsActivity.this , ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        helper.close();
    }

    public void onEditBtnClicked(View view) {
        Intent goToEditMovieActivity = new Intent(MovieDetailsActivity.this, EditMovieActivity.class);

        goToEditMovieActivity.putExtra("_id", _id);
        goToEditMovieActivity.putExtra("NAME", movieDetails.getName());
        goToEditMovieActivity.putExtra("GENRE", movieDetails.getGenre());
        goToEditMovieActivity.putExtra("DIRECTOR", movieDetails.getDirector());
        goToEditMovieActivity.putExtra("SCREENPLAY", movieDetails.getScreenplay());
        goToEditMovieActivity.putExtra("DESCRIPTION", movieDetails.getDescription());

        startActivity(goToEditMovieActivity);
    }

    public void onDeleteBtnClicked(View view) {
        DatabaseHelper.deleteMovie(db, _id);

        Intent goToViewMoviesActivity = new Intent(MovieDetailsActivity.this, ViewMoviesActivity.class);
        startActivity(goToViewMoviesActivity);
    }
}