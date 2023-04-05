package com.example.two.model;

public class Choice {

    private int contentId;

    private int contentLikeUserId;

    private String title;

    private String genre;

    private String content;

    private String imgUrl;

    private float contentRating;

    private String createdYear;

    private int tmdbcontentId;

    public Choice() {
    }

    public Choice(int contentId, int contentLikeUserId, String title, String genre, String content, String imgUrl, float contentRating, String createdYear, int tmdbcontentId) {
        this.contentId = contentId;
        this.contentLikeUserId = contentLikeUserId;
        this.title = title;
        this.genre = genre;
        this.content = content;
        this.imgUrl = imgUrl;
        this.contentRating = contentRating;
        this.createdYear = createdYear;
        this.tmdbcontentId = tmdbcontentId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentLikeUserId() {
        return contentLikeUserId;
    }

    public void setContentLikeUserId(int contentLikeUserId) {
        this.contentLikeUserId = contentLikeUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getContentRating() {
        return contentRating;
    }

    public void setContentRating(float contentRating) {
        this.contentRating = contentRating;
    }

    public String getCreatedYear() {
        return createdYear;
    }

    public void setCreatedYear(String createdYear) {
        this.createdYear = createdYear;
    }

    public int getTmdbcontentId() {
        return tmdbcontentId;
    }

    public void setTmdbcontentId(int tmdbcontentId) {
        this.tmdbcontentId = tmdbcontentId;
    }
}
