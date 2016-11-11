package com.viethoa.potdemo.uis.main.moviefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseSnackBarFragment;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.main.MainComponent;
import com.viethoa.potdemo.models.Movie;
import com.viethoa.potdemo.uis.main.characterFragment.CharacterAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by VietHoa on 05/11/2016.
 */
public class MovieFragment extends BaseSnackBarFragment implements MovieViewModel.Listener {

    private MovieAdapter movieAdapter;
    private List<Movie> movieArrayList;
    private boolean isPaginationLoading;
    private int currentPage;

    @Inject
    MovieViewModel movieViewModel;

    @Bind(R.id.grid_view)
    GridView gridViewMovie;
    @Bind(R.id.progress_loading)
    View vLoadingProgress;
    @Bind(R.id.swipe_to_refresh)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.tv_no_data)
    TextView tvNoData;

    @Override
    protected void injectComponent(BaseComponent component) {
        if (component instanceof MainComponent) {
            ((MainComponent) component).inject(this);
        }
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init viewModel
        movieViewModel.initialize(this);

        // Init grid view
        isPaginationLoading = false;
        gridViewMovie.setOnScrollListener(new PaginationScrollListener());

        // Init swipe to refresh
        refreshLayout.setOnRefreshListener(new PullToRefreshCallBack());
        refreshLayout.setColorSchemeResources(
                R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPage = 0;
        movieArrayList = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieViewModel.onDestroy();
    }

    //----------------------------------------------------------------------------------------------
    // ViewModel events
    //----------------------------------------------------------------------------------------------

    @Override
    public void onAPIErrorResponse(String errorMessage) {
        refreshLayout.setRefreshing(false);
        showTopErrorMessage(errorMessage);
        tvNoData.setText(errorMessage);
        tvNoData.setVisibility(View.VISIBLE);
        vLoadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onGetMoviesSuccess(List<Movie> movies) {
        isPaginationLoading = false;
        refreshLayout.setRefreshing(false);
        vLoadingProgress.setVisibility(View.GONE);

        if (movieArrayList == null) {
            movieArrayList = new ArrayList<>(movies);
        } else {
            movieArrayList.addAll(movies);
        }

        if (gridViewMovie.getAdapter() == null) {
            movieAdapter = new MovieAdapter(getContext(), movieArrayList);
            gridViewMovie.setAdapter(movieAdapter);
        } else {
            movieAdapter.refreshData(movieArrayList);
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
                movieViewModel.getMovies(currentPage);
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // Swipe to refresh
    //----------------------------------------------------------------------------------------------

    private class PullToRefreshCallBack implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            currentPage = 1;
            isPaginationLoading = true;
            movieArrayList = new ArrayList<>();
            movieViewModel.getMovies(currentPage);
        }
    }
}
