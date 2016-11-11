package com.viethoa.potdemo.uis.main.moviefragment;

import com.viethoa.potdemo.base.BaseViewModel;
import com.viethoa.potdemo.base.BriefObserver;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.models.Movie;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 06/11/2016.
 */

public class MovieViewModelImpl extends BaseViewModel<MovieViewModel.Listener> implements MovieViewModel {

    private final MovieService movieService;

    public MovieViewModelImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void getMovies(int page) {
        if (listener == null) {
            return;
        }

        manageSubscription(movieService.getMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BriefObserver<MovieApiResponse<List<Movie>>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onAPIErrorResponse(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(MovieApiResponse<List<Movie>> response) {
                        if (listener != null) {
                            listener.onGetMoviesSuccess(response.getData());
                        }
                    }
                }));
    }
}
