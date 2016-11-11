package com.viethoa.potdemo.di.characterdetails;

import com.viethoa.potdemo.di.ActivityScope;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.uis.characterdetails.CharacterDetailsActivity;
import com.viethoa.potdemo.uis.moviedetails.MovieDetailsActivity;

import dagger.Component;

/**
 * Created by VietHoa on 05/11/2016.
 */

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = CharacterDetailModule.class
)
public interface CharacterDetailComponent extends BaseComponent {

    void inject(CharacterDetailsActivity detailsActivity);
}
