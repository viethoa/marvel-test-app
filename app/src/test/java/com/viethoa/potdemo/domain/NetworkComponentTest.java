package com.viethoa.potdemo.domain;

import com.viethoa.potdemo.di.ApplicationModule;

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
}
