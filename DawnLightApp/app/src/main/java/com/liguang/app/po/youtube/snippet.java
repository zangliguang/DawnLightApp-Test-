package com.liguang.app.po.youtube;

/**
 * Created by DawnLight on 15/10/19.
 */
public class snippet {
    private String channelId;
    private String title;
    private String description;
    private String publishedAt;
    private String channelTitle;


    @Override
    public String toString() {
        return "snippet{" +
                "channelId='" + channelId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", channelTitle='" + channelTitle + '\'' +
                '}';
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
