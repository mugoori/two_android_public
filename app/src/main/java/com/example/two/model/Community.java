package com.example.two.model;

import java.io.Serializable;

public class Community implements Serializable {
    private String imgUrl;

    private String createdAt;

    private String profileImgUrl;

    private String nickname;

    private String userEmail;

    private String communityId;

    private String title;

    private String userId;

    private String content;

    private String updatedAt;

    public String getImgUrl ()
    {
        return imgUrl;
    }

    public void setImgUrl (String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getProfileImgUrl ()
    {
        return profileImgUrl;
    }

    public void setProfileImgUrl (String profileImgUrl)
    {
        this.profileImgUrl = profileImgUrl;
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

    public String getCommunityId ()
    {
        return communityId;
    }

    public void setCommunityId (String communityId)
    {
        this.communityId = communityId;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
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
