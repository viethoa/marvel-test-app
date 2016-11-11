package com.viethoa.potdemo.utilities;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by VietHoa on 08/06/16.
 */
public class KeyBoardUtils {

    public interface softKeyBoardListener {
        void onSoftKeyBoardHidden();

        void onSoftKeyBoardShowing();
    }

    public static void detectSoftKeyBoard(final View rootView, final softKeyBoardListener listener) {

        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //rect will be populated with the coordinates of your view that area still visible.
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);

                int heightDiff = rootView.getRootView().getHeight() - (rect.bottom - rect.top);
                if (heightDiff > 300) // if more than 300 pixels, its probably a keyboard
                    listener.onSoftKeyBoardShowing(); // keyboard is showing

                else listener.onSoftKeyBoardHidden();  // keyboard is hidden
            }
        });
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
