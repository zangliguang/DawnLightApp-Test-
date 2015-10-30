package com.liguang.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liguang.app.R;
import com.liguang.app.po.youtube.YoutubeVideoCategoryItem;

import java.util.ArrayList;
import java.util.List;

public class DemoVideoCategoryPagerAdapter extends PagerAdapter {

    private List<YoutubeVideoCategoryItem> mYoutubeVideoCategoryItems = new ArrayList<>();

    public DemoVideoCategoryPagerAdapter() {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_page, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title);
        textView.setText("Page: " + mYoutubeVideoCategoryItems.get(position).snippet.getTitle());
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mYoutubeVideoCategoryItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return mYoutubeVideoCategoryItems.get(position).snippet.getTitle();
    }

    public void addAll(List<YoutubeVideoCategoryItem> items) {
        mYoutubeVideoCategoryItems = new ArrayList<>(items);
    }
}
