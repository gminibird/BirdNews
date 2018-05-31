package com.zrj.birdnews.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.gminibird.birdrecyclerview.view.RefreshView;
import com.zrj.birdnews.presenter.BasePresenter;
import com.zrj.birdnews.ui.view.MVPBaseView;

/**
 * Created by a on 2018/3/11.
 */

public abstract class MVPBaseFragment<V> extends Fragment implements MVPBaseView<V>{


    protected BasePresenter mPresenter;
    protected ProgressDialog mDialog1;
    protected Dialog mDialog;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    public void showLoading(){

        if (mDialog==null){
            mDialog = new Dialog(getContext());
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mDialog.setContentView(new RefreshView(getContext()),params);
        }
        if (mDialog1 ==null){
            mDialog1 =new ProgressDialog(getContext());
            mDialog1.setMessage("loading...");
        }
        mDialog1.show();
    }


    public   void hideLoading(){
        if (mDialog1 !=null){
            mDialog1.dismiss();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
    }
}
