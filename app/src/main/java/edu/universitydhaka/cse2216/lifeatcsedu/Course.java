package edu.universitydhaka.cse2216.lifeatcsedu;

public class Course {
    String title;
    String code;

    public Course() {
    }

    public Course(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
