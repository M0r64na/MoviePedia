package com.example.moviepedia.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.moviepedia.R;
import com.example.moviepedia.util.DatabaseHelper;

public class EditMovieActivity extends AppCompatActivity {
    private EditText name, genre, director, screenplay, description;
    private Intent movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        movieDetails = getIntent();

        name = findViewById(R.id.nameEdit);
        genre = findViewById(R.id.genreEdit);
        director = findViewById(R.id.directorEdit);
        screenplay = findViewById(R.id.screenplayEdit);
        description = findViewById(R.id.descriptionEdit);

        name.setText(movieDetails.getStringExtra("NAME"));
        genre.setText(movieDetails.getStringExtra("GENRE"));
        director.setText(movieDetails.getStringExtra("DIRECTOR"));
        screenplay.setText(movieDetails.getStringExtra("SCREENPLAY"));
        description.setText(movieDetails.getStringExtra("DESCRIPTION"));

        ImageButton imageButton = findViewById(R.id.imageButtonEdit);
        imageButton.setEnabled(false);
    }

    public void onSubmitBtnClicked(View view) {
        SQLiteOpenHelper helper = new DatabaseHelper(EditMovieActivity.this);

        try {
            SQLiteDatabase db = helper.getReadableDatabase();

            int id = movieDetails.getIntExtra("_id", -1);

            DatabaseHelper.editMovie(
                    db,
                    id,
                    name.getText().toString(),
                    genre.getText().toString(),
                    director.getText().toString(),
                    screenplay.getText().toString(),
                    description.getText().toString(),
                    R.drawable.ic_launcher_background
            );
        }
        catch(SQLException ex) {
            Toast.makeText(EditMovieActivity.this , ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        helper.close();

        Intent goToViewMoviesActivity = new Intent(EditMovieActivity.this, MovieDetailsActivity.class);
        goToViewMoviesActivity.putExtra("_id", movieDetails.getIntExtra("_id", -1));
        startActivity(goToViewMoviesActivity);
    }
}