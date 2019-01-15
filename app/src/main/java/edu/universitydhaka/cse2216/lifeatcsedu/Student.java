package edu.universitydhaka.cse2216.lifeatcsedu;

public class Student {
    private String name,batch,roll,phone,email,registrationNo;

    public Student(){

    }

    public Student(String name, String batch, String roll, String phone, String email, String registrationNo) {
        this.name = name;
        this.batch = batch;
        this.roll = roll;
        this.phone = phone;
        this.email = email;
        this.registrationNo = registrationNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }
}
