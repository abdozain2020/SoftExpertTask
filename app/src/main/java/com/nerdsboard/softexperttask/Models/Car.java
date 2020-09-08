package com.nerdsboard.softexperttask.Models;

public class Car {

    private String brand = "";
    private String imageUrl = "";
    private String status = "";
    private String ConstructionYear = "";

    public Car(String brand,String status,String imageUrl,String year){
        this.brand = brand;
        this.status = status;
        this.imageUrl = imageUrl;
        this.ConstructionYear = year;
    }

    public String getBrand(){return brand; }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getImageUrl(){return imageUrl; }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getStatus(){return status; }

    public void setStatus(String status){
        this.status = status;
    }

    public String getConstructionYear(){return ConstructionYear; }

    public void setConstructionYear (String ConstructionYear){
        this.ConstructionYear = ConstructionYear;
    }
}

