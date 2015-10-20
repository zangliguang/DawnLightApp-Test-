package com.liguang.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DawnLight on 15/10/20.
 */
public class YoutubeVideoPageAdapter extends FragmentPagerAdapter {
    List<Fragment> mListFragment = new ArrayList<>();

    public YoutubeVideoPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public YoutubeVideoPageAdapter(FragmentManager fm, List<Fragment> ListFragment) {
        super(fm);
        this.mListFragment = ListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragment.get(position).getArguments().getString("param2");
    }
}
