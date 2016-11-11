package com.viethoa.potdemo.uis.moviedetails;

import com.viethoa.potdemo.base.BaseView;
import com.viethoa.potdemo.models.Movie;

import java.util.List;

/**
 * Created by VietHoa on 06/11/2016.
 */

public interface DetailViewModel {

    interface Listener extends BaseView {
        void setTitle(String title);

        void setDescription(String description);

        void setVoteAverage(String imdb);

        void setReleaseDate(String releaseDate);

        void setImages(String posterUrl, String backgroundUrl);

        void setFavoriteMovie();

        void setNonFavoriteMovie();

        void onAPIErrorResponse(String errorMessage);

        void onGetRelatedMoviesSuccess(List<Movie> movies);
    }

    void initialize(Listener listener);

    void onViewCreated(Movie movie);

    void getRelatedMovies(long movieID, int page);

    void actionFavoriteClicked(Movie movie);

    void onDestroy();
}
