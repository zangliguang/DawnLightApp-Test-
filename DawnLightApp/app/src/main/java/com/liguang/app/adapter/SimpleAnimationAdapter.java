package com.liguang.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.liguang.app.R;
import com.liguang.app.po.youtube.YoutubeVideoItem;
import com.marshalchen.ultimaterecyclerview.URLogs;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class SimpleAnimationAdapter extends UltimateViewAdapter<SimpleAnimationAdapter.ViewHolder> {
    private List<YoutubeVideoItem> youtubeData;

    public SimpleAnimationAdapter(List<YoutubeVideoItem> youtubeData) {
        this.youtubeData = youtubeData;
    }

    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mLastPosition = 5;

    private boolean isFirstOnly = true;


    @Override
    public int getAdapterItemCount() {
        return youtubeData.size();
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    public void insert(YoutubeVideoItem item, int position) {
        insert(youtubeData, item, position);
    }

    public void remove(int position) {
        remove(youtubeData, position);
    }

    public void clear() {
        clear(youtubeData);
    }

    @Override
    public void toggleSelection(int pos) {
        super.toggleSelection(pos);
    }

    @Override
    public void setSelected(int pos) {
        super.setSelected(pos);
    }

    @Override
    public void clearSelection(int pos) {
        super.clearSelection(pos);
    }


    public void swapPositions(int from, int to) {
        swapPositions(youtubeData, from, to);
    }


    @Override
    public long generateHeaderId(int position) {
        URLogs.d("position--" + position + "   " + getItem(position));
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position>=youtubeData.size()){
            return;
        }
        ((ViewHolder) holder).textViewSample.setText(youtubeData.get(position).getSnippet().getTitle());
        ImageLoader.getInstance().displayImage(youtubeData.get(position).getThumbnails(), ((ViewHolder) holder).imageViewSample);
    }
//
//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }


    public class ViewHolder extends UltimateRecyclerviewViewHolder {

        TextView textViewSample;
        ImageView imageViewSample;

        public ViewHolder(View itemView) {
            super(itemView);
//            itemView.setOnTouchListener(new SwipeDismissTouchListener(itemView, null, new SwipeDismissTouchListener.DismissCallbacks() {
//                @Override
//                public boolean canDismiss(Object token) {
//                    Logs.d("can dismiss");
//                    return true;
//                }
//
//                @Override
//                public void onDismiss(View view, Object token) {
//                   // Logs.d("dismiss");
//                    remove(getPosition());
//
//                }
//            }));
            textViewSample = (TextView) itemView.findViewById(
                    R.id.textview);
            imageViewSample = (ImageView) itemView.findViewById(R.id.imageview);

        }
    }

    public YoutubeVideoItem getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < youtubeData.size())
            return youtubeData.get(position);
        else return null;
    }

}
