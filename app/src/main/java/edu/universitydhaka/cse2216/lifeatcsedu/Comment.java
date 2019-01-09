package edu.universitydhaka.cse2216.lifeatcsedu;

public class Comment {

    String user, description;

    public Comment() {

    }

    public Comment(String user, String description) {
        this.user = user;
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
