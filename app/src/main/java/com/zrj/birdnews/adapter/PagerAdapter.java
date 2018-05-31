package com.zrj.birdnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zrj.birdnews.ui.fragment.NewsFragment;

import java.util.List;

/**
 * Created by a on 2018/3/9.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<NewsFragment> mFragments;

    public PagerAdapter(FragmentManager fm, List<NewsFragment> fragments){
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
