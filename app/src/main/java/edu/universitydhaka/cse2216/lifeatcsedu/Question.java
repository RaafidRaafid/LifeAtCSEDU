package edu.universitydhaka.cse2216.lifeatcsedu;

public class Question {

    String title,asker,time,desc;

    public Question() {

    }

    public Question(String title, String asker, String time,String desc) {
        this.title = title;
        this.asker = asker;
        this.time = time;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getAsker() {
        return asker;
    }

    public String getTime() {
        return time;
    }

    public String getDesc() {
        return desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
