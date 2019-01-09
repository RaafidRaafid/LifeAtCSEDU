package edu.universitydhaka.cse2216.lifeatcsedu;

public class Stuff {
    private String name,phoneNo,work,email,stuffKey;
    public Stuff(){

    }

    public Stuff(String name, String phoneNo, String work, String email, String stuffKey) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.work = work;
        this.email = email;
        this.stuffKey = stuffKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStuffKey() {
        return stuffKey;
    }

    public void setStuffKey(String stuffKey) {
        this.stuffKey = stuffKey;
    }
}
