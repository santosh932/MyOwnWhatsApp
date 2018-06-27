package com.santosh.myownwhatsapp.model;

import java.util.Date;

public class ChatMsg {

    private String msg;
    private String user;
    private Date dtm;

    public ChatMsg(String msg, String user, Date dtm) {
        this.msg = msg;
        this.user = user;
        this.dtm = dtm;
    }

    public ChatMsg() {
    }

    public String getMsg() {
        return msg;
    }

    public String getUser() {
        return user;
    }

    public Date getDtm() {
        return dtm;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDtm(Date dtm) {
        this.dtm = dtm;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "msg='" + msg + '\'' +
                ", user='" + user + '\'' +
                ", dtm=" + dtm +
                '}';
    }
}
