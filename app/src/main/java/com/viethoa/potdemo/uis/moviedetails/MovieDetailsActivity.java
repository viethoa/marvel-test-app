package com.viethoa.potdemo.uis.moviedetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseSnackBarActivity;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.moviedetails.DaggerMovieDetailComponent;
import com.viethoa.potdemo.di.moviedetails.MovieDetailComponent;
import com.viethoa.potdemo.di.moviedetails.MovieDetailModule;
import com.viethoa.potdemo.models.Movie;
import com.viethoa.potdemo.uis.main.moviefragment.MovieAdapter;
import com.viethoa.potdemo.utilities.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MovieDetailsActivity extends BaseSnackBarActivity implements DetailViewModel.Listener {
    private static final String EXTRACT_MOVIE = "movie-extract";

    private MovieDetailComponent detailComponent;
    private SimilarMovieAdapter similarMovieAdapter;
    private List<Movie> movieArrayList;
    private boolean isPaginationLoading;
    private int currentPage;
    private Movie movie;
    private MenuItem actionFavorite;

    @Inject
    DetailViewModel detailViewModel;

    @Bind(R.id.img_background)
    ImageView imgBackground;
    @Bind(R.id.img_poster)
    ImageView imgPoster;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @Bind(R.id.tv_release_date)
    TextView tvRelatedDate;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.grid_view_related)
    GridView gridViewRelatedMovie;
    @Bind(R.id.progress_loading)
    View vLoadingProgress;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;

    public static Intent newInstance(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRACT_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        showToolbarBackIcon();
        setToolBarTitle("");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        // Get bundle data
        movie = (Movie) bundle.getSerializable(EXTRACT_MOVIE);

        // Init viewModel
        detailViewModel.initialize(this);
        detailViewModel.onViewCreated(movie);

        // Init grid view
        isPaginationLoading = false;
        gridViewRelatedMovie.setOnScrollListener(new PaginationScrollListener());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detailViewModel.onDestroy();
    }

    //----------------------------------------------------------------------------------------------
    // Menu items
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        actionFavorite = menu.findItem(R.id.actions_favorite);
        detailViewModel.onViewCreated(movie);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actions_favorite:
                detailViewModel.actionFavoriteClicked(movie);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------------------------------
    // Injection
    //----------------------------------------------------------------------------------------------

    protected void injectModule(ApplicationComponent appComponent) {
        detailComponent = DaggerMovieDetailComponent.builder()
                .applicationComponent(appComponent)
                .movieDetailModule(new MovieDetailModule())
                .build();
        detailComponent.inject(this);
    }

    public BaseComponent getComponent() {
        return detailComponent;
    }

    //----------------------------------------------------------------------------------------------
    // ViewModel events
    //----------------------------------------------------------------------------------------------

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        tvDescription.setText(description);
    }

    @Override
    public void setVoteAverage(String imdb) {
        tvVoteAverage.setText(imdb);
    }

    @Override
    public void setReleaseDate(String releaseDate) {
        tvRelatedDate.setText(releaseDate);
    }

    @Override
    public void setImages(String posterUrl, String backgroundUrl) {
        GlideUtils.loadImage(this, posterUrl, GlideUtils.Size.W300, imgPoster, R.drawable.placeholder_image_v);
        GlideUtils.loadImage(this, backgroundUrl, GlideUtils.Size.W500, imgBackground, R.drawable.placeholder_image);
    }

    @Override
    public void setFavoriteMovie() {
        if (actionFavorite != null) {
            actionFavorite.setIcon(getResources().getDrawable(R.mipmap.ic_favourite));
        }
    }

    @Override
    public void setNonFavoriteMovie() {
        if (actionFavorite != null) {
            actionFavorite.setIcon(getResources().getDrawable(R.mipmap.ic_not_favorite));
        }
    }

    @Override
    public void onAPIErrorResponse(String errorMessage) {
        showTopErrorMessage(errorMessage);
        tvNoData.setText(errorMessage);
        tvNoData.setVisibility(View.VISIBLE);
        vLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onGetRelatedMoviesSuccess(List<Movie> movies) {
        if (movies == null || movies.size() == 0) {
            tvNoData.setText(R.string.detail_no_data);
            tvNoData.setVisibility(View.VISIBLE);
            vLoadingProgress.setVisibility(View.GONE);
            return;
        }

        isPaginationLoading = false;
        if (similarMovieAdapter == null) {
            movieArrayList = new ArrayList<>(movies);
            similarMovieAdapter = new SimilarMovieAdapter(this, movieArrayList);
            gridViewRelatedMovie.setAdapter(similarMovieAdapter);
            vLoadingProgress.setVisibility(View.GONE);
        } else {
            movieArrayList.addAll(movies);
            similarMovieAdapter.refreshData(movieArrayList);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Pagination
    //----------------------------------------------------------------------------------------------

    private class PaginationScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int newState) {
        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (isPaginationLoading) {
                return;
            }

            if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                currentPage += 1;
                isPaginationLoading = true;
                detailViewModel.getRelatedMovies(movie.getId(), currentPage);
            }
        }
    }

}
