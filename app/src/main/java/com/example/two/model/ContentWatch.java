package com.example.two.model;

import java.util.List;

public class ContentWatch {

    private int userId;

    private String contentId;

    private String title;

    private String imgUrl;

    private String contentRating;

    private int tmdbcontentId;

    private String type;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public int getTmdbcontentId() {
        return tmdbcontentId;
    }

    public void setTmdbcontentId(int tmdbcontentId) {
        this.tmdbcontentId = tmdbcontentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
