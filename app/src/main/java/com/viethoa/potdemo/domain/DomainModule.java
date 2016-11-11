package com.viethoa.potdemo.domain;

import android.content.Context;

import com.viethoa.potdemo.domain.services.character.CharacterAPIs;
import com.viethoa.potdemo.domain.services.character.CharacterService;
import com.viethoa.potdemo.domain.services.character.CharacterServiceImpl;
import com.viethoa.potdemo.domain.services.movie.MovieAPIs;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.domain.services.movie.MovieServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 05/11/2016.
 */
@Module
public class DomainModule {

    @Provides
    @Singleton
    CustomInterceptor provideCustomInterceptor(Context applicationContext) {
        return new CustomInterceptor(applicationContext);
    }

    @Provides
    MovieAPIs provideMovieAPIs(RetrofitAPIService retrofitAPIService) {
        return retrofitAPIService.getMovieAPIs();
    }

    @Provides
    @Singleton
    MovieService provideMovieService(MovieServiceImpl service) {
        return service;
    }

    @Provides
    CharacterAPIs provideCharacterAPIs(RetrofitAPIService retrofitAPIService) {
        return retrofitAPIService.getCharacterAPIs();
    }

    @Provides
    @Singleton
    CharacterService provideCharacterService(CharacterServiceImpl service) {
        return service;
    }
}
