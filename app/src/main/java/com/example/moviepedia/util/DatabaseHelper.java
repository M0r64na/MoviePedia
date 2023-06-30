package com.example.moviepedia.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.moviepedia.R;
import com.example.moviepedia.dto.MovieDetails;

import org.jetbrains.annotations.NotNull;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "MoviePedia";
    public static int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
        initializeDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createDatabase(db);
        initializeDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    private void createDatabase(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE MOVIES(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "GENRE TEXT," +
                "DIRECTOR TEXT," +
                "SCREENPLAY TEXT," +
                "DESCRIPTION TEXT," +
                "IMAGERESOURCEID INTEGER);";

        db.execSQL(createQuery);
    }

    private void initializeDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        createMovie(
                db,
                "Fast and Furious 10",
                "Action, Comedy, Crime",
                "Louis Leterrier",
                "Justin Lin, Dan Mazeau",
                "Over many missions and against impossible odds, Dom Toretto and his family have " +
                        "outsmarted, out-nerved and out driven every foe in their path. Now, they confront " +
                        "the most lethal opponent they've ever faced: A terrifying threat emerging from the " +
                        "shadows of the past who's fueled by blood revenge, and who is determined to shatter" +
                        "this family and destroy everything—and everyone—that Dom loves, forever.",
                R.drawable.ic_launcher_background
        );
        createMovie(
                db,
                "John Wick: Chapter 4",
                "Action, Thriller, Crime",
                "Chad Stahelski",
                "Michael Finch, Shay Hatten",
                "With the price on his head ever increasing, John Wick uncovers a path to defeating " +
                        "The High Table. But before he can earn his freedom, Wick must face off against a " +
                        "new enemy with powerful alliances across the globe and forces that turn old friends " +
                        "into foes.",
                R.drawable.ic_launcher_background
        );
    }
    public static void createMovie(SQLiteDatabase db,
                                   @NotNull String name,
                                   @NotNull String genre,
                                   @NotNull String director,
                                   @NotNull String screenplay,
                                   @NotNull String description,
                                   int imageResourceId) {
        ContentValues values = new ContentValues();

        values.put("NAME", name);
        values.put("GENRE", genre);
        values.put("DIRECTOR", director);
        values.put("SCREENPLAY", screenplay);
        values.put("DESCRIPTION", description);
        values.put("IMAGERESOURCEID", imageResourceId);

        db.insertOrThrow("MOVIES", null, values);
    }

    public static void editMovie(SQLiteDatabase db,
                                 int id,
                                 @NotNull String name,
                                 @NotNull String genre,
                                 @NotNull String director,
                                 @NotNull String screenplay,
                                 @NotNull String description,
                                 int imageResourceId) {
        ContentValues values = createContentValues(db, name, genre, director, screenplay, description, imageResourceId);

        db.update("MOVIES", values, "_id = ?", new String[] {String.valueOf(id)});
        db.close();
    }

    public static void deleteMovie(SQLiteDatabase db, int id) {
        db.delete("MOVIES", "_id = ?", new String[] {String.valueOf(id)});
        db.close();
    }

    @SuppressLint("DefaultLocale")
    public static MovieDetails getMovieById(SQLiteDatabase db, int id) {
        String getByIdQuery = String.format("SELECT * FROM MOVIES WHERE _id = %d", id);

        Cursor movieCursor = db.rawQuery(getByIdQuery, null);
        if(!movieCursor.moveToFirst()) throw new SQLException("No movie with such id!");

        String name = movieCursor.getString(1);
        String genre = movieCursor.getString(2);
        String director = movieCursor.getString(3);
        String screenplay = movieCursor.getString(4);
        String description = movieCursor.getString(5);
        int imageResourceId = movieCursor.getInt(6);

        movieCursor.close();

        return new MovieDetails(name, genre, director, screenplay, description, imageResourceId);
    }

    private static ContentValues createContentValues(SQLiteDatabase db,
                                                     @NotNull String name,
                                                     @NotNull String genre,
                                                     @NotNull String director,
                                                     @NotNull String screenplay,
                                                     @NotNull String description,
                                                     int imageResourceId) {
        ContentValues values = new ContentValues();

        values.put("NAME", name);
        values.put("GENRE", genre);
        values.put("DIRECTOR", director);
        values.put("SCREENPLAY", screenplay);
        values.put("DESCRIPTION", description);
        values.put("IMAGERESOURCEID", imageResourceId);

        return values;
    }
}