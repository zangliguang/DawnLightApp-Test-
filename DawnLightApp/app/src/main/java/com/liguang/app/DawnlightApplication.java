package com.liguang.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by zangliguang on 15/10/19.
 */
public class DawnlightApplication extends Application {

    private static DawnlightApplication mAppApplication;

    //private SQLHelper sqlHelper;
    //private DatabaseHelper databaseHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppApplication = this;
        initImageLoader(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static DawnlightApplication getmAppApplication() {
        return mAppApplication;
    }

    public static void setmAppApplication(DawnlightApplication mAppApplication) {
        DawnlightApplication.mAppApplication = mAppApplication;
    }

    /** 初始化ImageLoader */
    public static void initImageLoader(Context context) {
        String filePath = Environment.getExternalStorageDirectory() +
                "/Android/data/" + context.getPackageName() + "/cache/";

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, filePath);// 获取到缓存的目录地址
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)  //降低线程的优先级保证主UI线程不受太大影响
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024)) //建议内存设在5-10M,可以有比较好的表现
                .memoryCacheSize(5 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }
}
