package com.example.two.model;

import java.util.List;

public class PartyListRes {
    String result;
    List<Chat> partylist;
    String pageNum;

    public PartyListRes() {

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Chat> getPartyList() {
        return partylist;
    }

    public void setPartyList(List<Chat> partylist) {
        this.partylist = partylist;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
}
