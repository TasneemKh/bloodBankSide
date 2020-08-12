package com.example.bloodbank;

import java.sql.Time;
import java.util.Date;

public class reservation {
    private String uid;
    private String date;
    private String time;
    private String name;
    private String hospital_id;
    private String patientFileNu;
    private String Type;


    public reservation(){}
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public reservation(String uid, String date, String time, String name, String type) {
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.name = name;
        Type = type;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getPatientFileNu() {
        return patientFileNu;
    }

    public void setPatientFileNu(String patientFileNu) {
        this.patientFileNu = patientFileNu;
    }
}
