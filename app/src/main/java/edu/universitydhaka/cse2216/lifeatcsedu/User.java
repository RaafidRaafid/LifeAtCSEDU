package edu.universitydhaka.cse2216.lifeatcsedu;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(batch);
        dest.writeString(roll);
        dest.writeString(phoneNumber);
        dest.writeString(name_batch);
        dest.writeString(name_batch_roll);
        dest.writeString(dpURL);
        dest.writeString(isModerator);
        dest.writeString(bio);
    }

    public User() {
    }

    public User(String email, String name, String batch, String roll, String phoneNumber, String name_batch, String name_batch_roll, String dpURL, String isModerator, String bio) {
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
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {

        return email;

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

    public User(Parcel in){
        this.email = in.readString();
        this.name = in.readString();
        this.batch = in.readString();
        this.roll = in.readString();
        this.phoneNumber = in.readString();

        this.name_batch = in.readString();
        this.name_batch_roll = in.readString();
        this.dpURL = in.readString();
        this.isModerator = in.readString();
        this.bio = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
}
