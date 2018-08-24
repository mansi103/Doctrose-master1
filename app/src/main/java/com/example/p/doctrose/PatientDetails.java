package com.example.p.doctrose;

/**
 * Created by P on 06-11-2017.
 */

public class PatientDetails {
    private String name;
    private String address;
    private String doctor;
    private String email;
    public PatientDetails(String name, String address,String doctor,String email) {
        this.name = name;
        this.address = address;
        this.doctor = doctor;
        this.email=email;
    }

    public String getName()  {
        return name;
    }

    public String getAddress()  {
         return address;
    }

    public String getDoctor()  {
        return doctor;
    }

    public String getEmail() {
        return email;
    }
}
