package com.example.studentinformationsystem.StaffPort;

public class StaffProfile {

    public String teName;
    public String teStaffId;
    public String teSubject;
    public String teGender;
    public String teMnum;
    public String teEmail;

    public StaffProfile() {
    }

    public StaffProfile(String teName, String teStaffId, String teSubject, String teGender, String teMnum, String teEmail) {
        this.teName = teName;
        this.teStaffId = teStaffId;
        this.teSubject = teSubject;
        this.teGender = teGender;
        this.teMnum = teMnum;
        this.teEmail = teEmail;
    }

    public String getTeName() {
        return teName;
    }

    public void setTeName(String teName) {
        this.teName = teName;
    }

    public String getTeStaffId() {
        return teStaffId;
    }

    public void setTeStaffId(String teStaffId) {
        this.teStaffId = teStaffId;
    }

    public String getTeSubject() {
        return teSubject;
    }

    public void setTeSubject(String teSubject) {
        this.teSubject = teSubject;
    }

    public String getTeGender() {
        return teGender;
    }

    public void setTeGender(String teGender) {
        this.teGender = teGender;
    }

    public String getTeMnum() {
        return teMnum;
    }

    public void setTeMnum(String teMnum) {
        this.teMnum = teMnum;
    }

    public String getTeEmail() {
        return teEmail;
    }

    public void setTeEmail(String teEmail) {
        this.teEmail = teEmail;
    }
}
