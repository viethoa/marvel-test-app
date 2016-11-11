package com.viethoa.potdemo.domain.services.movie;

import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.models.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by VietHoa on 05/11/2016.
 */

public interface MovieService {

    Observable<MovieApiResponse<List<Movie>>> getMovies(int page);

    Observable<MovieApiResponse<List<Movie>>> getSimilarMovies(long movieID, int page);
}
