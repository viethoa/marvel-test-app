package com.viethoa.potdemo.domain.services.movie;

import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.domain.services.BaseServices;
import com.viethoa.potdemo.models.Movie;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.viethoa.potdemo.Constants.APPLICATION_API_KEY;

/**
 * Created by VietHoa on 05/11/2016.
 */

public class MovieServiceImpl extends BaseServices implements MovieService {

    @Inject
    MovieAPIs movieAPIs;

    @Inject
    public MovieServiceImpl() {
        // Requirement
    }

    @Override
    public Observable<MovieApiResponse<List<Movie>>> getMovies(int page) {
        return Observable.create(subscriber -> {
            handleResponse(movieAPIs.getMovies(APPLICATION_API_KEY, page), null, subscriber);
        });
    }

    @Override
    public Observable<MovieApiResponse<List<Movie>>> getSimilarMovies(long movieID, int page) {
        return Observable.create(subscriber -> {
            handleResponse(movieAPIs.getSimilarMovies(movieID, APPLICATION_API_KEY, page), null, subscriber);
        });
    }
}
