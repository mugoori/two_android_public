package com.example.two.model;

import java.sql.Date;

public class Movie {

   private int id;
   private String title;
   private String genre;
   private String content;
   private String poster_path;
   private String vote_average;
   private String cratedYear;
   private String createdYear;
   private int tmdbcontentId;
   private String type;

    private String imgUrl;

    private String contentRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getCratedYear() {
        return cratedYear;
    }

    public void setCratedYear(String cratedYear) {
        this.cratedYear = cratedYear;
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

    public String getCreatedYear() {
        return createdYear;
    }

    public void setCreatedYear(String createdYear) {
        this.createdYear = createdYear;
    }
}
