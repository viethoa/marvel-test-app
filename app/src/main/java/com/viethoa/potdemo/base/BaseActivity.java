package com.viethoa.potdemo.base;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.viethoa.potdemo.MyApplication;
import com.viethoa.potdemo.R;
import com.viethoa.potdemo.di.ApplicationComponent;
import com.viethoa.potdemo.di.BaseComponent;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by VietHoa on 05/11/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    private CompositeSubscription compositeSubscription;
    private Dialog loadingDialog;

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDagger();
        initialiseDialogLoading();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (toolBar == null) {
            return;
        }

        setSupportActionBar(toolBar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoadingDialog();
    }

    //----------------------------------------------------------------------------------------------
    // Setup dagger
    //----------------------------------------------------------------------------------------------

    private void initializeDagger() {
        MyApplication application = (MyApplication) getApplication();
        injectModule(application.getComponent());
    }

    protected void injectModule(ApplicationComponent appComponent) {
        // Todo override
    }

    /**
     * For the children of Activity can inject to access dependencies
     */
    public BaseComponent getComponent() {
        return null; // Todo override
    }

    //----------------------------------------------------------------------------------------------
    // Toolbar
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showToolbarBackIcon() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void setToolBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setTitle(title);
    }

    //----------------------------------------------------------------------------------------------
    // Loading Dialog
    //----------------------------------------------------------------------------------------------

    protected void initialiseDialogLoading() {
        loadingDialog = new Dialog(this, R.style.FullScreen_DialogStyle);
        loadingDialog.setContentView(R.layout.dialog_loading_layout);
        loadingDialog.setCancelable(false);
    }

    @Override
    public void showLoadingDialog() {
        if (isFinishing() || loadingDialog == null || loadingDialog.isShowing()) {
            return;
        }

        ImageView imgLoading = (ImageView) loadingDialog.findViewById(R.id.iv_loading);
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.loading_animate);
        imgLoading.startAnimation(rotation);
        rotation.setDuration(800);
        loadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog == null) {
            return;
        }
        if (!loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    // Subscription
    //----------------------------------------------------------------------------------------------

    public synchronized Subscription manageSubscription(Subscription subscription) {
        if (compositeSubscription == null || compositeSubscription.isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
        return subscription;
    }

    public final <T> Observable.Transformer<T, T> bindToMainThread() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //----------------------------------------------------------------------------------------------
    // Helpers function
    //----------------------------------------------------------------------------------------------

    protected void replaceFragment(final Fragment fg, final int containerResId,
                                   final boolean backStacked, final boolean animated) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        if (animated) {
            tx.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        }
        if (backStacked) {
            tx.addToBackStack(fg.getClass().getSimpleName());
        }
        tx.replace(containerResId, fg, fg.getClass().getSimpleName());
        tx.commit();
        fm.executePendingTransactions();
    }

    protected void openUrlInBrowser(String url) {
        if (url == null || url.length() <= 0) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
