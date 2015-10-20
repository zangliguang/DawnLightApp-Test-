package com.liguang.app.po;

/**
 * Created by DawnLight on 15/10/19.
 */
public class YoutubeVideoCategoryItem {
    public String id;
    public snippet snippet;

    @Override
    public String toString() {
        return "YoutubeVideoCategoryItem{" +
                "id='" + id + '\'' +
                ", snippet=" + snippet +
                '}';
    }
}
