package com.viethoa.potdemo.caches;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 06/11/2016.
 */
@Module
public class CacheModule {

    @Provides
    @Singleton
    CacheManager provideCacheManager(Context applicationContext) {
        return new CacheManager(applicationContext);
    }

    @Provides
    @Singleton
    MovieFavouriteMemoryCache provideFavouriteMemoryCache(Context applicationContext) {
        return new MovieFavouriteMemoryCache(applicationContext);
    }

    @Provides
    @Singleton
    CharacterFavoriteMemoryCache provideCharacterFavoriteMemoryCache(Context applicationContext) {
        return new CharacterFavoriteMemoryCache(applicationContext);
    }
}
