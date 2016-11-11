package com.viethoa.potdemo.uis.main.moviefragment;

import com.viethoa.potdemo.base.BaseView;
import com.viethoa.potdemo.models.Movie;

import java.util.List;

/**
 * Created by VietHoa on 06/11/2016.
 */

public interface MovieViewModel {

    interface Listener extends BaseView {
        void onAPIErrorResponse(String errorMessage);

        void onGetMoviesSuccess(List<Movie> movies);
    }

    void initialize(Listener listener);

    void getMovies(int page);

    void onDestroy();
}
