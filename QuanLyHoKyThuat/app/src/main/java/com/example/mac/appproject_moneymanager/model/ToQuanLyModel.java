package com.example.mac.appproject_moneymanager.model;

public class ToQuanLyModel {

    public int ms_tql ;

    public String ten_tql ;


    public ToQuanLyModel(int ms_tql, String ten_tql) {
        this.ms_tql = ms_tql;
        this.ten_tql = ten_tql;
    }

    public ToQuanLyModel() {
    }

    public int getMs_tql() {
        return ms_tql;
    }

    public void setMs_tql(int ms_tql) {
        this.ms_tql = ms_tql;
    }

    public String getTen_tql() {
        return ten_tql;
    }

    public void setTen_tql(String ten_tql) {
        this.ten_tql = ten_tql;
    }
}
