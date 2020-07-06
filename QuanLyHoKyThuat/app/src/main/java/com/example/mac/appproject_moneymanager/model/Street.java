package com.example.mac.appproject_moneymanager.model;

public class Street {

    public Integer Street_Id ;

    public Integer Street_Route ;

    public String Street_Name ;

    public String Descristion ;

    public Street(Integer street_Id, Integer street_Route, String street_Name, String descristion) {
        Street_Id = street_Id;
        Street_Route = street_Route;
        Street_Name = street_Name;
        Descristion = descristion;
    }

    public Street() {
    }

    public Integer getStreet_Id() {
        return Street_Id;
    }

    public void setStreet_Id(Integer street_Id) {
        Street_Id = street_Id;
    }

    public Integer getStreet_Route() {
        return Street_Route;
    }

    public void setStreet_Route(Integer street_Route) {
        Street_Route = street_Route;
    }

    public String getStreet_Name() {
        return Street_Name;
    }

    public void setStreet_Name(String street_Name) {
        Street_Name = street_Name;
    }

    public String getDescristion() {
        return Descristion;
    }

    public void setDescristion(String descristion) {
        Descristion = descristion;
    }

    @Override
    public String toString(){
        return Street_Name;
    }
}
