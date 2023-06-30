package com.example.moviepedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviepedia.R;
import com.example.moviepedia.util.DatabaseHelper;

public class AddMovieActivity extends AppCompatActivity {
    private EditText name, genre, director, screenplay, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        name = findViewById(R.id.name);
        genre = findViewById(R.id.genre);
        director = findViewById(R.id.director);
        screenplay = findViewById(R.id.screenplay);
        description = findViewById(R.id.description);

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setEnabled(false);
    }

    public void onSubmitBtnClicked(View view) {
        SQLiteOpenHelper helper = new DatabaseHelper(AddMovieActivity.this);

        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            DatabaseHelper.createMovie(
                    db,
                    name.getText().toString(),
                    genre.getText().toString(),
                    director.getText().toString(),
                    screenplay.getText().toString(),
                    description.getText().toString(),
                    R.drawable.ic_launcher_background
            );
        }
        catch(SQLException ex) {
            Toast.makeText(AddMovieActivity.this , ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        helper.close();

        Intent goToViewMoviesActivity = new Intent(AddMovieActivity.this, ViewMoviesActivity.class);
        startActivity(goToViewMoviesActivity);
    }
}