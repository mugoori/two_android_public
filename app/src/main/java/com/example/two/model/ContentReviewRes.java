package com.example.two.model;

import java.util.List;

public class ContentReviewRes {
    private String result;

    private List<ContentReview> contentReviewList;

    private String contentReviewSize;

    private String pageNum;

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }
    public List<ContentReview> getContentReviewList() {
        return contentReviewList;
    }

    public void setContentReviewList(List<ContentReview> contentReviewList) {
        this.contentReviewList = contentReviewList;
    }

    public String getContentReviewSize ()
    {
        return contentReviewSize;
    }

    public void setContentReviewSize (String contentReviewSize)
    {
        this.contentReviewSize = contentReviewSize;
    }

    public String getPageNum ()
    {
        return pageNum;
    }

    public void setPageNum (String pageNum)
    {
        this.pageNum = pageNum;
    }
}
