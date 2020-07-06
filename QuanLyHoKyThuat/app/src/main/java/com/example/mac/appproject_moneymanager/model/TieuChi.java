package com.example.mac.appproject_moneymanager.model;

public class TieuChi {
    private int ms_tc;
    private String ten_tc;

    public TieuChi(int ms_tc, String ten_tc) {
        this.ms_tc = ms_tc;
        this.ten_tc = ten_tc;
    }

    public TieuChi() {
    }

    public int getMs_tc() {
        return ms_tc;
    }

    public void setMs_tc(int ms_tc) {
        this.ms_tc = ms_tc;
    }

    public String getTen_tc() {
        return ten_tc;
    }

    public void setTen_tc(String ten_tc) {
        this.ten_tc = ten_tc;
    }
}
