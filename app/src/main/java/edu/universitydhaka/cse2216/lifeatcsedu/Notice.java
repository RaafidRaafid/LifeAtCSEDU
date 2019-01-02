package edu.universitydhaka.cse2216.lifeatcsedu;

import android.os.Parcel;
import android.os.Parcelable;

public class Notice{
    private String title,description,time,noticeKey;
    public Notice(){

    }

    public Notice(String title, String description, String time, String noticeKey) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.noticeKey=noticeKey;
    }

    public int describeContents() {
        return 0;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoticeKey() {
        return noticeKey;
    }

    public void setNoticeKey(String noticeKey) {
        this.noticeKey = noticeKey;
    }



}
