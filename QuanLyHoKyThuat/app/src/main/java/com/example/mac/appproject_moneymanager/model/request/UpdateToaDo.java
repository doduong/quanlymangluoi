package com.example.mac.appproject_moneymanager.model.request;

public class UpdateToaDo {

    private Integer Hole_Id;
    private String Hole_Latitude;
    private String Hole_Longitude;

    public UpdateToaDo(Integer hole_Id, String hole_Latitude, String hole_Longitude) {
        Hole_Id = hole_Id;
        Hole_Latitude = hole_Latitude;
        Hole_Longitude = hole_Longitude;
    }

    public UpdateToaDo() {
    }

    public Integer getHole_Id() {
        return Hole_Id;
    }

    public void setHole_Id(Integer hole_Id) {
        Hole_Id = hole_Id;
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
