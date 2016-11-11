package com.viethoa.potdemo.widgets.snackbars;

import android.app.Activity;
import android.support.annotation.StringRes;

import com.viethoa.potdemo.R;

/**
 * Created by VietHoa on 22/05/16.
 */
public class BottomSnackBarMessage extends SnackBarMessage<BottomSnackBar> {
    private Activity activity;
    
    public BottomSnackBarMessage(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    //----------------------------------------------------------------------------------------------
    // Show message without closeable button.
    //----------------------------------------------------------------------------------------------

    public void showErrorMessage(@StringRes int message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationError, false);
        showNotification(bottomSnackBar);
    }

    public void showWarningMessage(@StringRes int message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationWaring, false);
        showNotification(bottomSnackBar);
    }

    public void showSuccessMessage(@StringRes int message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationSuccess, false);
        showNotification(bottomSnackBar);
    }

    public void showErrorMessage(String message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationError, false);
        showNotification(bottomSnackBar);
    }

    public void showWarningMessage(String message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationWaring, false);
        showNotification(bottomSnackBar);
    }

    public void showSuccessMessage(String message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationSuccess, false);
        showNotification(bottomSnackBar);
    }

    //----------------------------------------------------------------------------------------------
    // Show message within closeable button
    //----------------------------------------------------------------------------------------------

    public void showErrorMessageCloseable(@StringRes int message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationError, true);
        showNotification(bottomSnackBar);
    }

    public void showWarningMessageCloseable(@StringRes int message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationWaring, true);
        showNotification(bottomSnackBar);
    }

    public void showSuccessMessageCloseable(@StringRes int message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationSuccess, true);
        showNotification(bottomSnackBar);
    }

    public void showErrorMessageCloseable(String message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationError, true);
        showNotification(bottomSnackBar);
    }

    public void showWarningMessageCloseable(String message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationWaring, true);
        showNotification(bottomSnackBar);
    }

    public void showSuccessMessageCloseable(String message) {
        BottomSnackBar bottomSnackBar = new BottomSnackBar(activity);
        bottomSnackBar.init(message, R.color.snackBarColorNotificationSuccess, true);
        showNotification(bottomSnackBar);
    }
}
