package com.viethoa.potdemo.domain.services.movie;

import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by VietHoa on 05/11/2016.
 */

public interface MovieAPIs {

    @GET("movie/now_playing")
    Call<MovieApiResponse<List<Movie>>> getMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/{movieID}/similar")
    Call<MovieApiResponse<List<Movie>>> getSimilarMovies(
            @Path("movieID") long movieID,
            @Query("api_key") String apiKey,
            @Query("Page") int page
    );
}
