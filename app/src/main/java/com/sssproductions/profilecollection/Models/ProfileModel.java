package com.sssproductions.profilecollection.Models;

import android.os.Parcelable;

import java.io.Serializable;

public class ProfileModel implements Serializable {

    private String name;
    private String occupation;
    private String gotra;
    private String birthPlace;
    private String city;
    private String number;
    private String img_url;
    private String age;
    private String dob;

    public ProfileModel() {}

    public ProfileModel(String name, String occupation, String gotra, String birthPlace, String city, String number, String img_url, String age, String dob) {
        this.name = name;
        this.occupation = occupation;
        this.gotra = gotra;
        this.birthPlace = birthPlace;
        this.city = city;
        this.number = number;
        this.img_url = img_url;
        this.age = age;
        this.dob = dob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getGotra() {
        return gotra;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
