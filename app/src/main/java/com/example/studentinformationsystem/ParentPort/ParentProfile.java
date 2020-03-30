package com.example.studentinformationsystem.ParentPort;

public class ParentProfile {

    public String paName;
    public String paStudentName;
    public String paStudentRegisterNumber;
    public String paGender;
    public String paMnum;
    public String paEmail;

    public ParentProfile() {
    }

    public ParentProfile(String paName, String paStudentName, String paStudentRegisterNumber, String paGender, String paMnum, String paEmail) {
        this.paName = paName;
        this.paStudentName = paStudentName;
        this.paStudentRegisterNumber = paStudentRegisterNumber;
        this.paGender = paGender;
        this.paMnum = paMnum;
        this.paEmail = paEmail;
    }

    public String getPaName() {
        return paName;
    }

    public void setPaName(String paName) {
        this.paName = paName;
    }

    public String getPaStudentName() {
        return paStudentName;
    }

    public void setPaStudentName(String paStudentName) {
        this.paStudentName = paStudentName;
    }

    public String getPaStudentRegisterNumber() {
        return paStudentRegisterNumber;
    }

    public void setPaStudentRegisterNumber(String paStudentRegisterNumber) {
        this.paStudentRegisterNumber = paStudentRegisterNumber;
    }

    public String getPaGender() {
        return paGender;
    }

    public void setPaGender(String paGender) {
        this.paGender = paGender;
    }

    public String getPaMnum() {
        return paMnum;
    }

    public void setPaMnum(String paMnum) {
        this.paMnum = paMnum;
    }

    public String getPaEmail() {
        return paEmail;
    }

    public void setPaEmail(String paEmail) {
        this.paEmail = paEmail;
    }

}
