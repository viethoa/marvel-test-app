package com.viethoa.potdemo.uis.main.moviefavouritefragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseFragment;
import com.viethoa.potdemo.caches.MovieFavouriteMemoryCache;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.main.MainComponent;
import com.viethoa.potdemo.models.Movie;
import com.viethoa.potdemo.uis.main.moviefragment.MovieAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by VietHoa on 05/11/2016.
 */
public class MovieFavouriteFragment extends BaseFragment {

    private MovieAdapter movieAdapter;
    private List<Movie> movieArrayList;

    @Inject
    MovieFavouriteMemoryCache favouriteMemoryCache;

    @Bind(R.id.tv_no_data)
    TextView tvNoDataView;
    @Bind(R.id.grid_view)
    GridView gridViewFavoriteMovie;

    @Override
    protected void injectComponent(BaseComponent component) {
        if (component instanceof MainComponent) {
            ((MainComponent) component).inject(this);
        }
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_movie_favourites, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initialiseView();
    }

    public void initialiseView() {
        movieArrayList = favouriteMemoryCache.get();
        movieAdapter = new MovieAdapter(getContext(), movieArrayList);
        gridViewFavoriteMovie.setAdapter(movieAdapter);

        if (movieArrayList == null || movieArrayList.size() == 0) {
            tvNoDataView.setVisibility(View.VISIBLE);
        } else {
            tvNoDataView.setVisibility(View.GONE);
        }
    }
}
