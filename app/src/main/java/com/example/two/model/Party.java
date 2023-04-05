package com.example.two.model;

public class Party {
    private String pay;

    private String captain;

    private String partyBoardId;

    private String finishedAt;

    public String getPay ()
    {
        return pay;
    }

    public void setPay (String pay)
    {
        this.pay = pay;
    }

    public String getCaptain ()
    {
        return captain;
    }

    public void setCaptain (String captain)
    {
        this.captain = captain;
    }

    public String getPartyBoardId ()
    {
        return partyBoardId;
    }

    public void setPartyBoardId (String partyBoardId)
    {
        this.partyBoardId = partyBoardId;
    }

    public String getFinishedAt ()
    {
        return finishedAt;
    }

    public void setFinishedAt (String finishedAt)
    {
        this.finishedAt = finishedAt;
    }
}
