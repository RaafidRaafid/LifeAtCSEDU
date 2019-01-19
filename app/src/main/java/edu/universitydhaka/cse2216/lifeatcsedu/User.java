package edu.universitydhaka.cse2216.lifeatcsedu;

import android.os.Parcel;
import android.os.Parcelable;

public class User {
    private String email;
    private String name;
    private String batch;
    private String roll;
    private String phoneNumber;
    private String name_batch;
    private String name_batch_roll;
    private String dpURL;
    private String isModerator;
    private String bio;
    private String token;


    public User() {
    }

    public User(String email, String name, String batch, String roll, String phoneNumber, String name_batch, String name_batch_roll, String dpURL, String isModerator, String bio,String token) {
        this.email = email;
        this.name = name;
        this.batch = batch;
        this.roll = roll;
        this.phoneNumber = phoneNumber;
        this.name_batch = name_batch;
        this.name_batch_roll = name_batch_roll;
        this.dpURL = dpURL;
        this.isModerator = isModerator;
        this.bio = bio;
        this.token = token;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {

        return email;

    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public String getBio() {
        return bio;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName_batch() {
        return name_batch;
    }

    public String getName_batch_roll() {
        return name_batch_roll;
    }

    public String getDpURL() {
        return dpURL;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName_batch(String name_batch) {
        this.name_batch = name_batch;
    }

    public void setName_batch_roll(String name_batch_roll) {
        this.name_batch_roll = name_batch_roll;
    }

    public void setDpURL(String dpURL) {
        this.dpURL = dpURL;
    }

    public void setIsModerator(String isModerator) {
        this.isModerator = isModerator;
    }

    public String getIsModerator() {
        return isModerator;
    }
}
