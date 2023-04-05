package com.example.two.model;

public class PartyCheckRes {
    private String result;

    private String servicePassword;

    private String service;

    private String serviceId;

    private String memberCnt;

    private String[] memberEmail;

    private String finishedAt;


    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }

    public String getServicePassword ()
    {
        return servicePassword;
    }

    public void setServicePassword (String servicePassword)
    {
        this.servicePassword = servicePassword;
    }

    public String getService ()
    {
        return service;
    }

    public void setService (String service)
    {
        this.service = service;
    }

    public String getServiceId ()
    {
        return serviceId;
    }

    public void setServiceId (String serviceId)
    {
        this.serviceId = serviceId;
    }

    public String getMemberCnt ()
    {
        return memberCnt;
    }

    public void setMemberCnt (String memberCnt)
    {
        this.memberCnt = memberCnt;
    }

    public String[] getMemberEmail ()
    {
        return memberEmail;
    }

    public void setMemberEmail (String[] memberEmail)
    {
        this.memberEmail = memberEmail;
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
