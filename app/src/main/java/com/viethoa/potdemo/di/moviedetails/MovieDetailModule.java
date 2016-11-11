package com.viethoa.potdemo.di.moviedetails;

import android.content.Context;

import com.viethoa.potdemo.caches.MovieFavouriteMemoryCache;
import com.viethoa.potdemo.di.ActivityScope;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.uis.moviedetails.DetailViewModel;
import com.viethoa.potdemo.uis.moviedetails.DetailViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 05/11/2016.
 */
@Module
public class MovieDetailModule {

    @Provides
    @ActivityScope
    DetailViewModel provideDetailViewModel(Context applicationContext,
                                           MovieService movieService,
                                           MovieFavouriteMemoryCache favouriteMemoryCache) {
        return new DetailViewModelImpl(applicationContext, movieService, favouriteMemoryCache);
    }
}
