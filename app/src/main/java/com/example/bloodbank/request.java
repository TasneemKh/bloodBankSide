package com.example.bloodbank;

public class request {
    private String donType,UnitsType;
    private int NoOfUnits;
    private int status;
    private String date;



    private String idKey;

    public request(String donType, String unitsType, int noOfUnits, int status, String date,String idKey) {
        this.donType = donType;
        UnitsType = unitsType;
        NoOfUnits = noOfUnits;
        this.status = status;
        this.date = date;
        this.idKey=idKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDonType() {
        return donType;
    }

    public void setDonType(String donType) {
        this.donType = donType;
    }

    public String getUnitsType() {
        return UnitsType;
    }

    public void setUnitsType(String unitsType) {
        UnitsType = unitsType;
    }

    public int getNoOfUnits() {
        return NoOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        NoOfUnits = noOfUnits;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

}
