package com.example.subside.main_activity_fragments.search;

public class ItemModel {
    private String profPictUri;
    private String name;
    private String major;
    private String faculty;
    private String cohort;
    private String sid;
    private String instagram;   // link to https://instagram.com/[instagram]
    private String email;
    private String phoneNum;
    private String linkedIn;    // link to https://linkedin.com/in/[kebab-case-linkedIn]
    private String funFact;
/*    private boolean hideSID = false;
    private boolean disableFeatured = false;
    private boolean hideAccount = false;
*/


    public ItemModel(String profPictUri, String name, String major, String faculty, String cohort, String sid, String instagram, String email, String phoneNum, String linkedIn, String funFact) {
        this.profPictUri = profPictUri;
        this.name = name;
        this.major = major;
        this.faculty = faculty;
        this.cohort = cohort;
        this.sid = sid;
        this.instagram = instagram;
        this.email = email;
        this.phoneNum = phoneNum;
        this.linkedIn = linkedIn;
        this.funFact = funFact;
        //this.hideSID = hideSID;
        //this.disableFeatured = disableFeatured;
        //this.hideAccount = hideAccount;
    }

    public String getProfPictUri() {
        return profPictUri;
    }

    public void setProfPictUri(String profPictUri) {
        this.profPictUri = profPictUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCohort() {
        return cohort;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getFunFact() {
        return funFact;
    }

    public void setFunFact(String funFact) {
        this.funFact = funFact;
    }

    /*public boolean isHideSID() {
        return hideSID;
    }

    public void setHideSID(boolean hideSID) {
        this.hideSID = hideSID;
    }

    public boolean isDisableFeatured() {
        return disableFeatured;
    }

    public void setDisableFeatured(boolean disableFeatured) {
        this.disableFeatured = disableFeatured;
    }

    public boolean isHideAccount() {
        return hideAccount;
    }

    public void setHideAccount(boolean hideAccount) {
        this.hideAccount = hideAccount;
    }*/
}
