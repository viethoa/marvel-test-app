package com.viethoa.potdemo.domain.movie;

import android.content.Context;

import com.viethoa.potdemo.BuildConfig;
import com.viethoa.potdemo.domain.BaseDomainTest;
import com.viethoa.potdemo.domain.CustomInterceptor;
import com.viethoa.potdemo.domain.NetworkComponentTest;
import com.viethoa.potdemo.domain.responses.MovieApiResponse;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.fakedatafactory.NetworkFakeDataFactory;
import com.viethoa.potdemo.models.Movie;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;

/**
 * Created by VietHoa on 12/11/2016.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MovieServiceTest extends BaseDomainTest {

    @Inject
    Context applicationContext;
    @Inject
    MovieService movieService;
    @Inject
    CustomInterceptor customInterceptor;

    @Override
    protected void init(NetworkComponentTest component) {
        component.inject(this);
    }

    /**
     * Test get movies API
     */

    @Test
    public void testGetMoviesSuccess() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getMoviesSuccessResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<MovieApiResponse<List<Movie>>> testSubscriber = new TestSubscriber<>();
        movieService.getMovies(any(Integer.class)).subscribe(testSubscriber);

        // Check API
        testSubscriber.assertCompleted();
        assertThat(testSubscriber.getOnNextEvents().size(), IsEqual.equalTo(1));

        // Check response
        MovieApiResponse<List<Movie>> response = testSubscriber.getOnNextEvents().get(0);
        assertThat(response, notNullValue(MovieApiResponse.class));
        assertThat(response.getData().isEmpty(), equalTo(false));
        assertThat(response.getData().size(), equalTo(3));
        assertThat(response.getData().get(0), notNullValue(Movie.class));

        // Check data
        Movie movie = response.getData().get(0);
        assertThat(movie.getId(), equalTo(284052L));
        assertThat(movie.getTitle(), equalTo("Doctor Strange"));
    }

    @Test
    public void testGetEmptyMovie() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getEmptyMovieResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<MovieApiResponse<List<Movie>>> testSubscriber = new TestSubscriber<>();
        movieService.getMovies(any(Integer.class)).subscribe(testSubscriber);

        // Check API
        testSubscriber.assertCompleted();
        assertThat(testSubscriber.getOnNextEvents().size(), IsEqual.equalTo(1));

        // Check response
        MovieApiResponse<List<Movie>> response = testSubscriber.getOnNextEvents().get(0);
        assertThat(response, notNullValue(MovieApiResponse.class));
        assertThat(response.getData().isEmpty(), equalTo(true));
        assertThat(response.getData().size(), equalTo(0));
    }

    @Test
    public void testGetMovieError() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getMoviesErrorResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<MovieApiResponse<List<Movie>>> testSubscriber = new TestSubscriber<>();
        movieService.getMovies(any(Integer.class)).subscribe(testSubscriber);

        // Check exception
        testSubscriber.assertError(Throwable.class);
    }

    /**
     * Test get similar movies API
     */

    @Test
    public void testGetSimilarMoviesSuccess() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getMoviesSuccessResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<MovieApiResponse<List<Movie>>> testSubscriber = new TestSubscriber<>();
        movieService.getSimilarMovies(any(Long.class), any(Integer.class))
                .subscribe(testSubscriber);

        // Check API
        testSubscriber.assertCompleted();
        assertThat(testSubscriber.getOnNextEvents().size(), IsEqual.equalTo(1));

        // Check response
        MovieApiResponse<List<Movie>> response = testSubscriber.getOnNextEvents().get(0);
        assertThat(response, notNullValue(MovieApiResponse.class));
        assertThat(response.getData().isEmpty(), equalTo(false));
        assertThat(response.getData().size(), equalTo(3));
        assertThat(response.getData().get(0), notNullValue(Movie.class));

        // Check data
        Movie movie = response.getData().get(0);
        assertThat(movie.getId(), equalTo(284052L));
        assertThat(movie.getTitle(), equalTo("Doctor Strange"));
    }

    @Test
    public void testGetEmptySimilarMovie() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getEmptyMovieResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<MovieApiResponse<List<Movie>>> testSubscriber = new TestSubscriber<>();
        movieService.getSimilarMovies(any(Long.class), any(Integer.class))
                .subscribe(testSubscriber);

        // Check API
        testSubscriber.assertCompleted();
        assertThat(testSubscriber.getOnNextEvents().size(), IsEqual.equalTo(1));

        // Check response
        MovieApiResponse<List<Movie>> response = testSubscriber.getOnNextEvents().get(0);
        assertThat(response, notNullValue(MovieApiResponse.class));
        assertThat(response.getData().isEmpty(), equalTo(true));
        assertThat(response.getData().size(), equalTo(0));
    }

    @Test
    public void testGetSimilarMovieError() throws Exception {
        // Setup fake data
        NetworkFakeDataFactory.getMoviesErrorResponse(applicationContext, customInterceptor);

        // Trigger API
        TestSubscriber<MovieApiResponse<List<Movie>>> testSubscriber = new TestSubscriber<>();
        movieService.getSimilarMovies(any(Long.class), any(Integer.class))
                .subscribe(testSubscriber);

        // Check exception
        testSubscriber.assertError(Throwable.class);
    }
}
