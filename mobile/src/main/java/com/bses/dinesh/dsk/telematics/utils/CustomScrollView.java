package com.bses.dinesh.dsk.telematics.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

public class CustomScrollView extends NestedScrollView {
    private ScrollViewListener scrollViewListener = null;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int currLen, int currTop, int prevLen, int prevTop) {
        super.onScrollChanged(currLen, currTop, prevLen, prevTop);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, currLen, currTop, prevLen, prevTop);
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(CustomScrollView scrollView,
                             int currLen, int currTop, int prevLen, int prevTop);
    }
}