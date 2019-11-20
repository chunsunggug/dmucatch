package com.example.dmu.dmucatch.notice;

import java.io.Serializable;
import java.util.Date;

public class NoticeInfo implements Serializable {
   int noti_seq[];
   String cate[];
   String user_id[];
   String title[];
   String content[];
   int readnum[];
   Date writedate[];

    public NoticeInfo() {
        super();
    }

    public NoticeInfo(int size) {
        this.noti_seq = new int[size];
        this.cate = new String[size];
        this.user_id = new String[size];
        this.title = new String[size];
        this.content = new String[size];
        this.readnum = new int[size];
        this.writedate = new Date[size];
    }

    public int getNoti_seq(int idx) {
        return noti_seq[idx];
    }

    public void setNoti_seq(int noti_seq,int idx) {
        this.noti_seq[idx] = noti_seq;
    }

    public String getCate(int idx) {
        return cate[idx];
    }

    public void setCate(String cate,int idx) {
        this.cate[idx] = cate;
    }

    public String getUser_id(int idx) {
        return user_id[idx];
    }

    public void setUser_id(String user_id,int idx) {
        this.user_id[idx] = user_id;
    }

    public String getTitle(int idx) {
        return title[idx];
    }

    public void setTitle(String title,int idx) {
        this.title[idx] = title;
    }

    public String getContent(int idx) {
        return content[idx];
    }

    public void setContent(String content,int idx) {
        this.content[idx] = content;
    }

    public int getReadnum(int idx) {
        return readnum[idx];
    }

    public void setReadnum(int readnum,int idx) {
        this.readnum[idx] = readnum;
    }

    public Date getWritedate(int idx) {
        return writedate[idx];
    }

    public void setWritedate(Date writedate,int idx) {
        this.writedate[idx] = writedate;
    }
}
