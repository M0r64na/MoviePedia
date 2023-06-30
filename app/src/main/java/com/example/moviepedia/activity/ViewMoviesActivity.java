package com.example.moviepedia.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviepedia.R;
import com.example.moviepedia.util.DatabaseHelper;

public class ViewMoviesActivity extends AppCompatActivity {
    private SQLiteOpenHelper helper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movies);

        helper = new DatabaseHelper(ViewMoviesActivity.this);

       try {
           SQLiteDatabase db = helper.getReadableDatabase();

           cursor = db.query(
                    "MOVIES",
                    new String[] {"_id","NAME", "GENRE"},
                    null, null, null, null, null
            );

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.activity_list_item,
                    cursor,
                    new String[] {"NAME", "GENRE"},
                    new int[] {android.R.id.text1},
                    0
            )
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView textView = view.findViewById(android.R.id.text1);

                    String name = cursor.getString(1);
                    String genre = cursor.getString(2);

                    textView.setText(String.format("%s\n%s", name, genre));
                    textView.setTextSize(20);

                    return view;
                }
            };

            ListView movieList = findViewById(R.id.movieList);
            movieList.setAdapter(adapter);

            AdapterView.OnItemClickListener listener = (parent, view, position, id) -> {
                Intent goToMovieDetailsActivity = new Intent(ViewMoviesActivity.this, MovieDetailsActivity.class);

                int _id = cursor.getInt(0);
                goToMovieDetailsActivity.putExtra("_id", _id);

                startActivity(goToMovieDetailsActivity);
            };

            movieList.setOnItemClickListener(listener);

        }
        catch(SQLException ex) {
            Toast.makeText(ViewMoviesActivity.this , ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        helper.close();
        cursor.close();
    }
}