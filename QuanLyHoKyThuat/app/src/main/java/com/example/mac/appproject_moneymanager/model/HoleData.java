package com.example.mac.appproject_moneymanager.model;

import java.io.Serializable;
import java.util.Date;

public class HoleData implements Serializable {
    public Integer Id ;

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


    public Integer Period_Id ;

    public Date Maintain_Day ;

    public Date Inspect_Day ;
    public String Maintain_Pic;

    public String Inspect_Pic ;

    public Integer Maintain_Status ;

    public Integer Inspect_Status;

    public Integer Ok_Status ;

    public Integer Inspect_Count ;
    public String description_holedata;
    public String Hole_Latitude;
    public String Hole_Longitude;

    public HoleData(Integer id, Integer hole_Id, Integer hole_Route, String hole_Name, String hole_Address, Integer street_Id, Integer holeStatus_Id, Integer holeSize_Id, Integer holeType_Id, String description, String holeType_Name, String holeSize_Name, String holeStatus_Name, String street_Name, Integer period_Id, Date maintain_Day, Date inspect_Day, String maintain_Pic, String inspect_Pic, Integer maintain_Status, Integer inspect_Status, Integer ok_Status, Integer Inspect_Count, String description_holedata) {
        Id = id;
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
        Period_Id = period_Id;
        Maintain_Day = maintain_Day;
        Inspect_Day = inspect_Day;
        Maintain_Pic = maintain_Pic;
        Inspect_Pic = inspect_Pic;
        Maintain_Status = maintain_Status;
        Inspect_Status = inspect_Status;
        Ok_Status = ok_Status;
        this.Inspect_Count = Inspect_Count;
        this.description_holedata = description_holedata;
    }

    public HoleData(Integer id, Integer hole_Id, Integer hole_Route, String hole_Name, String hole_Address, Integer street_Id, Integer holeStatus_Id, Integer holeSize_Id, Integer holeType_Id, String description, String holeType_Name, String holeSize_Name, String holeStatus_Name, String street_Name, Integer period_Id, Date maintain_Day, Date inspect_Day, String maintain_Pic, String inspect_Pic, Integer maintain_Status, Integer inspect_Status, Integer ok_Status, Integer inspect_Count, String description_holedata, String hole_Latitude, String hole_Longitude) {
        Id = id;
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
        Period_Id = period_Id;
        Maintain_Day = maintain_Day;
        Inspect_Day = inspect_Day;
        Maintain_Pic = maintain_Pic;
        Inspect_Pic = inspect_Pic;
        Maintain_Status = maintain_Status;
        Inspect_Status = inspect_Status;
        Ok_Status = ok_Status;
        Inspect_Count = inspect_Count;
        this.description_holedata = description_holedata;
        Hole_Latitude = hole_Latitude;
        Hole_Longitude = hole_Longitude;
    }

    public HoleData() {
    }

    public Integer getInspect_Count() {
        return Inspect_Count;
    }

    public void setInspect_Count(Integer inspect_Count) {
        Inspect_Count = inspect_Count;
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

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public Integer getPeriod_Id() {
        return Period_Id;
    }

    public void setPeriod_Id(Integer period_Id) {
        Period_Id = period_Id;
    }

    public Date getMaintain_Day() {
        return Maintain_Day;
    }

    public void setMaintain_Day(Date maintain_Day) {
        Maintain_Day = maintain_Day;
    }

    public Date getInspect_Day() {
        return Inspect_Day;
    }

    public void setInspect_Day(Date inspect_Day) {
        Inspect_Day = inspect_Day;
    }

    public String getMaintain_Pic() {
        return Maintain_Pic;
    }

    public void setMaintain_Pic(String maintain_Pic) {
        Maintain_Pic = maintain_Pic;
    }

    public String getInspect_Pic() {
        return Inspect_Pic;
    }

    public void setInspect_Pic(String inspect_Pic) {
        Inspect_Pic = inspect_Pic;
    }

    public Integer getMaintain_Status() {
        return Maintain_Status;
    }

    public void setMaintain_Status(Integer maintain_Status) {
        Maintain_Status = maintain_Status;
    }

    public Integer getInspect_Status() {
        return Inspect_Status;
    }

    public void setInspect_Status(Integer inspect_Status) {
        Inspect_Status = inspect_Status;
    }

    public Integer getOk_Status() {
        return Ok_Status;
    }

    public void setOk_Status(Integer ok_Status) {
        Ok_Status = ok_Status;
    }

    public Integer getInspect_count() {
        return Inspect_Count;
    }

    public void setInspect_count(Integer inspect_count) {
        this.Inspect_Count = inspect_count;
    }

    public String getDescription_holedata() {
        return description_holedata;
    }

    public void setDescription_holedata(String description_holedata) {
        this.description_holedata = description_holedata;
    }
}
