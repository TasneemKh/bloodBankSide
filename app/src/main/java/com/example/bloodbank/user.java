package com.example.bloodbank;


public class user {
    private String phoneNumber;
    private String weight;
    private String bloodType;
    private String identityNumber;
    private String gender;
    private int drugDurations;
    private String fullname,email,birthday;




    public user() {
    }

    public user(String phoneNumber, String weight, String bloodType, String identityNumber, String gender, int drugDurations) {
        this.phoneNumber = phoneNumber;
        this.weight = weight;
        this.bloodType = bloodType;
        this.identityNumber = identityNumber;
        this.gender = gender;
        this.drugDurations = drugDurations;
    }
    public user(String fullname,String email,String birthday,String phoneNumber, String weight, String bloodType, String gender) {
        this.fullname = fullname;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.weight = weight;
        this.bloodType = bloodType;
        this.gender = gender;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDrugDurations() {
        return drugDurations;
    }

    public void setDrugDurations(int drugDurations) {
        this.drugDurations = drugDurations;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}