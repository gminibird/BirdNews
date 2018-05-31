package com.zrj.birdnews.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zrj.birdnews.R;
import com.zrj.birdnews.presenter.NewsDetailPresenter;
import com.zrj.birdnews.ui.view.MVPBaseView;
import com.zrj.birdnews.util.MyLog;

/**
 * Created by a on 2018/3/20.
 */

public class NewsDetailActivity extends MVPBaseActivity<String> {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activivy_web_view);
        initView();
    }


    private void initView() {
        mWebView = findViewById(R.id.web_view);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        mWebView.setWebViewClient(new WebViewClient() {
        });
        String url = getIntent().getStringExtra("url");
        if (url!=null){
            MyLog.e("=============",url);
            updateData(url);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void updateData(String data,int... flags) {
        mWebView.loadUrl(data);
    }

    @Override
    public NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter();
    }
}
