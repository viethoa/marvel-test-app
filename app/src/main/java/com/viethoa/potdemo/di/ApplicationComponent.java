package com.viethoa.potdemo.di;

import android.content.Context;

import com.viethoa.potdemo.caches.CharacterFavoriteMemoryCache;
import com.viethoa.potdemo.caches.MovieFavouriteMemoryCache;
import com.viethoa.potdemo.domain.services.character.CharacterService;
import com.viethoa.potdemo.domain.services.movie.MovieService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VietHoa on 06/08/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getApplicationContext();

    MovieService getMovieService();

    CharacterService getCharacterService();

    MovieFavouriteMemoryCache getFavouriteMemoryCache();

    CharacterFavoriteMemoryCache getCharacterFavoriteMemoryCache();
}
