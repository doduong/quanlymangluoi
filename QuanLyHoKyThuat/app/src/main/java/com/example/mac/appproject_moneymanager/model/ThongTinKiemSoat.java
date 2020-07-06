package com.example.mac.appproject_moneymanager.model;

import java.util.Date;

public class ThongTinKiemSoat {

    private Integer ms_ks ;

    private Integer ms_mnoi ;

    private Integer ms_userthanhtra ;

    private Integer ms_tk ;

    private Date ngay_kiem_soat ;

    private Integer chi_so_cu ;

    private Integer chi_so_ks ;

    private Integer so_tthu_ks ;

    private Integer ms_dk_ks ;

    private Integer ms_tt_ks ;

    private String ghi_chu_ks ;

    private String url_image_ks ;

    private String thu_muc ;

    public ThongTinKiemSoat(Integer ms_ks, Integer ms_mnoi, Integer ms_userthanhtra, Integer ms_tk, Date ngay_kiem_soat, Integer chi_so_cu, Integer chi_so_ks, Integer so_tthu_ks, Integer ms_dk_ks, Integer ms_tt_ks, String ghi_chu_ks, String url_image_ks, String thu_muc) {
        this.ms_ks = ms_ks;
        this.ms_mnoi = ms_mnoi;
        this.ms_userthanhtra = ms_userthanhtra;
        this.ms_tk = ms_tk;
        this.ngay_kiem_soat = ngay_kiem_soat;
        this.chi_so_cu = chi_so_cu;
        this.chi_so_ks = chi_so_ks;
        this.so_tthu_ks = so_tthu_ks;
        this.ms_dk_ks = ms_dk_ks;
        this.ms_tt_ks = ms_tt_ks;
        this.ghi_chu_ks = ghi_chu_ks;
        this.url_image_ks = url_image_ks;
        this.thu_muc = thu_muc;
    }

    public ThongTinKiemSoat() {
    }


    public Integer getMs_ks() {
        return ms_ks;
    }

    public void setMs_ks(Integer ms_ks) {
        this.ms_ks = ms_ks;
    }

    public Integer getMs_mnoi() {
        return ms_mnoi;
    }

    public void setMs_mnoi(Integer ms_mnoi) {
        this.ms_mnoi = ms_mnoi;
    }

    public Integer getMs_userthanhtra() {
        return ms_userthanhtra;
    }

    public void setMs_userthanhtra(Integer ms_userthanhtra) {
        this.ms_userthanhtra = ms_userthanhtra;
    }

    public Integer getMs_tk() {
        return ms_tk;
    }

    public void setMs_tk(Integer ms_tk) {
        this.ms_tk = ms_tk;
    }

    public Date getNgay_kiem_soat() {
        return ngay_kiem_soat;
    }

    public void setNgay_kiem_soat(Date ngay_kiem_soat) {
        this.ngay_kiem_soat = ngay_kiem_soat;
    }

    public Integer getChi_so_cu() {
        return chi_so_cu;
    }

    public void setChi_so_cu(Integer chi_so_cu) {
        this.chi_so_cu = chi_so_cu;
    }

    public Integer getChi_so_ks() {
        return chi_so_ks;
    }

    public void setChi_so_ks(Integer chi_so_ks) {
        this.chi_so_ks = chi_so_ks;
    }

    public Integer getSo_tthu_ks() {
        return so_tthu_ks;
    }

    public void setSo_tthu_ks(Integer so_tthu_ks) {
        this.so_tthu_ks = so_tthu_ks;
    }

    public Integer getMs_dk_ks() {
        return ms_dk_ks;
    }

    public void setMs_dk_ks(Integer ms_dk_ks) {
        this.ms_dk_ks = ms_dk_ks;
    }

    public Integer getMs_tt_ks() {
        return ms_tt_ks;
    }

    public void setMs_tt_ks(Integer ms_tt_ks) {
        this.ms_tt_ks = ms_tt_ks;
    }

    public String getGhi_chu_ks() {
        return ghi_chu_ks;
    }

    public void setGhi_chu_ks(String ghi_chu_ks) {
        this.ghi_chu_ks = ghi_chu_ks;
    }

    public String getUrl_image_ks() {
        return url_image_ks;
    }

    public void setUrl_image_ks(String url_image_ks) {
        this.url_image_ks = url_image_ks;
    }

    public String getThu_muc() {
        return thu_muc;
    }

    public void setThu_muc(String thu_muc) {
        this.thu_muc = thu_muc;
    }
}
