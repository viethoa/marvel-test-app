package com.viethoa.potdemo.di.main;

import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.ActivityScope;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.uis.main.MainActivity;
import com.viethoa.potdemo.uis.main.characterFragment.CharacterFragment;
import com.viethoa.potdemo.uis.main.characterfavoritefragment.CharacterFavoriteFragment;
import com.viethoa.potdemo.uis.main.moviefavouritefragment.MovieFavouriteFragment;
import com.viethoa.potdemo.uis.main.moviefragment.MovieFragment;

import dagger.Component;

/**
 * Created by VietHoa on 05/11/2016.
 */

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = MainModule.class
)
public interface MainComponent extends BaseComponent {

    void inject(MainActivity mainActivity);

    void inject(MovieFragment movieFragment);

    void inject(MovieFavouriteFragment favouriteFragment);

    void inject(CharacterFragment characterFragment);

    void inject(CharacterFavoriteFragment characterFavoriteFragment);
}
