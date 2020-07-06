package com.example.mac.appproject_moneymanager.api;


import com.example.mac.appproject_moneymanager.model.BarcodeResponse;
import com.example.mac.appproject_moneymanager.model.NoiDungBaoDuong;
import com.example.mac.appproject_moneymanager.model.NoiDungKiemSoat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WaterApi {

    /*@PUT("khachhang/savecontrol/{id}")
    Call<BarcodeResponse> updateInfoControl(@Path("id") int id, @Body KhachHangUpdateRequest kiemsoatRequest);*/

    @POST("hkt/luuKiemSoat")
    Call<Void> luuKiemSoat(@Body NoiDungKiemSoat noiDungKiemSoat);
    @POST("hkt/luuBaoDuong")
    Call<Void> luuBaoDuong(@Body NoiDungBaoDuong noiDungBaoDuong);


}
