package com.viethoa.potdemo;

import android.app.Application;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.ApplicationModule;
import com.viethoa.potdemo.di.DaggerApplicationComponent;

/**
 * Created by VietHoa on 03/11/2016.
 */
public class MyApplication extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init dagger
        initializeDagger();
    }

    //----------------------------------------------------------------------------------------------
    // Setup dagger
    //----------------------------------------------------------------------------------------------

    private void initializeDagger() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return appComponent;
    }
}
