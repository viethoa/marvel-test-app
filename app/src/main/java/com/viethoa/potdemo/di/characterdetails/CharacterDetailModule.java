package com.viethoa.potdemo.di.characterdetails;

import android.content.Context;

import com.viethoa.potdemo.caches.CharacterFavoriteMemoryCache;
import com.viethoa.potdemo.di.ActivityScope;
import com.viethoa.potdemo.uis.characterdetails.CharacterDetailViewModel;
import com.viethoa.potdemo.uis.characterdetails.CharacterDetailViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 05/11/2016.
 */
@Module
public class CharacterDetailModule {

    @Provides
    @ActivityScope
    CharacterDetailViewModel provideCharaterDetailViewModel(Context applicationContext, CharacterFavoriteMemoryCache favouriteMemoryCache) {
        return new CharacterDetailViewModelImpl(applicationContext, favouriteMemoryCache);
    }
}
