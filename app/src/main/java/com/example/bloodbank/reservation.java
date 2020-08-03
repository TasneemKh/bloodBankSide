package com.example.bloodbank;

import java.sql.Time;
import java.util.Date;

public class reservation {
    private String uid;
    private Date date;
    private Time time;
    private int hospital_id;
    private String patientFileNu;
    private String Type;


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getPatientFileNu() {
        return patientFileNu;
    }

    public void setPatientFileNu(String patientFileNu) {
        this.patientFileNu = patientFileNu;
    }
}
