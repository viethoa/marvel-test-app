package com.viethoa.potdemo.domain;

import com.viethoa.potdemo.di.ApplicationModule;
import com.viethoa.potdemo.domain.character.CharacterAPIsTest;
import com.viethoa.potdemo.domain.character.CharacterServiceTest;
import com.viethoa.potdemo.domain.movie.MovieAPIsTest;
import com.viethoa.potdemo.domain.movie.MovieServiceTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VietHoa on 12/11/2016.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
})
public interface NetworkComponentTest {

    void inject(MovieAPIsTest movieAPITest);

    void inject(CharacterAPIsTest characterAPITest);

    void inject(CharacterServiceTest characterService);

    void inject(MovieServiceTest movieServiceTest);
}
