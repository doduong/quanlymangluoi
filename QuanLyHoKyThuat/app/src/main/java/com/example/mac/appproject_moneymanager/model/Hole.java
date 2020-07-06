package com.example.mac.appproject_moneymanager.model;

import java.io.Serializable;

public class Hole implements Serializable {
    public Integer Hole_Id ;

    public Integer Hole_Route ;

    public String Hole_Name ;

    public String Hole_Address ;

    public Integer Street_Id;

    public Integer HoleStatus_Id ;

    public Integer HoleSize_Id;

    public Integer HoleType_Id ;

    public String Description ;

    public String HoleType_Name ;
    public String HoleSize_Name ;

    public String HoleStatus_Name ;

    public String Street_Name ;

    public String Hole_Latitude;

    public String Hole_Longitude;

    public String Maintain_Name;

    public String Inspect_Name;

    public Hole(Integer hole_Id, Integer hole_Route, String hole_Name, String hole_Address, Integer street_Id, Integer holeStatus_Id, Integer holeSize_Id, Integer holeType_Id, String description, String holeType_Name, String holeSize_Name, String holeStatus_Name, String street_Name, String hole_Latitude, String hole_Longitude, String maintain_Name, String inspect_Name) {
        Hole_Id = hole_Id;
        Hole_Route = hole_Route;
        Hole_Name = hole_Name;
        Hole_Address = hole_Address;
        Street_Id = street_Id;
        HoleStatus_Id = holeStatus_Id;
        HoleSize_Id = holeSize_Id;
        HoleType_Id = holeType_Id;
        Description = description;
        HoleType_Name = holeType_Name;
        HoleSize_Name = holeSize_Name;
        HoleStatus_Name = holeStatus_Name;
        Street_Name = street_Name;
        Hole_Latitude = hole_Latitude;
        Hole_Longitude = hole_Longitude;
        Maintain_Name = maintain_Name;
        Inspect_Name = inspect_Name;
    }

    public Integer getHole_Id() {
        return Hole_Id;
    }

    public void setHole_Id(Integer hole_Id) {
        Hole_Id = hole_Id;
    }

    public Integer getHole_Route() {
        return Hole_Route;
    }

    public void setHole_Route(Integer hole_Route) {
        Hole_Route = hole_Route;
    }

    public String getHole_Name() {
        return Hole_Name;
    }

    public void setHole_Name(String hole_Name) {
        Hole_Name = hole_Name;
    }

    public String getHole_Address() {
        return Hole_Address;
    }

    public void setHole_Address(String hole_Address) {
        Hole_Address = hole_Address;
    }

    public Integer getStreet_Id() {
        return Street_Id;
    }

    public void setStreet_Id(Integer street_Id) {
        Street_Id = street_Id;
    }

    public Integer getHoleStatus_Id() {
        return HoleStatus_Id;
    }

    public void setHoleStatus_Id(Integer holeStatus_Id) {
        HoleStatus_Id = holeStatus_Id;
    }

    public Integer getHoleSize_Id() {
        return HoleSize_Id;
    }

    public void setHoleSize_Id(Integer holeSize_Id) {
        HoleSize_Id = holeSize_Id;
    }

    public Integer getHoleType_Id() {
        return HoleType_Id;
    }

    public void setHoleType_Id(Integer holeType_Id) {
        HoleType_Id = holeType_Id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHoleType_Name() {
        return HoleType_Name;
    }

    public void setHoleType_Name(String holeType_Name) {
        HoleType_Name = holeType_Name;
    }

    public String getHoleSize_Name() {
        return HoleSize_Name;
    }

    public void setHoleSize_Name(String holeSize_Name) {
        HoleSize_Name = holeSize_Name;
    }

    public String getHoleStatus_Name() {
        return HoleStatus_Name;
    }

    public void setHoleStatus_Name(String holeStatus_Name) {
        HoleStatus_Name = holeStatus_Name;
    }

    public String getStreet_Name() {
        return Street_Name;
    }

    public void setStreet_Name(String street_Name) {
        Street_Name = street_Name;
    }

    public String getMaintain_Name() {
        return Maintain_Name;
    }

    public void setMaintain_Name(String maintain_Name) {
        Maintain_Name = maintain_Name;
    }

    public String getInspect_Name() {
        return Inspect_Name;
    }

    public void setInspect_Name(String inspect_Name) {
        Inspect_Name = inspect_Name;
    }

    public String getHole_Latitude() {
        return Hole_Latitude;
    }

    public void setHole_Latitude(String hole_Latitude) {
        Hole_Latitude = hole_Latitude;
    }

    public String getHole_Longitude() {
        return Hole_Longitude;
    }

    public void setHole_Longitude(String hole_Longitude) {
        Hole_Longitude = hole_Longitude;
    }
}
