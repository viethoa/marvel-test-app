package com.viethoa.potdemo.domain;

import com.viethoa.potdemo.di.ApplicationModule;

import org.junit.Before;
import org.robolectric.RuntimeEnvironment;

/**
 * Created by VietHoa on 12/11/2016.
 */

public abstract class BaseDomainTest {

    protected abstract void init(NetworkComponentTest component);

    @Before
    public void before() throws Exception {
        NetworkComponentTest component = DaggerNetworkComponentTest.builder()
                .applicationModule(new ApplicationModule(RuntimeEnvironment.application))
                .build();
        init(component);
    }
}
