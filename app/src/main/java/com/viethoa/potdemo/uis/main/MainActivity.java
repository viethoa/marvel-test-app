package com.viethoa.potdemo.uis.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.base.BaseSnackBarActivity;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.BaseComponent;
import com.viethoa.potdemo.di.main.DaggerMainComponent;
import com.viethoa.potdemo.di.main.MainComponent;
import com.viethoa.potdemo.di.main.MainModule;

import butterknife.Bind;

/**
 * Created by VietHoa on 03/11/2016.
 */
public class MainActivity extends BaseSnackBarActivity {

    private MainComponent mainComponent;
    private MainPagerAdapter mainPagerAdapter;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init view pager.
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mainPagerAdapter);
    }

    //----------------------------------------------------------------------------------------------
    // Injection
    //----------------------------------------------------------------------------------------------

    protected void injectModule(ApplicationComponent appComponent) {
        mainComponent = DaggerMainComponent.builder()
                .applicationComponent(appComponent)
                .mainModule(new MainModule())
                .build();
        mainComponent.inject(this);
    }

    public BaseComponent getComponent() {
        return mainComponent;
    }
}
