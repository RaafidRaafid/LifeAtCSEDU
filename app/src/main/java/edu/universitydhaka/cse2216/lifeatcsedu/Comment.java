package edu.universitydhaka.cse2216.lifeatcsedu;

public class Comment {

    String user, description,name;

    public Comment() {

    }

    public Comment(String user, String name, String description) {
        this.user = user;
        this.name = name;
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
