package com.viethoa.potdemo.domain.movie;

import android.content.Context;

import com.viethoa.potdemo.BuildConfig;
import com.viethoa.potdemo.domain.BaseDomainTest;
import com.viethoa.potdemo.domain.CustomInterceptor;
import com.viethoa.potdemo.domain.NetworkComponentTest;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.domain.services.movie.MovieAPIs;
import com.viethoa.potdemo.fakedatafactory.NetworkFakeDataFactory;
import com.viethoa.potdemo.models.Movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;

/**
 * Created by VietHoa on 12/11/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MovieAPIsTest extends BaseDomainTest {

    @Inject
    MovieAPIs movieAPIs;
    @Inject
    Context applicationContext;
    @Inject
    CustomInterceptor customInterceptor;

    @Override
    protected void init(NetworkComponentTest component) {
        component.inject(this);
    }

    /**
     *  Test get movies API
     */

    @Test
    public void testGetMoviesSuccess() throws Exception {
        NetworkFakeDataFactory.getMoviesSuccessResponse(applicationContext, customInterceptor);
        MovieApiResponse<List<Movie>> response = movieAPIs.getMovies(
                any(String.class),
                any(Integer.class)
        ).execute().body();

        List<Movie> data = response.getData();
        assertThat(data.isEmpty(), is(false));
        assertThat(data.size(), equalTo(3));
    }

    @Test
    public void testGetEmptyMovie() throws Exception {
        NetworkFakeDataFactory.getEmptyMovieResponse(applicationContext, customInterceptor);
        MovieApiResponse<List<Movie>> response = movieAPIs.getMovies(
                any(String.class),
                any(Integer.class)
        ).execute().body();

        List<Movie> data = response.getData();
        assertThat(data.isEmpty(), is(true));
    }

    @Test
    public void testGetMoviesError() throws Exception {
        NetworkFakeDataFactory.getMoviesErrorResponse(applicationContext, customInterceptor);
        MovieApiResponse<List<Movie>> response = movieAPIs.getMovies(
                any(String.class),
                any(Integer.class)
        ).execute().body();

        assertThat(response.getException(), notNullValue(Throwable.class));
    }

    /**
     *  Test get similar movies API
     */

    @Test
    public void testGetSimilarMoviesSuccess() throws Exception {
        NetworkFakeDataFactory.getMoviesSuccessResponse(applicationContext, customInterceptor);
        MovieApiResponse<List<Movie>> response = movieAPIs.getSimilarMovies(
                any(Long.class),
                any(String.class),
                any(Integer.class)
        ).execute().body();

        List<Movie> data = response.getData();
        assertThat(data.isEmpty(), is(false));
        assertThat(data.size(), equalTo(3));
    }

    @Test
    public void testGetEmptySimilarMovie() throws Exception {
        NetworkFakeDataFactory.getEmptyMovieResponse(applicationContext, customInterceptor);
        MovieApiResponse<List<Movie>> response = movieAPIs.getSimilarMovies(
                any(Long.class),
                any(String.class),
                any(Integer.class)
        ).execute().body();

        List<Movie> data = response.getData();
        assertThat(data.isEmpty(), is(true));
    }

    @Test
    public void testGetSimilarMoviesError() throws Exception {
        NetworkFakeDataFactory.getMoviesErrorResponse(applicationContext, customInterceptor);
        MovieApiResponse<List<Movie>> response = movieAPIs.getSimilarMovies(
                any(Long.class),
                any(String.class),
                any(Integer.class)
        ).execute().body();

        assertThat(response.getException(), notNullValue(Throwable.class));
    }
}
