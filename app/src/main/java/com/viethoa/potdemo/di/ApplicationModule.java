package com.viethoa.potdemo.di;

import android.content.Context;

import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.viethoa.potdemo.caches.CacheManager;
import com.viethoa.potdemo.caches.CacheModule;
import com.viethoa.potdemo.domain.DomainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 06/08/2016.
 */

@Module(
        includes = {
                CacheModule.class,
                DomainModule.class
        }
)
public final class ApplicationModule {

    private Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return applicationContext;
    }
}
