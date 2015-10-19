package com.liguang.app.constants;

import android.os.Environment;

/**
 * Created by baidu on 15/10/19.
 */
public class LocalConstants {
    public static  boolean DEBUGE =true;
    public static  String appname ="DawnLightApp";
    public static final class Paths {
        public static final String LocalYoutubeVideoCategory = "VideoCategories.json";
        public static final String LocalYoutubeVideoCategoryItems = "items";
        public static final String LocalYoutubeVideoCategoryItems_ZH = "items_zh";
        public static final String LocalYoutubeVideoCategoryItems_EN = "items_en";
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/DawnLightApp/Images/";
    }
}
