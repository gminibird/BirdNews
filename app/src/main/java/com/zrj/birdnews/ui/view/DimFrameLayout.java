package com.zrj.birdnews.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.zrj.birdnews.R;

public class DimFrameLayout extends FrameLayout {

    private boolean isIntercept;
    private ValueAnimator mAlphaAnimator;
    private int mAlpha;
    private OnTouchCallback mCallback;

    public DimFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public DimFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DimFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setForeground(getResources().getDrawable(R.drawable.dim));
        getForeground().setAlpha(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isIntercept) {
            if (mCallback != null) {
                mCallback.onTouchEvent(ev);
            }
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setFgAlpha(int alpha, int duration) {
        mAlpha = alpha;
        if (mAlphaAnimator == null) {
            mAlphaAnimator = ValueAnimator.ofInt(0, alpha);
            mAlphaAnimator.setInterpolator(new LinearInterpolator());
            mAlphaAnimator.setDuration(duration);
            mAlphaAnimator.setRepeatCount(0);
            mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    getForeground().setAlpha((int) animation.getAnimatedValue());
                }
            });
        }
        mAlphaAnimator.setIntValues(0, alpha);
        mAlphaAnimator.start();
    }

    public void interceptTouchEvent(boolean intercept) {
        isIntercept = intercept;
    }

    public void interceptTouchEvent(boolean intercept, OnTouchCallback callback) {
        isIntercept = intercept;
        mCallback = callback;
    }

    public void restoreFgAlpha() {
        if (mAlphaAnimator != null) {
            mAlphaAnimator.setIntValues(mAlpha, 0);
            mAlphaAnimator.start();
        }
    }

    public void setOnTouchCallback(OnTouchCallback callback) {
        mCallback = callback;
    }

    public interface OnTouchCallback {
        void onTouchEvent(MotionEvent e);
    }
}
