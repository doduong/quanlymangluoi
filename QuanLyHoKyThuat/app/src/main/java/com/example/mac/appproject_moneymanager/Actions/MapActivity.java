package com.example.mac.appproject_moneymanager.Actions;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.model.HoleData;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.ReadJson;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapActivity  extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    CommonText common;
    ArrayList<HoleData> lstholedata = new ArrayList<>();
    IconGenerator generator;
    Bitmap icon;
    private Dialog dialog;
    EditText edtmaho;
    EditText edttenho;
    EditText edtloaiho;
    EditText edtpho;
    EditText edtdiachi;
    EditText edtngayks;
    EditText edtngaybd;
    EditText edtghichu;
    EditText edtkichthuoc;
    EditText edttoado;
    ImageView imgPhoto;
    ImageView imgPhotoBd;
    Double latforcus = null;
    Double longforcus = null;
    private final int REQUEST_PERMISSION_PHONE_STATE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_layout);
        common = new CommonText();
        Bundle bundle = getIntent().getExtras();
        if(null!=bundle) {
            String lat_str = bundle.getString("latforcus");
            String long_str = bundle.getString("longforcus");
            if (!"".equals(lat_str) && !"".equals(long_str)) {
                latforcus = Double.parseDouble(lat_str);
                longforcus = Double.parseDouble(long_str);
            }
        }



        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        getThongTinHoKyThuat();

    }

    public void getThongTinHoKyThuat() {

        if (isConnected()) {

            String url = common.URL_API + "/hkt/getholedatafull";
            Log.d("URL", url);
            //new HttpAsyncTaskGetHoleOfStreet().execute(url);
            JSONArray jsonArrayKH;
            common.getListHoleData(url, lstholedata);

        } else {

            Toast.makeText(getApplication(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng toa_do;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_PERMISSION_PHONE_STATE);
                    //mMap.setMyLocationEnabled(true);
        }
        //mMap.setMyLocationEnabled(true);
        for (int i = 0; i < lstholedata.size(); i++) {

            // arrTD.get(i).get
            if (!"".equals(lstholedata.get(i).getHole_Latitude()) && !"".equals(lstholedata.get(i).getHole_Longitude())) {
                Double latitude = Double.parseDouble(lstholedata.get(i).getHole_Latitude());
                Double longitude = Double.parseDouble(lstholedata.get(i).getHole_Longitude());
                toa_do = new LatLng(latitude, longitude);

                generator = new IconGenerator(getApplication());
                TextView text = new TextView(getApplication());
                String hole_name = lstholedata.get(i).getHole_Name();

                //String tenhienthi = xulytenkh(tenkh);
                //text.setText(tenhienthi+ "\n" + String.valueOf(arrTD.get(i).getId()));
                text.setText(hole_name);

                generator.setStyle(IconGenerator.STYLE_GREEN);

                generator.setContentView(text);
                icon = generator.makeIcon();


                Marker maker = mMap.addMarker(new MarkerOptions()
                        .title(lstholedata.get(i).getHole_Name())
                        .position(toa_do)
                        .draggable(true)
                        .snippet(String.valueOf(lstholedata.get(i).getHole_Id()))
                        .icon(BitmapDescriptorFactory.fromBitmap(icon)));
            }

        }
        //   .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        if (lstholedata.size() > 0)
            if(latforcus!=null && longforcus !=null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latforcus, longforcus), 14));
            }else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.858765, 106.682100), 13));
            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showDialog(marker);
                return false;
            }
        });

    }

    public void showDialog(Marker maker) {

        dialog = new Dialog(MapActivity.this);
        dialog.setTitle("Hố Kỹ Thuật");
        dialog.setContentView(R.layout.activity_popup_marker);
        khaibaobien();
        getthongtinhokythuatmaker(maker);
        dialog.show();

    }

    public void khaibaobien() {
        edtmaho = (EditText) dialog.findViewById(R.id.edtmaho);
        edttenho = (EditText) dialog.findViewById(R.id.edtTenHo);
        edtloaiho = (EditText) dialog.findViewById(R.id.edtloaiho);
        edtdiachi = (EditText) dialog.findViewById(R.id.edtdiachi);
        edtngaybd = (EditText) dialog.findViewById(R.id.edtngaybd);
        edtngayks = (EditText) dialog.findViewById(R.id.edtngayks);
        edtpho = (EditText) dialog.findViewById(R.id.edtpho);
        edtghichu = (EditText) dialog.findViewById(R.id.edtghichu);
        edtkichthuoc = (EditText) dialog.findViewById(R.id.edtktho);
        edttoado =  (EditText) dialog.findViewById(R.id.edttoado);
        imgPhoto = (ImageView) dialog.findViewById(R.id.imgPhoto);
        imgPhotoBd = (ImageView) dialog.findViewById(R.id.imgPhotoBd);
        //edtluluong1 =(EditText) dialog.findViewById(R.id.edtluluong1);

    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getApplication().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void getthongtinhokythuatmaker(Marker marker) {
        Integer id = Integer.parseInt(marker.getSnippet());
        //int hole_id = Integer.parseInt(id);
        for(int i =0; i<lstholedata.size(); i++){
            HoleData holedata =lstholedata.get(i);
            if(id.intValue() == holedata.getHole_Id()){
                edtmaho.setText(String.valueOf(holedata.getHole_Id()));
                edttenho.setText(holedata.getHole_Name());
                edtloaiho.setText(holedata.getHoleType_Name());
                edtkichthuoc.setText(holedata.HoleSize_Name );
                edtpho.setText(holedata.getStreet_Name());
                edtdiachi.setText(holedata.getHole_Address());
                edtghichu.setText(holedata.getDescription_holedata());
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                if (null != holedata.getInspect_Day()) {
                    edtngayks.setText(common.cat10kitucuoi(format2.format(holedata.getInspect_Day())));
                } else {
                    edtngayks.setText("");
                }
                if (null != holedata.getMaintain_Day()) {
                    edtngaybd.setText(common.cat10kitucuoi(format2.format(holedata.getMaintain_Day())));
                } else {
                    edtngaybd.setText("");
                }
                edttoado.setText(holedata.getHole_Latitude()+ ", " + holedata.getHole_Longitude());
                if("".equals(holedata.getInspect_Pic())||holedata.getInspect_Pic().equals("null")){
                    imgPhoto.setImageBitmap(null);
                }else {
                    imgPhoto.setImageBitmap(common.getBitmapFromURL(holedata.getInspect_Pic()));
                }
                if("".equals(holedata.getMaintain_Pic())||holedata.getMaintain_Pic().equals("null")){
                    imgPhotoBd.setImageBitmap(null);
                }else {
                    imgPhotoBd.setImageBitmap(common.getBitmapFromURL(holedata.getMaintain_Pic()));
                }
            }


        }


    }


}
