package com.zrj.birdnews.ui.activity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.gminibird.birdrecyclerview.adapter.RecyclerAdapter;
import com.gminibird.birdrecyclerview.item.IRecyclerItem;
import com.zrj.birdnews.R;
import com.zrj.birdnews.adapter.PagerAdapter;
import com.zrj.birdnews.presenter.BasePresenter;
import com.zrj.birdnews.presenter.SearchPresenter;
import com.zrj.birdnews.ui.fragment.NewsFragment;
import com.zrj.birdnews.ui.view.DimFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends MVPBaseActivity<List<IRecyclerItem>> implements View.OnClickListener {

    private NewsFragment mNewsFragment;
    private EditText mSearchEdit;
    private DimFrameLayout mDimView;
    private View mAppBar;
    private PopupWindow mSearchWindow;
    private PopupWindow mMoreWindow;
    private View mRootView;
    private ImageView mIcMore;

    private SearchPresenter mPresenter;
    private RecyclerAdapter mSearchAdapter;
    private InputMethodManager mInputManager;
    private List<IRecyclerItem> mRelatedList = new ArrayList<>();
    private DimFrameLayout.OnTouchCallback mDimCallback;
    private RecyclerAdapter.OnItemClickListener mCategoryItemListener;
    private WindowManager.LayoutParams mCategoryListParams;
    private boolean canShowSearchResult = false;
    private int mDimLayer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getPresenter();
        setContentView(R.layout.activity_news_main);
        init();
    }

    @Override
    public BasePresenter createPresenter() {
        return new SearchPresenter();
    }

    private void init() {
        mDimView = findViewById(R.id.dim_view);
        mIcMore = findViewById(R.id.action_more);
        mRootView = findViewById(R.id.root_view);
        mAppBar = findViewById(R.id.app_bar);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        final FragmentPagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), getFragments());
        viewPager.setAdapter(adapter);
        mSearchEdit = findViewById(R.id.search_edit);
        mSearchEdit.setCursorVisible(false);
        mSearchEdit.addTextChangedListener(new SearchTextWatcher());
        mSearchEdit.setOnClickListener(this);
        mIcMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_edit:
                if (mInputManager == null) {
                    mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                }
                addSearchWindow();
                mSearchEdit.setCursorVisible(true);
                if (!isDim()) {
                    setViewDim();
                } else {
                    mDimLayer++;
                }
                break;
            case R.id.action_more:
                addMoreWindow();
                if (!mMoreWindow.isShowing()) {
                    if (!isDim()) {
                        setViewDim();
                    } else {
                        mDimLayer++;
                    }
                    mMoreWindow.showAsDropDown(mAppBar, mAppBar.getWidth(), 0);
                } else {
                    mMoreWindow.dismiss();
                    restoreLight();
                }
                break;
        }
    }

    public void dismissMoreWindow(){
        mMoreWindow.dismiss();
        restoreLight();
    }

    private void addMoreWindow() {
        if (mMoreWindow == null) {
            mMoreWindow = new PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            mMoreWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mMoreWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    public void setMoreWindowContentView(View view) {
        if (mMoreWindow == null) {
            addMoreWindow();
        }
        mMoreWindow.setContentView(view);
    }


    private boolean isDim() {
        return mDimLayer != 0;
    }

    private void restoreLight() {
        mDimLayer--;
        if (!isDim()) {
            mDimView.restoreFgAlpha();
            mDimView.interceptTouchEvent(false);
        }
    }

    private void setViewDim() {
        configDimView();
        mDimLayer++;
    }

    private void configDimView() {
        mDimView.setFgAlpha(150, 300);
        if (mDimCallback == null) {
            mDimCallback = new DimFrameLayout.OnTouchCallback() {
                @Override
                public void onTouchEvent(MotionEvent e) {
                    if (e.getAction() == MotionEvent.ACTION_UP) {
                        if (mSearchWindow != null && mInputManager.isActive()) {
                            setSearchEditTrigger();
                        }
                        if (mMoreWindow != null && mMoreWindow.isShowing()) {
                            setIcMoreTrigger();
                        }
                        if (isDim()) {
                            mDimLayer = 1;
                            restoreLight();
                        }
                    }
                }
            };
        }
        mDimView.interceptTouchEvent(true, mDimCallback);
    }

    private void setIcMoreTrigger() {
        if (isDim()) {
            mMoreWindow.dismiss();
        }
    }

    private void setSearchEditTrigger() {
        if (isDim()) {
            mSearchEdit.setCursorVisible(false);
            mDimView.interceptTouchEvent(false);
            mSearchWindow.dismiss();
            mSearchEdit.setText("");
            hideSoftInput();
        }
    }

    private void addSearchWindow() {
        if (mSearchWindow == null) {
            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mSearchAdapter = new RecyclerAdapter(this, mRelatedList);
            mSearchAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                }
            });
            recyclerView.setAdapter(mSearchAdapter);
            mSearchWindow = new PopupWindow(recyclerView,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            mSearchWindow.setContentView(recyclerView);
            mSearchWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mSearchWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    private List<NewsFragment> getFragments() {
        List<NewsFragment> fragments = new ArrayList<>();
        mNewsFragment = new NewsFragment();
        fragments.add(mNewsFragment);
        return fragments;
    }

    private void hideSoftInput() {
        if (mInputManager == null) {
            mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        mInputManager.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void updateData(final List<IRecyclerItem> data, int... flags) {
        if (!canShowSearchResult) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRelatedList.clear();
                mRelatedList.addAll(data);
                if (mRelatedList.size() > 0) {
                    if (!mSearchWindow.isShowing()) {
                        mSearchWindow.showAsDropDown(mAppBar);
                    }
                    mSearchAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    class SearchTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() == 0) {
                canShowSearchResult = false;
                mRelatedList.clear();
                mSearchAdapter.notifyDataSetChanged();
                mSearchWindow.dismiss();
            } else {
                mPresenter.fetchSearch(s.toString());
                canShowSearchResult = true;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}
