package com.example.mac.appproject_moneymanager.api;

import com.example.mac.appproject_moneymanager.utils.CommonText;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BarcodeReaderApiManager {
    CommonText common = new CommonText();
    private static BarcodeReaderApiManager instance;
    private Retrofit retrofit;
    private BarcodeReaderApiManager(){
        retrofit = new Retrofit.Builder()
                .baseUrl(common.URL_API+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static BarcodeReaderApiManager getInstance(){
        if(instance == null){
            instance = new BarcodeReaderApiManager();
        }
        return instance;
    }


    public WaterApi waterApi(){
        return  retrofit.create(WaterApi.class);
    }

    public AccountApi accountApi(){
        return  retrofit.create(AccountApi.class);
    }


}
