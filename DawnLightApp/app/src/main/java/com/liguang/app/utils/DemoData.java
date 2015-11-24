package com.liguang.app.utils;

import android.content.Context;
import android.graphics.Color;

import com.google.gson.Gson;
import com.liguang.app.constants.LocalConstants;
import com.liguang.app.po.ColorItem;
import com.liguang.app.po.youtube.YoutubeVideoCategoryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
public class DemoData {

    private DemoData() {

    }

    public static List<ColorItem> loadDemoColorItems(Context context) {
        List<ColorItem> items = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, "colors.json"));
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject value = obj.getJSONObject(key);
                ColorItem colorItem = new ColorItem();
                colorItem.name = value.getString("name");
                colorItem.hex = value.getString("hex");
                JSONArray rgb = value.getJSONArray("rgb");
                colorItem.color = Color.rgb(rgb.getInt(0), rgb.getInt(1), rgb.getInt(2));
                items.add(colorItem);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static String loadJSONFromAsset(Context context, String filename) throws IOException {
        InputStream is = context.getAssets().open(filename);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

    public static List<Integer> loadImageResourceList() {
        return new ArrayList<>();
    }

    public static List<YoutubeVideoCategoryItem> loadDemoYoutubeVideoCategoryItems(Context context) {
        String localeLanguage = Utils.GetLanguage(context);
        List<YoutubeVideoCategoryItem> items = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, LocalConstants.Paths.LocalYoutubeVideoCategory));
            JSONArray ja = obj.getJSONArray(LocalConstants.Params.LocalYoutubeVideoCommonItems);
            Gson gson = new Gson();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject vcobj = (JSONObject) ja.get(i);
                YoutubeVideoCategoryItem yvci = gson.fromJson(vcobj.toString(), YoutubeVideoCategoryItem.class);
//                yvci.id = vcobj.getString("id");
//                yvci.channelId = vcobj.getJSONObject("snippet").getString("channelId");
//                yvci.title = vcobj.getJSONObject("snippet").getString("title");
                items.add(yvci);
            }
        } catch (IOException | JSONException e) {
            LogUtils.DebugerError("", e);
            e.printStackTrace();
        }
        return items;
    }
}
