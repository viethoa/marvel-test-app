package com.viethoa.potdemo.caches;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viethoa.potdemo.models.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VietHoa on 06/11/2016.
 */

public class MovieFavouriteMemoryCache {

    private static final String PREF_KEY_CACHE_MOVIE = "movies-memory-cache";
    private static List<Movie> cacheMovies;
    private SharedPreferences mSharedPreferences;

    public MovieFavouriteMemoryCache(Context context) {
        mSharedPreferences = context.getSharedPreferences("pref_movies_memory_data", Context.MODE_PRIVATE);
        cacheMovies = get();
    }

    public synchronized void clear() {
        cacheMovies = null;
        mSharedPreferences.edit().putString(PREF_KEY_CACHE_MOVIE, "").apply();
        mSharedPreferences.edit().clear().apply();
    }

    //----------------------------------------------------------------------------------------------
    // Movie
    //----------------------------------------------------------------------------------------------

    public synchronized void set(Movie movie) {
        if (movie == null || cacheMovies.contains(movie)) {
            return;
        }

        // memory cache
        cacheMovies.add(movie);

        String json = (new Gson()).toJson(cacheMovies);
        if (TextUtils.isEmpty(json)) {
            return;
        }

        mSharedPreferences.edit().putString(PREF_KEY_CACHE_MOVIE, json).apply();
    }

    public synchronized void set(List<Movie> movies) {
        if (movies == null) {
            return;
        }

        // memory cache
        cacheMovies = movies;

        String json = (new Gson()).toJson(cacheMovies);
        if (TextUtils.isEmpty(json)) {
            return;
        }

        mSharedPreferences.edit().putString(PREF_KEY_CACHE_MOVIE, json).apply();
    }

    public synchronized List<Movie> get() {
        if (cacheMovies != null) {
            return cacheMovies;
        }

        List<Movie> movies = null;
        Type type = new TypeToken<List<Movie>>() {
        }.getType();
        String jsonData = mSharedPreferences.getString(PREF_KEY_CACHE_MOVIE, "");

        //Convert back to User data model
        try {
            movies = (new Gson()).fromJson(jsonData, type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (movies == null) {
                movies = new ArrayList<>();
            }
        }

        cacheMovies = movies;
        return cacheMovies;
    }

    public synchronized boolean contains(Movie movie) {
        if (movie == null) {
            return false;
        }

        List<Movie> movies = get();
        if (movies == null || movies.size() == 0) {
            return false;
        }

        return movies.contains(movie);
    }

    public synchronized boolean remove(Movie movie) {
        if (movie == null) {
            return false;
        }
        List<Movie> movies = get();
        if (movies == null || movies.size() == 0) {
            return false;
        }
        if (!movies.contains(movie)) {
            return false;
        }

        movies.remove(movie);
        set(movies);
        return true;
    }
}
