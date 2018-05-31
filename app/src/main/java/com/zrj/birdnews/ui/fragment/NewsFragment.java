package com.zrj.birdnews.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gminibird.birdrecyclerview.adapter.RecyclerAdapter;
import com.gminibird.birdrecyclerview.item.IRecyclerItem;
import com.gminibird.birdrecyclerview.item.RecyclerItem;
import com.gminibird.birdrecyclerview.view.BirdRecyclerView;
import com.zrj.birdnews.R;
import com.zrj.birdnews.presenter.NewsPresenter;
import com.zrj.birdnews.ui.activity.NewsActivity;
import com.zrj.birdnews.ui.item.CategoryItem;
import com.zrj.birdnews.ui.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2018/3/11.
 */

public class NewsFragment extends MVPBaseFragment<List<RecyclerItem>> {

    private static final int UPDATE = NewsPresenter.FLAG_NEW_ITEMS;
    private static final int APPEND = NewsPresenter.FLAG_APPEND_ITEMS;

    private List<IRecyclerItem> mItemList = new ArrayList<>();

    private RecyclerAdapter mAdapter;
    private ProgressDialog mDialog;
    private BirdRecyclerView mNewsList;

    private NewsPresenter mPresenter;
    private Context mContext;
    private View mCategoryView;
    private int mLastCategoryPos = 0;


    @SuppressWarnings("unchecked")
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    mItemList.clear();
                    mItemList.addAll((List) msg.obj);
                    if (mNewsList != null) {
                        mNewsList.setDownRefreshing(false);
                        Toast.makeText(NewsFragment.this.getContext(), "更新成功", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case APPEND:
                    List<RecyclerItem> items = (List<RecyclerItem>) msg.obj;
                    if (items != null) {
                        mItemList.addAll(items);
                        mNewsList.setUpRefreshing(false);
                        Toast.makeText(NewsFragment.this.getContext(), "加载成功", Toast.LENGTH_SHORT).show();
                        mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), items.size());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (NewsPresenter) super.mPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        mNewsList = view.findViewById(R.id.recycler_view);
        mNewsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new RecyclerAdapter(getContext(), mItemList);
        mNewsList.setAdapter(mAdapter);
        mNewsList.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.news_item_space_gap)));
        mNewsList.addDownPullRefresh(
                new BirdRecyclerView.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.fetchItemList();
                    }
                });
        mNewsList.addUpPullRefresh(new BirdRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.appendItems();
            }
        });
        mNewsList.post(new Runnable() {
            @Override
            public void run() {
                mNewsList.setDownRefreshing(true);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addViewToCategoryWindow();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCategory();
    }

    private void initCategoryView(LayoutInflater inflater) {
        if (mCategoryView == null) {
            mCategoryView = inflater.inflate(R.layout.more_window_view, null, false);
            final RecyclerView recyclerView = mCategoryView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            final List<IRecyclerItem> items = mPresenter.createCategoryList();
            final RecyclerAdapter adapter = new RecyclerAdapter(mContext, items);
            ((CategoryItem) items.get(mLastCategoryPos)).isSelect = true;
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(mLastCategoryPos);
            recyclerView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) recyclerView.getLayoutParams();
            int visibleItems = adapter.getItemCount() > 6 ? 6 : adapter.getItemCount();
            params.height = visibleItems * getResources().getDimensionPixelSize(R.dimen.category_window_item_height);
            params.rightMargin = getResources().getDimensionPixelSize(R.dimen.category_window_margin_right);
            recyclerView.setLayoutParams(params);
            adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if (position == mLastCategoryPos) {
                        return;
                    }
                    CategoryItem lastSelectItem = (CategoryItem) items.get(mLastCategoryPos);
                    lastSelectItem.isSelect = false;
                    CategoryItem item = (CategoryItem) items.get(position);
                    item.isSelect = true;
                    mLastCategoryPos = position;
                    adapter.notifyDataSetChanged();
                    mPresenter.setCategory(item.getValue());
                    ((NewsActivity)mContext).dismissMoreWindow();
                    mNewsList.setDownRefreshing(true);
                }
            });
        }
    }



    private void saveCategory(){
        
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("position",mLastCategoryPos);
        editor.apply();
    }

    private void addViewToCategoryWindow() {
        mLastCategoryPos = PreferenceManager.getDefaultSharedPreferences(mContext).getInt("position",0);
        if (mCategoryView == null) {
            initCategoryView(LayoutInflater.from(mContext));
        }
        ((NewsActivity) mContext).setMoreWindowContentView(mCategoryView);
    }

    @Override
    public NewsPresenter createPresenter() {
        return new NewsPresenter();
    }

    @Override
    public void updateData(List<RecyclerItem> data, int... flags) {
        Message message = Message.obtain();
        message.what = flags[0];
        message.obj = data;
        mHandler.sendMessage(message);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

//    public DimFrameLayout getDimView() {
//        return mDimView;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
