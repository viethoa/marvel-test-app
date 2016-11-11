package com.viethoa.potdemo.di.moviedetails;

import com.viethoa.potdemo.di.ActivityScope;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.uis.moviedetails.MovieDetailsActivity;

import dagger.Component;

/**
 * Created by VietHoa on 05/11/2016.
 */

@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = MovieDetailModule.class
)
public interface MovieDetailComponent extends BaseComponent {

    void inject(MovieDetailsActivity detailsActivity);
}
