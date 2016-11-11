package com.viethoa.potdemo.uis.moviedetails;

import android.content.Context;
import android.text.TextUtils;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseViewModel;
import com.viethoa.potdemo.base.BriefObserver;
import com.viethoa.potdemo.caches.MovieFavouriteMemoryCache;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.models.Movie;

import java.text.NumberFormat;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 06/11/2016.
 */

public class DetailViewModelImpl extends BaseViewModel<DetailViewModel.Listener> implements DetailViewModel {

    private Context applicationContext;
    private MovieService movieService;
    private MovieFavouriteMemoryCache favouriteMemoryCache;

    public DetailViewModelImpl(Context applicationContext,
                               MovieService movieService,
                               MovieFavouriteMemoryCache favouriteMemoryCache) {
        this.movieService = movieService;
        this.applicationContext = applicationContext;
        this.favouriteMemoryCache = favouriteMemoryCache;
    }

    @Override
    public void onViewCreated(Movie movie) {
        if (listener == null || movie == null) {
            return;
        }

        // Title
        String title = movie.getTitle();
        if (!TextUtils.isEmpty(title)) {
            listener.setTitle(title);
        }

        // Description
        String description = movie.getOverview();
        if (!TextUtils.isEmpty(description)) {
            listener.setDescription(description);
        }

        // Release date
        String releaseDate = movie.getReleaseDate();
        if (!TextUtils.isEmpty(releaseDate)) {
            listener.setReleaseDate(releaseDate);
        }

        // Vote average
        if (movie.getVoteAverage() == 0) {
            listener.setVoteAverage(String.format("IMDB: %s",
                    applicationContext.getResources().getString(R.string.na)));
        } else {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(1);
            formatter.setMaximumFractionDigits(1);
            listener.setVoteAverage(String.format("IMDB: %s",
                    formatter.format(movie.getVoteAverage())));
        }

        // Favorite
        if (favouriteMemoryCache.contains(movie)) {
            listener.setFavoriteMovie();
        } else {
            listener.setNonFavoriteMovie();
        }

        // Background
        listener.setImages(movie.getImageUrl(), movie.getBackgroundImageUrl());
    }

    @Override
    public void actionFavoriteClicked(Movie movie) {
        if (listener == null || movie == null) {
            return;
        }

        boolean isRemoved = favouriteMemoryCache.remove(movie);
        if (isRemoved) {
            listener.setNonFavoriteMovie();
        } else {
            favouriteMemoryCache.set(movie);
            listener.setFavoriteMovie();
        }
    }

    @Override
    public void getRelatedMovies(long movieID, int page) {
        if (listener == null) {
            return;
        }

        manageSubscription(movieService.getSimilarMovies(movieID, page)
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
                            listener.onGetRelatedMoviesSuccess(response.getData());
                        }
                    }
                }));
    }
}
