package com.example.mac.appproject_moneymanager.model;

public class NoiDungBaoDuong {

    private Integer Hole_Id ;

    private String Maintain_Pic;

    private Integer Maintain_Status;

    private String Description ;

    private String Hole_Name;
    private Integer Period_Id;
    private String base_64_image;

    public NoiDungBaoDuong(Integer hole_Id, String maintain_Pic, Integer maintain_Status, String description, String hole_Name, Integer period_Id, String base_64_image) {
        Hole_Id = hole_Id;
        Maintain_Pic = maintain_Pic;
        Maintain_Status = maintain_Status;
        Description = description;
        Hole_Name = hole_Name;
        Period_Id = period_Id;
        this.base_64_image = base_64_image;
    }

    public Integer getHole_Id() {
        return Hole_Id;
    }

    public void setHole_Id(Integer hole_Id) {
        Hole_Id = hole_Id;
    }

    public String getMaintain_Pic() {
        return Maintain_Pic;
    }

    public void setMaintain_Pic(String maintain_Pic) {
        Maintain_Pic = maintain_Pic;
    }

    public Integer getMaintain_Status() {
        return Maintain_Status;
    }

    public void setMaintain_Status(Integer maintain_Status) {
        Maintain_Status = maintain_Status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHole_Name() {
        return Hole_Name;
    }

    public void setHole_Name(String hole_Name) {
        Hole_Name = hole_Name;
    }

    public Integer getPeriod_Id() {
        return Period_Id;
    }

    public void setPeriod_Id(Integer period_Id) {
        Period_Id = period_Id;
    }

    public String getBase_64_image() {
        return base_64_image;
    }

    public void setBase_64_image(String base_64_image) {
        this.base_64_image = base_64_image;
    }
}
