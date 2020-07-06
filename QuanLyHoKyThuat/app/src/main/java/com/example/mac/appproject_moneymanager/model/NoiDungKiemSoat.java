package com.example.mac.appproject_moneymanager.model;

import java.util.Date;

public class NoiDungKiemSoat {
    private Integer Hole_Id ;

    private String Inspect_Pic;

    private Integer Inspect_Status;

    private Integer Ok_Status;

    private String Description ;

    private Integer Inspect_Count;

    private int isks;
    private String Hole_Name;
    private Integer Period_Id;
    private String base_64_image;


    public NoiDungKiemSoat(Integer hole_id, String inspect_Pic, Integer inspect_Status, Integer ok_Status, String description,
                           Integer Inspect_Count, int isks, String Hole_Name, Integer Period_Id, String base_64_image) {
        this.Hole_Id = hole_id;
        this.Inspect_Pic = inspect_Pic;
        this.Inspect_Status = inspect_Status;
        this.Ok_Status = ok_Status;
        Description = description;
        this.Inspect_Count = Inspect_Count;
        this.isks = isks;
        this.Hole_Name = Hole_Name;
        this.Period_Id = Period_Id;
        this.base_64_image = base_64_image;
    }

    public String getBase_64_image() {
        return base_64_image;
    }

    public void setBase_64_image(String base_64_image) {
        this.base_64_image = base_64_image;
    }

    public Integer getPeriod_Id() {
        return Period_Id;
    }

    public void setPeriod_Id(Integer period_Id) {
        Period_Id = period_Id;
    }

    public String getHole_Name() {
        return Hole_Name;
    }

    public void setHole_Name(String hole_Name) {
        Hole_Name = hole_Name;
    }

    public Integer getHole_Id() {
        return Hole_Id;
    }

    public String getInspect_Pic() {
        return Inspect_Pic;
    }

    public Integer getInspect_Status() {
        return Inspect_Status;
    }

    public Integer getOk_Status() {
        return Ok_Status;
    }

    public String getDescription() {
        return Description;
    }

    public int getIsks() {
        return isks;
    }

    public void setHole_Id(Integer hole_id) {
        this.Hole_Id = hole_id;
    }

    public void setInspect_Pic(String inspect_Pic) {
        Inspect_Pic = inspect_Pic;
    }

    public void setInspect_Status(Integer inspect_Status) {
        Inspect_Status = inspect_Status;
    }

    public void setOk_Status(Integer ok_Status) {
        Ok_Status = ok_Status;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setIsks(int isks) {
        this.isks = isks;
    }

    public Integer getInspect_Count() {
        return Inspect_Count;
    }

    public void setInspect_Count(Integer inspect_Count) {
        Inspect_Count = inspect_Count;
    }
}
