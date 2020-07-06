package com.example.mac.appproject_moneymanager.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;

import com.example.mac.appproject_moneymanager.model.Hole;
import com.example.mac.appproject_moneymanager.model.HoleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommonText {
    public CommonText() {
    }

    //SERVER TEST
    //public  String URL_API = "http://123.26.252.98:8087/api";
    //public  String URL_IMAGE = "http://123.26.252.98:8087";
    //SERVER THAT
    public  String URL_API = "http://113.160.100.217:8083/api";
    public  String URL_IMAGE = "http://113.160.100.217:8083";

    //http://123.26.252.98:8084



    public String cat2kitucuoi(String str){
        return str.substring(0,str.length()-2);
    }
    public String cat2kitudauvacuoi(String str){
        return str.substring(1,str.length()-1);
    }
    public String cat10kitucuoi(String str){
        return str.substring(0,10);
    }
    public String lay7kytudau(String str){
        return str.substring(0,7);
    }

    public Bitmap getBitmapFromURL(String src){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        src = URL_IMAGE+src;
        try {
            URL url = new URL(src);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String strimBarcode(String resultscan){

        if(resultscan.length()>4) {
            resultscan = resultscan.substring(4, resultscan.length());
        }
        return resultscan;
    }

    public String GetDataToValue(Object Data, String sDefault)
    {
        String kq;
        if (Data==null||Data.toString().equals("null"))
        {
            kq=sDefault;
        }
        else
        {
            if (Data.toString().equals("")||Data.toString().equals("{}"))
            {
                kq=sDefault;
            }
            else   {kq=Data.toString();}
        }
        return kq;
    }

    public static String WriteNum(long num)
    {
        byte i;
        long sochia, T1, T2, T3;
        long luu;
        String st = "";
        String[] hang;
        String[] donvi;
        String ChuDau="";
        hang = new String[10];
        hang[0] = "";
        hang[1] = " một";
        hang[2] = " hai";
        hang[3] = " ba";
        hang[4] = " bốn";
        hang[5] = " năm";
        hang[6] = " sáu";
        hang[7] = " bảy";
        hang[8] = " tám";
        hang[9] = " chín";
        donvi = new String[4];
        donvi[0] = " tỉ";
        donvi[1] = " triệu";
        donvi[2] = " nghìn";
        donvi[3] = "";
        sochia = 1000000000;

        for (i = 0; i < 4; i++)
        {
            luu = num;
            luu = luu / sochia % 1000;
            T1 = luu / 100;
            T2 = luu / 10 % 10;
            T3 = luu % 10;
            if (T1 == 0 && T2 == 0 && T3 == 0)
            {
                sochia = sochia / 1000;
            }
            else
            {
                if (T1 == 0 && st != "") { st = st + " không"; }
                st = st + hang[(int)T1];
                if (st != "") { st = st + " trăm"; }
                if (T2 != 0)
                {
                    if (T2 == 1)
                    {
                        st = st + " mười";
                        if (T3 != 5) { st = st + hang[(int)T3]; }
                        if (T3 == 5) { st = st + " lăm"; }
                    }
                    else
                    {
                        st = st + hang[(int)T2] + " mươi";
                        if (T3 != 1 && T3 != 5) { st = st + hang[(int)T3]; }
                        if (T3 == 1 && T2 > 1) { st = st + " mốt"; }
                        if (T3 == 5) { st = st + " lăm"; }
                    }
                }
                else
                {
                    if (T3 != 0 && st != "") { st = st + " linh"; }
                    st = st + hang[(int)T3];
                }

                if (st != "") { st = st + donvi[i]; }
                sochia = sochia / 1000;
            }
        }
        if (st.trim() != "")
        {
            if (st.trim().length() > 1)
            {
                ChuDau = st.trim().substring(0, 1);
                ChuDau = ChuDau.toUpperCase();
                st = ChuDau + st.trim().substring(1, st.length() - 1);
            }
        }
        return st.trim() + " đồng";
    }

    public boolean checkGetCustomerInfoWereRead(Integer madb, Integer ms_tk, Integer ms_tuyendoc) {


            String url = URL_API + "/GetCustomerInfo?ms_mnoi=" + madb + "&ms_tk=" + ms_tk + "&ms_tuyen=" + ms_tuyendoc;
            Log.d("GetCustomerInfo", url);

            String ms_tuyen = "";
            String chi_so_moi = null;
            String so_tthu = null;
            String ngay_doc_moi = null;
            JSONObject jsonKH;
            JSONArray jsonArray = new JSONArray();

            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                if (jsonArray.length() > 0) {
                    jsonKH = jsonArray.getJSONObject(0);
                    chi_so_moi = jsonKH.getString("chi_so_moi");
                    so_tthu = jsonKH.getString("so_tieu_thu");
                    ngay_doc_moi = jsonKH.getString("ngay_doc_moi");

                    if ("null".equals(chi_so_moi) && "null".equals(so_tthu) && "null".equals(ngay_doc_moi)) {
                        return true;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        return false;
    }
    //get list HoleData from url
    public void getListHoleData(String url, ArrayList<HoleData> lstHoleData){
        JSONArray jsonArrayKH;
        try {
            jsonArrayKH = ReadJson.readJSonArrayFromURL(url);
            for (int i = 0; i < jsonArrayKH.length(); i++) {


                HoleData holeData;
                JSONObject objTTTT = jsonArrayKH.getJSONObject(i);
                Integer Id = objTTTT.getInt("Id");
                Integer Hole_Id =objTTTT.getInt("Hole_Id");
                Integer Hole_Route =objTTTT.getInt("Hole_Route");
                String Hole_Name = objTTTT.getString("Hole_Name");
                String Hole_Address = objTTTT.getString("Hole_Address");
                Integer Street_Id =objTTTT.getInt("Street_Id");
                Integer HoleStatus_Id =objTTTT.getInt("HoleStatus_Id");

                Integer HoleSize_Id =objTTTT.getInt("HoleSize_Id");
                Integer HoleType_Id =objTTTT.getInt("HoleType_Id");
                String Description = objTTTT.getString("Description");
                String HoleType_Name = objTTTT.getString("HoleType_Name");
                String HoleSize_Name = objTTTT.getString("HoleSize_Name");
                String HoleStatus_Name = objTTTT.getString("HoleStatus_Name");
                String Street_Name = objTTTT.getString("Street_Name");
                Integer Period_Id =objTTTT.getInt("Period_Id");
                String str_Maintain_Day = GetDataToValue(objTTTT.getString("Maintain_Day"),"");
                String Maintain_Day = "";
                if(!str_Maintain_Day.equals("")) {
                    Maintain_Day = cat10kitucuoi(objTTTT.getString("Maintain_Day"));
                }
                String str_Inspect_Day = GetDataToValue(objTTTT.getString("Inspect_Day"),"");
                String Inspect_Day = "";
                if(!str_Inspect_Day.equals("")) {
                    Inspect_Day = cat10kitucuoi(objTTTT.getString("Inspect_Day"));
                }
                String Maintain_Pic = GetDataToValue(objTTTT.getString("Maintain_Pic"),"");
                String Inspect_Pic =  GetDataToValue(objTTTT.getString("Inspect_Pic"),"");
                Integer Maintain_Status = objTTTT.getInt("Maintain_Status");
                Integer Inspect_Status = objTTTT.getInt("Inspect_Status");
                Integer Ok_Status = objTTTT.getInt("Ok_Status");
                Integer Inspect_Count = objTTTT.getInt("Inspect_Count");
                String description_holedata = GetDataToValue(objTTTT.getString("description_holedata"),"");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date_Maintain_Day = null;
                Date date_Inspect_Day = null;
                Date ngay_loc = null;
                try {
                    if(!"".equals(Maintain_Day)) {
                        date_Maintain_Day = format1.parse(Maintain_Day);
                    }
                    if(!"".equals(Inspect_Day)) {
                        date_Inspect_Day = format1.parse(Inspect_Day);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String Hole_Latitude = GetDataToValue(objTTTT.getString("Hole_Latitude"),"");
                String Hole_Longitude = GetDataToValue(objTTTT.getString("Hole_Longitude"),"");

                holeData = new HoleData(Id, Hole_Id, Hole_Route, Hole_Name, Hole_Address, Street_Id, HoleStatus_Id,
                        HoleSize_Id, HoleType_Id, Description, HoleType_Name, HoleSize_Name, HoleStatus_Name, Street_Name,
                        Period_Id, date_Maintain_Day, date_Inspect_Day, Maintain_Pic, Inspect_Pic, Maintain_Status, Inspect_Status,
                        Ok_Status, Inspect_Count, description_holedata, Hole_Latitude, Hole_Longitude );
                lstHoleData.add(holeData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //end

    //get HoleData from Url By Hole_ID
    public HoleData getListHoleDataById(String url){
        JSONArray jsonArrayKH;
        HoleData holeDataFist = new HoleData();
        try {
                jsonArrayKH = ReadJson.readJSonArrayFromURL(url);
                JSONObject objTTTT = jsonArrayKH.getJSONObject(0);
                Integer Id = objTTTT.getInt("Id");
                Integer Hole_Id =objTTTT.getInt("Hole_Id");
                Integer Hole_Route =objTTTT.getInt("Hole_Route");
                String Hole_Name = objTTTT.getString("Hole_Name");
                String Hole_Address = objTTTT.getString("Hole_Address");
                Integer Street_Id =objTTTT.getInt("Street_Id");
                Integer HoleStatus_Id =objTTTT.getInt("HoleStatus_Id");

                Integer HoleSize_Id =objTTTT.getInt("HoleSize_Id");
                Integer HoleType_Id =objTTTT.getInt("HoleType_Id");
                String Description = objTTTT.getString("Description");
                String HoleType_Name = objTTTT.getString("HoleType_Name");
                String HoleSize_Name = objTTTT.getString("HoleSize_Name");
                String HoleStatus_Name = objTTTT.getString("HoleStatus_Name");
                String Street_Name = objTTTT.getString("Street_Name");
                Integer Period_Id =objTTTT.getInt("Period_Id");
                String str_Maintain_Day = GetDataToValue(objTTTT.getString("Maintain_Day"),"");
                String Maintain_Day = "";
                if(!str_Maintain_Day.equals("")) {
                    Maintain_Day = cat10kitucuoi(objTTTT.getString("Maintain_Day"));
                }
                String str_Inspect_Day = GetDataToValue(objTTTT.getString("Inspect_Day"),"");
                String Inspect_Day = "";
                if(!str_Inspect_Day.equals("")) {
                    Inspect_Day = cat10kitucuoi(objTTTT.getString("Inspect_Day"));
                }
                String Maintain_Pic = GetDataToValue(objTTTT.getString("Maintain_Pic"),"");
                String Inspect_Pic =  GetDataToValue(objTTTT.getString("Inspect_Pic"),"");
                Integer Maintain_Status = objTTTT.getInt("Maintain_Status");
                Integer Inspect_Status = objTTTT.getInt("Inspect_Status");
                Integer Ok_Status = objTTTT.getInt("Ok_Status");
                Integer Inspect_Count = objTTTT.getInt("Inspect_Count");
                String description_holedata = GetDataToValue(objTTTT.getString("description_holedata"),"");
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date_Maintain_Day = null;
                Date date_Inspect_Day = null;
                Date ngay_loc = null;
                try {
                    if(!"".equals(Maintain_Day)) {
                        date_Maintain_Day = format1.parse(Maintain_Day);
                    }
                    if(!"".equals(Inspect_Day)) {
                        date_Inspect_Day = format1.parse(Inspect_Day);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String Hole_Latitude = GetDataToValue(objTTTT.getString("Hole_Latitude"),"");
                String Hole_Longitude = GetDataToValue(objTTTT.getString("Hole_Longitude"),"");

                holeDataFist = new HoleData(Id, Hole_Id, Hole_Route, Hole_Name, Hole_Address, Street_Id, HoleStatus_Id,
                        HoleSize_Id, HoleType_Id, Description, HoleType_Name, HoleSize_Name, HoleStatus_Name, Street_Name,
                        Period_Id, date_Maintain_Day, date_Inspect_Day, Maintain_Pic, Inspect_Pic, Maintain_Status, Inspect_Status,
                        Ok_Status, Inspect_Count, description_holedata, Hole_Latitude, Hole_Longitude );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return holeDataFist;
    }

    //get list HoleData from url
    public void getListHole(String url, ArrayList<Hole> lstHole){
        JSONArray jsonArrayKH;
        try {
            jsonArrayKH = ReadJson.readJSonArrayFromURL(url);
            for (int i = 0; i < jsonArrayKH.length(); i++) {


                Hole hole;
                JSONObject objTTTT = jsonArrayKH.getJSONObject(i);
                Integer Hole_Id =objTTTT.getInt("Hole_Id");
                Integer Hole_Route =objTTTT.getInt("Hole_Route");
                String Hole_Name = objTTTT.getString("Hole_Name");
                String Hole_Address = objTTTT.getString("Hole_Address");
                Integer Street_Id =objTTTT.getInt("Street_Id");
                Integer HoleStatus_Id =objTTTT.getInt("HoleStatus_Id");

                Integer HoleSize_Id =objTTTT.getInt("HoleSize_Id");
                Integer HoleType_Id =objTTTT.getInt("HoleType_Id");
                String Description = GetDataToValue(objTTTT.getString("Description"),"");
                String HoleType_Name = objTTTT.getString("HoleType_Name");
                String HoleSize_Name = objTTTT.getString("HoleSize_Name");
                String HoleStatus_Name = objTTTT.getString("HoleStatus_Name");
                String Street_Name = objTTTT.getString("Street_Name");
                String Hole_Latitude = GetDataToValue(objTTTT.getString("Hole_Latitude"),"");
                String Hole_Longitude = GetDataToValue(objTTTT.getString("Hole_Longitude"),"");
                String Maintain_Name = GetDataToValue(objTTTT.getString("Maintain_Name"),"");
                String Inspect_Name = GetDataToValue(objTTTT.getString("Inspect_Name"),"");

                hole= new Hole( Hole_Id, Hole_Route, Hole_Name, Hole_Address, Street_Id, HoleStatus_Id,
                        HoleSize_Id, HoleType_Id, Description, HoleType_Name, HoleSize_Name, HoleStatus_Name, Street_Name,
                         Hole_Latitude, Hole_Longitude, Maintain_Name, Inspect_Name );
                lstHole.add(hole);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //end

    public boolean coAnh(ImageView imgPhoto) {

        if (imgPhoto.getDrawable() == null) {
            return false;
        }
        return true;

    }


}
