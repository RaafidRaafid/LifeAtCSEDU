package edu.universitydhaka.cse2216.lifeatcsedu;

public class Resource {

    String title;
    String link;
    String courseCode;

    public Resource(String title, String link, String courseCode) {
        this.title = title;
        this.link = link;
        this.courseCode = courseCode;
    }

    public Resource() {

    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {

        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

