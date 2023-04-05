package com.example.two.model;

import java.util.List;

public class ChatRoomList {

    private List<Chat> partyBoard;

    String pageNum;
    String partyBoardSize;

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPartyBoardSize() {
        return partyBoardSize;
    }

    public void setPartyBoardSize(String partyBoardSize) {
        this.partyBoardSize = partyBoardSize;
    }

    public List<Chat> getPartyBoard() {
        return partyBoard;
    }

    public void setPartyBoard(List<Chat> partyBoard) {
        this.partyBoard = partyBoard;
    }
}
