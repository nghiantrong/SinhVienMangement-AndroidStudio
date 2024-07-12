package com.example.sinhvienmanagement.Models;

public class Major {
    private long idMajor;
    private String nameMajor;

    public long getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(long idMajor) {
        this.idMajor = idMajor;
    }

    public Major(String nameMajor) {
        this.nameMajor = nameMajor;
    }

    public String getNameMajor() {
        return nameMajor;
    }

    public void setNameMajor(String nameMajor) {
        this.nameMajor = nameMajor;
    }
}
