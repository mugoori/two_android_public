package com.example.two.model;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.http.Body;

public class SeachData {

   private String keyword;
   private String genre;
   private String limit;
   private String rating;
   private String year;
   private String offset;
   private String filtering;
   private String sort;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getFiltering() {
        return filtering;
    }

    public void setFiltering(String filtering) {
        this.filtering = filtering;
    }

    public String getSoft() {
        return sort;
    }

    public void setSoft(String sort) {
        this.sort = sort;
    }

    public SeachData(String keyword, String genre, String limit, String rating, String year, String offset, String filtering, String sort) {
        this.keyword = keyword;
        this.genre = genre;
        this.limit = limit;
        this.rating = rating;
        this.year = year;
        this.offset = offset;
        this.filtering = filtering;
        this.sort = sort;
    }


}
