package com.zrj.birdnews.presenter;

import android.util.Log;

import com.zrj.birdnews.ui.view.MVPBaseView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by a on 2018/3/10.
 */

public abstract class BasePresenter {

    protected Reference<MVPBaseView> mViewRef;


    public void attachView(MVPBaseView view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends MVPBaseView> T getView() {
        return (T)mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;

    }

}
