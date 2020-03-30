package com.example.studentinformationsystem.StudentPort;

public class StudentProfile {

    public String stuName, stuRegisterNumber, stuStandard, stuSection, stuGender, stuParentName, stuMobileNumber, stuEmail, id, username, imageURL, status;

    public StudentProfile() {
    }

    public StudentProfile(String stuName, String stuRegisterNumber, String stuStandard, String stuSection, String stuGender, String stuParentName, String stuMobileNumber, String stuEmail, String id, String username, String imageURL, String status) {
        this.stuName = stuName;
        this.stuRegisterNumber = stuRegisterNumber;
        this.stuStandard = stuStandard;
        this.stuSection = stuSection;
        this.stuGender = stuGender;
        this.stuParentName = stuParentName;
        this.stuMobileNumber = stuMobileNumber;
        this.stuEmail = stuEmail;
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.status = status;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuRegisterNumber() {
        return stuRegisterNumber;
    }

    public void setStuRegisterNumber(String stuRegisterNumber) {
        this.stuRegisterNumber = stuRegisterNumber;
    }

    public String getStuStandard() {
        return stuStandard;
    }

    public void setStuStandard(String stuStandard) {
        this.stuStandard = stuStandard;
    }

    public String getStuSection() {
        return stuSection;
    }

    public void setStuSection(String stuSection) {
        this.stuSection = stuSection;
    }

    public String getStuGender() {
        return stuGender;
    }

    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }

    public String getStuParentName() {
        return stuParentName;
    }

    public void setStuParentName(String stuParentName) {
        this.stuParentName = stuParentName;
    }

    public String getStuMobileNumber() {
        return stuMobileNumber;
    }

    public void setStuMobileNumber(String stuMobileNumber) {
        this.stuMobileNumber = stuMobileNumber;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String dis() {

        return this.stuName + "  (" + stuRegisterNumber + ")";
    }
}
