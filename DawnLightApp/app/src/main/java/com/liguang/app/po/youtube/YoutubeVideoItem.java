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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public com.liguang.app.po.youtube.snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(com.liguang.app.po.youtube.snippet snippet) {
        this.snippet = snippet;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
}
