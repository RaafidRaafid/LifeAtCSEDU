package edu.universitydhaka.cse2216.lifeatcsedu;

public class User {
    private String password;
    private String email;
    private String name;
    private String batch;
    private String roll;

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getBatch() {
        return batch;
    }

    public String getRoll() {
        return roll;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public User(String password, String email, String name, String batch, String roll) {
        this.password = password;
        this.email = email;
        this.name = name;
        this.batch = batch;
        this.roll = roll;

    }


}
