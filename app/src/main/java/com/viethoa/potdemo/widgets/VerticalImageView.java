package com.viethoa.potdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.viethoa.potdemo.Constants;

/**
 * Created by VietHoa on 06/11/2016.
 */

public class VerticalImageView extends ImageView {

    public VerticalImageView(Context context) {
        super(context);
    }

    public VerticalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * Constants.POSTER_IMAGE_RATIO);
        setMeasuredDimension(width, height);
    }
}
