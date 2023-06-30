package com.example.moviepedia.dto;

public class MovieDetails {
    private final String name, genre, director, screenplay, description;
    private final int imageResourceId;

    public MovieDetails(String name, String genre, String director, String screenplay, String description, int imageResourceId) {
        this.name = name;
        this.genre = genre;
        this.director = director;
        this.screenplay = screenplay;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getScreenplay() {
        return screenplay;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}