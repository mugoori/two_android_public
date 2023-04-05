package com.example.two.model;

import java.io.Serializable;

public class ContentReview implements Serializable {
    private String createdAt;

    private String likeCnt;

    private String contentReviewUserId;

    private String profileImgUrl;

    private String contentReviewId;

    private String contentId;

    private String nickname;

    private String userEmail;

    private String title;

    private String content;

    private String userRating;

    private String updatedAt;

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getLikeCnt ()
    {
        return likeCnt;
    }

    public void setLikeCnt (String likeCnt)
    {
        this.likeCnt = likeCnt;
    }

    public String getContentReviewUserId ()
    {
        return contentReviewUserId;
    }

    public void setContentReviewUserId (String contentReviewUserId)
    {
        this.contentReviewUserId = contentReviewUserId;
    }

    public String getProfileImgUrl ()
    {
        return profileImgUrl;
    }

    public void setProfileImgUrl (String profileImgUrl)
    {
        this.profileImgUrl = profileImgUrl;
    }

    public String getContentReviewId ()
    {
        return contentReviewId;
    }

    public void setContentReviewId (String contentReviewId)
    {
        this.contentReviewId = contentReviewId;
    }

    public String getContentId ()
    {
        return contentId;
    }

    public void setContentId (String contentId)
    {
        this.contentId = contentId;
    }

    public String getNickname ()
    {
        return nickname;
    }

    public void setNickname (String nickname)
    {
        this.nickname = nickname;
    }

    public String getUserEmail ()
    {
        return userEmail;
    }

    public void setUserEmail (String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getUserRating ()
    {
        return userRating;
    }

    public void setUserRating (String userRating)
    {
        this.userRating = userRating;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
