package com.liguang.app.po.youtube;

/**
 * Created by zangliguang on 15/10/30.
 */
public class YoutubeVideoItem {
    private String videoId;
    private snippet snippet;
    private String thumbnails;

    @Override
    public String toString() {
        return "YoutubeVideoItem{" +
                "videoId='" + videoId + '\'' +
                ", snippet=" + snippet +
                ", thumbnails='" + thumbnails + '\'' +
                '}';
    }

    public YoutubeVideoItem(String videoId, com.liguang.app.po.youtube.snippet snippet, String thumbnails) {
        this.videoId = videoId;
        this.snippet = snippet;
        this.thumbnails = thumbnails;
    }
}
