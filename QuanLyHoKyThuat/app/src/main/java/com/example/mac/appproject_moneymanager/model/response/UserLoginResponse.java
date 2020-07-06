package com.example.mac.appproject_moneymanager.model.response;

public class UserLoginResponse {
    private String ten_userthanhtra;
    private String mk_userthanhtra;

    public UserLoginResponse(String ten_userthanhtra, String mk_userthanhtra) {
        this.ten_userthanhtra = ten_userthanhtra;
        this.mk_userthanhtra = mk_userthanhtra;
    }

    public String getTen_userthanhtra() {
        return ten_userthanhtra;
    }

    public void setTen_userthanhtra(String ten_userthanhtra) {
        this.ten_userthanhtra = ten_userthanhtra;
    }

    public String getMk_userthanhtra() {
        return mk_userthanhtra;
    }

    public void setMk_userthanhtra(String mk_userthanhtra) {
        this.mk_userthanhtra = mk_userthanhtra;
    }
}
