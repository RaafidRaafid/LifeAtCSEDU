package edu.universitydhaka.cse2216.lifeatcsedu;

public class Teacher {
    private String name,phone,email,designation,researchArea,teacherKey;

    public Teacher(){

    }

    public Teacher(String name, String phone, String email, String designation, String researchArea, String teacherKey) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.designation = designation;
        this.researchArea = researchArea;
        this.teacherKey = teacherKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public String getTeacherKey() {
        return teacherKey;
    }

    public void setTeacherKey(String teacherKey) {
        this.teacherKey = teacherKey;
    }
}
