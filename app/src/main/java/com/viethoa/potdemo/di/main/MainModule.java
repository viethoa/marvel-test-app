package com.viethoa.potdemo.di.main;

import com.viethoa.potdemo.di.ActivityScope;
import com.viethoa.potdemo.domain.services.character.CharacterService;
import com.viethoa.potdemo.domain.services.movie.MovieService;
import com.viethoa.potdemo.uis.main.characterFragment.CharacterViewModel;
import com.viethoa.potdemo.uis.main.characterFragment.CharacterViewModelImpl;
import com.viethoa.potdemo.uis.main.moviefragment.MovieViewModel;
import com.viethoa.potdemo.uis.main.moviefragment.MovieViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 05/11/2016.
 */
@Module
public class MainModule {

    @Provides
    @ActivityScope
    MovieViewModel provideMovieViewModel(MovieService movieService) {
        return new MovieViewModelImpl(movieService);
    }

    @Provides
    @ActivityScope
    CharacterViewModel provideCharacterViewModel(CharacterService characterService) {
        return new CharacterViewModelImpl(characterService);
    }
}
