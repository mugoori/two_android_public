package com.example.two.model;

import java.io.Serializable;

public class Chat implements Serializable {
   private int partyBoardId;

   private String service;

   private String title;

   private String createdAt;

   private int userId;

   private String serviceId;

   private String servicePassword;

   private String finishedAt;
   private String userEmail;
   private String profileImgUrl;
   private String nickname;
   private int memberCnt;


    public int getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(int memberCnt) {
        this.memberCnt = memberCnt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Chat(String service, String title, String serviceId, String servicePassword, String finishedAt) {
        this.service = service;
        this.title = title;
        this.serviceId = serviceId;
        this.servicePassword = servicePassword;
        this.finishedAt = finishedAt;
    }

    public int getPartyBoardId() {
        return partyBoardId;
    }

    public void setPartyBoardId(int partyBoardId) {
        this.partyBoardId = partyBoardId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServicePassword() {
        return servicePassword;
    }

    public void setServicePassword(String servicePassword) {
        this.servicePassword = servicePassword;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }
}
