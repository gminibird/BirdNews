package com.zrj.birdnews.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zrj.birdnews.presenter.BasePresenter;
import com.zrj.birdnews.ui.view.MVPBaseView;

/**
 * Created by a on 2018/3/10.
 */

public abstract class MVPBaseActivity<T> extends AppCompatActivity implements MVPBaseView<T>{

    protected BasePresenter mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }

    protected <V extends BasePresenter> V getPresenter(){
        return (V)mPresenter;
    }

}
