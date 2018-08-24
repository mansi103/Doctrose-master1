package com.example.p.doctrose;

/**
 * Created by P on 04-11-2017.
 */

public class DoctorInfo {
    private String name;
    private String address;
    private String qualification;
    private String speciality;
    private String rate;

    public DoctorInfo(String name, String address, String qualification, String speciality,String rate) {
        this.name = name;
        this.address = address;
        this.qualification= qualification;
        this.speciality = speciality;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getQualification() {
        return qualification;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getRate() {
        return rate;
    }
}
