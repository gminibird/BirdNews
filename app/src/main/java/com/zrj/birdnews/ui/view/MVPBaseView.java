package com.zrj.birdnews.ui.view;

import com.zrj.birdnews.presenter.BasePresenter;

import java.util.List;

/**
 * Created by a on 2018/3/10.
 */

public interface MVPBaseView<T> {

    void showLoading();

    void hideLoading();

    void updateData(T data,int... flags);

    BasePresenter createPresenter();
}
