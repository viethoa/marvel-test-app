package com.viethoa.potdemo.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.viethoa.potdemo.R;
import com.viethoa.potdemo.di.BaseComponent;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by VietHoa on 05/11/2016.
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    private CompositeSubscription mCompositeSubscription;
    private Dialog loadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Dependencies
        BaseActivity activity = (BaseActivity) getActivity();
        injectComponent(activity.getComponent());

        // Loading dialog
        initialiseDialogLoading();
    }

    protected void injectComponent(BaseComponent component) {
        // Todo override
    }

    //----------------------------------------------------------------------------------------------
    // Configuration
    //----------------------------------------------------------------------------------------------

    protected abstract View setContentView(LayoutInflater inflater, ViewGroup container);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = setContentView(inflater, container);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
        if(loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }

    }

    //----------------------------------------------------------------------------------------------
    // Subscriptions
    //----------------------------------------------------------------------------------------------

    public synchronized Subscription manageSubscription(Subscription subscription) {
        if (isAdded()) {
            if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
                mCompositeSubscription = new CompositeSubscription();
            }
            mCompositeSubscription.add(subscription);
            return subscription;
        }
        return null;
    }

    public final <T> Observable.Transformer<T, T> bindToMainThread() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //----------------------------------------------------------------------------------------------
    // Loading Dialog
    //----------------------------------------------------------------------------------------------

    protected void initialiseDialogLoading() {
        loadingDialog = new Dialog(getContext(), R.style.FullScreen_DialogStyle);
        loadingDialog.setContentView(R.layout.dialog_loading_layout);
        loadingDialog.setCancelable(false);
    }

    @Override
    public void showLoadingDialog() {
        if (loadingDialog == null || loadingDialog.isShowing()) {
            return;
        }

        ImageView imgLoading = (ImageView) loadingDialog.findViewById(R.id.iv_loading);
        Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_animate);
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

}
