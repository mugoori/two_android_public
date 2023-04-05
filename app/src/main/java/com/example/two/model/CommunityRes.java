package com.example.two.model;

import java.util.List;

public class CommunityRes {
    private String result;

    private List<Community> communityList;

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }

    public List<Community> getCommunityList() {
        return communityList;
    }

    public void setCommunityList(List<Community> communityList) {
        this.communityList = communityList;
    }
}
