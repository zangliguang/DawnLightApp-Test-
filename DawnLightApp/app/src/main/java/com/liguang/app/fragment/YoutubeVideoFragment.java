package com.liguang.app.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.liguang.app.R;
import com.liguang.app.adapter.SimpleAnimationAdapter;
import com.liguang.app.constants.LocalConstants;
import com.liguang.app.constants.WebConstant;
import com.liguang.app.http.Url;
import com.liguang.app.po.youtube.YoutubeVideoItem;
import com.liguang.app.po.youtube.snippet;
import com.liguang.app.utils.LogUtils;
import com.liguang.app.utils.OkHttpUtil;
import com.liguang.app.utils.Utils;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YoutubeVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YoutubeVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YoutubeVideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mCatgoryId;
    private String mParam2;
    private String nextPageToken;

    private OnFragmentInteractionListener mListener;

    CustomUltimateRecyclerview ultimateRecyclerView;
    SimpleAnimationAdapter simpleRecyclerViewAdapter = null;
    View floatingButton = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    int ADAPTEROTIFYDATA = 4;
    protected Context mContext = null;
    private int mLoadTime = 0;
    StoreHouseHeader storeHouseHeader;
    MaterialHeader materialHeader;
    List<YoutubeVideoItem> YoutubeVideoList = new ArrayList<YoutubeVideoItem>();
    Handler changeHeaderHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshingStringArray();
                    break;
                case 1:
                    //  refreshingMaterial();
                    refreshingString();
                    break;
                case 2:
                    // refreshingString();
                    break;
                case 3:
                    refreshingString();
                    break;
                case 4:
//                    for(YoutubeVideoItem youtubeItem:YoutubeVideoList){
//                        simpleRecyclerViewAdapter.insert(youtubeItem,simpleRecyclerViewAdapter.getAdapterItemCount());
//                    }
                    simpleRecyclerViewAdapter.add(YoutubeVideoList);
                    break;
            }
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YoutubeVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YoutubeVideoFragment newInstance(String param1, String param2) {
        YoutubeVideoFragment fragment = new YoutubeVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public YoutubeVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.DebugerTest("YoutubeVideoFragment-onCreate");
        if (getArguments() != null) {
            mCatgoryId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.DebugerTest("YoutubeVideoFragment-onCreateView");
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_youtube_video, container, false);
        initView(rootview);
        if (TextUtils.isEmpty(nextPageToken)) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    loadData(Url.youtubeGetCategoriyVideoslist, mCatgoryId, nextPageToken);
                }
            };
            new Thread(runnable).start();
        }
        return rootview;
    }

    private void loadData(String url, String catgoryId, String pageToken) {
        if (Utils.isMobileNetworkAvailable(getActivity())) {
            try {
                String result = OkHttpUtil.getStringFromServer(OkHttpUtil.attachHttpGetParams(url, new BasicNameValuePair(WebConstant.YoutubeParams.part, WebConstant.YoutubeParams.snippet),
                        new BasicNameValuePair(WebConstant.YoutubeParams.order, WebConstant.YoutubeParams.date),
                        new BasicNameValuePair(WebConstant.YoutubeParams.type, WebConstant.YoutubeParams.video),
                        //new BasicNameValuePair(WebConstant.YoutubeParams.regionCode, WebConstant.YoutubeParams.US),
                        new BasicNameValuePair(WebConstant.YoutubeParams.videoCategoryId, catgoryId),
                        new BasicNameValuePair(WebConstant.YoutubeParams.maxResults, "50"),
                        new BasicNameValuePair(WebConstant.YoutubeParams.pageToken, pageToken),
                        new BasicNameValuePair(WebConstant.YoutubeParams.key, WebConstant.YoutubeParams.keyvalue)));
                JSONObject obj = new JSONObject(result);
                nextPageToken = obj.getString(LocalConstants.Params.LocalYoutubeVideoNextPageToken);
                JSONArray ja = obj.getJSONArray(LocalConstants.Params.LocalYoutubeVideoCommonItems);
                Gson gson = new Gson();
                List<YoutubeVideoItem> list = new ArrayList<>();
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject vcobj = (JSONObject) ja.get(i);
                    JSONObject snipeetobjec = vcobj.getJSONObject(LocalConstants.Params.LocalYoutubeVideoCommonSnippet);
                    String snippetStr = snipeetobjec.toString();
                    snippet Snippet = gson.fromJson(snippetStr, snippet.class);
                    YoutubeVideoItem yvi = new YoutubeVideoItem(vcobj.getJSONObject("id").getString("videoId"), Snippet, snipeetobjec.getJSONObject("thumbnails").getJSONObject("high").getString("url"));
                    list.add(yvi);
                }
                YoutubeVideoList = list;
//                if (TextUtils.isEmpty(pageToken)) {
//
//                } else {
//                }
                changeHeaderHandler.sendEmptyMessage(ADAPTEROTIFYDATA);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView(View rootview) {
        ultimateRecyclerView = (CustomUltimateRecyclerview) rootview.findViewById(R.id.custom_ultimate_recycler_view);
        ultimateRecyclerView.setHasFixedSize(false);
        floatingButton = rootview.findViewById(R.id.custom_urv_add_floating_button);
        simpleRecyclerViewAdapter = new SimpleAnimationAdapter(new ArrayList<YoutubeVideoItem>());

        linearLayoutManager = new LinearLayoutManager(mContext);
        ultimateRecyclerView.setLayoutManager(linearLayoutManager);
        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
        ultimateRecyclerView.enableLoadmore();
        ultimateRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case 0:
                        ImageLoader.getInstance().resume();
                        break;

                    case 1:
                        ImageLoader.getInstance().pause();
                        break;

                    case 2:
                        ImageLoader.getInstance().pause();
                        break;
                }
            }
        });
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(mContext)
                .inflate(R.layout.custom_bottom_progressbar, null));

        //ultimateRecyclerView.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {

//                Drawable c =getActivity().getActionBar().getCustomView().getBackground();
//                c.setAlpha(Math.round(127 + percentage * 128));
//                getActivity().getActionBar().getCustomView().setBackgroundDrawable(c);
            }
        });

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        loadData(Url.youtubeGetCategoriyVideoslist, mCatgoryId, nextPageToken);
                    }
                };
                new Thread(runnable).start();
            }
        });
        ultimateRecyclerView.hideDefaultFloatingActionButton();
        ultimateRecyclerView.hideFloatingActionMenu();
        ultimateRecyclerView.displayCustomFloatingActionView(false);
        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                if (observableScrollState == ObservableScrollState.DOWN) {
                    //  ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                    //  ultimateRecyclerView.showView(floatingButton, ultimateRecyclerView, getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.UP) {
//                    ultimateRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
//                    ultimateRecyclerView.hideFloatingActionMenu();
                    //   ultimateRecyclerView.hideView(floatingButton, ultimateRecyclerView, getScreenHeight());

                } else if (observableScrollState == ObservableScrollState.STOP) {
                }
            }
        });
        ultimateRecyclerView.setCustomSwipeToRefresh();

        // refreshingMaterial();
        refreshingString();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mContext = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(mContext);
        //   header.setPadding(0, 15, 0, 0);

        storeHouseHeader.initWithString("Marshal Chen");
        //  header.initWithStringArray(R.array.akta);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                boolean canbePullDown = PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, view, view2);
                return canbePullDown;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
//                ptrFrameLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
//                        //   ultimateRecyclerView.scrollBy(0, -50);
//                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
//                        changeHeaderHandler.sendEmptyMessageDelayed(0, 500);
//                    }
//                }, 1800);
            }
        });
    }

    void refreshingStringArray() {
        storeHouseHeader = new StoreHouseHeader(mContext);
        storeHouseHeader.setPadding(0, 15, 0, 0);

        // using string array from resource xml file
        storeHouseHeader.initWithStringArray(R.array.storehouse);

        ultimateRecyclerView.mPtrFrameLayout.setDurationToCloseHeader(1500);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);


        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
//        ultimateRecyclerView.mPtrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
//            }
//        }, 100);

        // change header after loaded
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(new PtrUIHandler() {


            @Override
            public void onUIReset(PtrFrameLayout frame) {
                mLoadTime++;
                if (mLoadTime % 2 == 0) {
                    storeHouseHeader.setScale(1);
                    storeHouseHeader.initWithStringArray(R.array.storehouse);
                } else {
                    storeHouseHeader.setScale(0.5f);
                    storeHouseHeader.initWithStringArray(R.array.akta);
                }
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            }
        });

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        loadData(Url.youtubeGetCategoriyVideoslist, mCatgoryId, nextPageToken);
                    }
                };
                new Thread(runnable).start();
            }
        });
    }

}
