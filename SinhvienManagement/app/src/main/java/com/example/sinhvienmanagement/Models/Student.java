package com.example.sinhvienmanagement.Models;

public class Student {
    private long id;
    private String name;
    private String date;
    private String gender;
    private String email;
    private String address;
    private long idMajor;

    public Student(String name, String date, String gender, String email, String address, long idMajor) {
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.idMajor = idMajor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(long idMajor) {
        this.idMajor = idMajor;
    }
}
