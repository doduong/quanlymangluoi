package com.example.mac.appproject_moneymanager.Actions;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.api.BarcodeReaderApiManager;
import com.example.mac.appproject_moneymanager.model.HoleData;
import com.example.mac.appproject_moneymanager.model.NoiDungKiemSoat;
import com.example.mac.appproject_moneymanager.model.request.UpdateToaDo;
import com.example.mac.appproject_moneymanager.utils.BaseActivity;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.GPSTrack;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;


public class NhapThongTinKiemSoat extends BaseActivity {
    HoleData holeData;
    TextView tvtentuyen;
    TextView tvSoLuongAnh;

    EditText edtmaho;
    EditText edtloaiho;
    EditText edttenpho;
    EditText edtDiaChi;
    EditText edtngayks;
    EditText edtngaybd;
    EditText edtghichu;
    EditText edttoado;

    ImageButton btncamera;
    Button btnLuu;
    Button btnCapNhatGPS;
    Button btnvitri;
    Spinner spindktt;
    CheckBox chkkhongdat;

    ImageView imgPhoto;
    ImageView imgPhotoBd;
    String base_64_image;
    String ba1;
    int ok_status = 1;
    CommonText common;
    Bitmap selectedBitmap;
    private Uri photoUri;
    private final static int TAKE_PHOTO = 100;
    private final static String PHOTO_URI = "photoUri";
    GPSTrack gps;
    private static DecimalFormat df2 = new DecimalFormat("#.#######");
    Double latitude = null;
    Double longitude = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_thong_tin_kiem_soat);
        initView();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]
                    { android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        if (ActivityCompat.checkSelfPermission(NhapThongTinKiemSoat.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NhapThongTinKiemSoat.this, new String[]
                    {android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
        if (ActivityCompat.checkSelfPermission(NhapThongTinKiemSoat.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NhapThongTinKiemSoat.this, new String[]
                    {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            return;
        }
        /*Xác nhận quyền truy cập GPS*/
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                // Permission already Granted
                //Do your work here
                //Perform operations here only which requires permission
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        gps = new GPSTrack(NhapThongTinKiemSoat.this);
        if(gps.canGetLocation()) {

             latitude =  gps.getLatitude();
             longitude = gps.getLongitude();
            //Toast.makeText(NhapThongTinKiemSoat.this, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        }


        btnCapNhatGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(latitude!=null && longitude!=null) {
                    latitude = Math.round(latitude*10000000.0)/10000000.0;
                    longitude = Math.round(longitude*10000000.0)/10000000.0;

                    android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapThongTinKiemSoat.this);
                    b.setTitle("Cảnh báo");
                    b.setMessage("Bạn chắc chắn cập nhật tọa độ: " + latitude + "," + longitude);
                    b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            updateToaDo(holeData.getHole_Id(), String.valueOf(latitude) ,String.valueOf(longitude));
                            edttoado.setText(latitude+"," +longitude);

                        }
                    });
                    b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if(holeData.getHole_Latitude()==null&holeData.getHole_Longitude()==null) {
                                edttoado.setText("");
                            }
                        }
                    });
                    b.create().show();
                }

            }
        });
        //End GPS
        btnvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhapThongTinKiemSoat.this, MapActivity.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data from getFactualResults method to bundle
                if(holeData.getHole_Latitude()!=null&&holeData.getHole_Latitude()!=null) {
                    bundle.putString("latforcus", holeData.getHole_Latitude());
                    bundle.putString("longforcus", holeData.getHole_Longitude());
                }else {
                    bundle.putString("latforcus", "");
                    bundle.putString("longforcus", "");
                }
                    //Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    // Mở camera mặc định
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //phien ban vsmart
                    photoUri = getContentResolver().insert(EXTERNAL_CONTENT_URI, new ContentValues());
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    //End Vsmart

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, TAKE_PHOTO);
                    }

                } else {
                    Toast.makeText(getApplication(), "Camera không được hỗ trợ", Toast.LENGTH_LONG).show();
                }

            }
        });

        //phien ban vsmart
        if (savedInstanceState != null) {
            photoUri = (Uri) savedInstanceState.get(PHOTO_URI);
        }
        //End Vsmart
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toado = edttoado.getText().toString();
               if(!"".equals(toado)) {

                   if (common.coAnh(imgPhoto) == true) {
                       android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapThongTinKiemSoat.this);
                       b.setTitle("Cảnh báo");
                       b.setMessage("Bạn có chắc chắn muốn lưu thông tin kiểm soát?");
                       b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                               luuThongTinKiemSoat();

                           }
                       });
                       b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                           @Override

                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                           }
                       });
                       b.create().show();
                   } else {
                       android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapThongTinKiemSoat.this);
                       b.setTitle("Cảnh báo");
                       b.setMessage("Truyền về không có ảnh?");
                       b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                               luuThongTinKiemSoat();

                           }
                       });
                       b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                           @Override

                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                           }
                       });
                       b.create().show();
                   }
               }else {
                   android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapThongTinKiemSoat.this);
                   b.setTitle("Cảnh báo");
                   b.setMessage("Đối tượng chưa có tọa độ!");
                   b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();

                       }
                   });
                   b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                       @Override

                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                       }
                   });
                   b.create().show();
               }
            }
        });




    }

    private void khaibaobien() {

        tvtentuyen = (TextView) findViewById(R.id.tentuyen);

        edtmaho = (EditText) findViewById(R.id.edtmaho);
        edtloaiho = (EditText) findViewById(R.id.edtloaiho);
        edttenpho = (EditText) findViewById(R.id.edttenpho);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtngaybd = (EditText) findViewById(R.id.edtngaybd);
        edtngayks = (EditText) findViewById(R.id.edtngayks);
        edtghichu = (EditText) findViewById(R.id.edtghichu) ;

        btncamera = (ImageButton) findViewById(R.id.btncamera);
        btnLuu = (Button) findViewById(R.id.btnLuu);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgPhotoBd = (ImageView) findViewById(R.id.imgPhotoBd);
        chkkhongdat = (CheckBox) findViewById(R.id.chkkhongdat);
        btnCapNhatGPS = (Button) findViewById(R.id.btnCapNhatGPS);
        edttoado = (EditText) findViewById(R.id.edttoadogps);
        btnvitri = (Button) findViewById(R.id.btnvitri);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    public void luuThongTinKiemSoat(){

        if(chkkhongdat.isChecked()){
            Log.d("ảnh net", "...");
            //selectedBitmap = BitmapFactory.decodeStream(stream);
            ok_status = 0;
        }else {
            //selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 450);
        }

        String Inspect_Pic = holeData.getPeriod_Id()+"/"+"/"+holeData.getHole_Name();

        Integer Inspect_Status = 1;

        String Description = edtghichu.getText().toString();
        Integer Inspect_Count = holeData.getInspect_Count() + 1;
        /*SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        Date ngay_ks = null;
        Date ngay_bd = null;*/
        int isks = 1;
        if(common.coAnh(imgPhoto)==true) {

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            base_64_image = Base64.encodeToString(ba, Base64.DEFAULT);
        }else {
            base_64_image = "";
        }


        luuKiemSoat(holeData.getHole_Id() ,  Inspect_Pic , Inspect_Status, ok_status, Description, Inspect_Count, isks, holeData.Hole_Name, holeData.Period_Id, base_64_image );

    }


    private void updateToaDo(Integer Hole_ID, String Lat, String Long){
        UpdateToaDo request = new UpdateToaDo(Hole_ID,Lat,Long);
        BarcodeReaderApiManager.getInstance().accountApi().updateToaDo(Hole_ID,request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        if(null !=response.body()){
                            String result = response.body().string().toString();
                            if(result.equals("true")){
                                Toast.makeText(NhapThongTinKiemSoat.this, "Cập nhật tọa độ thành công!", Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(NhapThongTinKiemSoat.this, "Lỗi cập nhật tọa độ!", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(NhapThongTinKiemSoat.this, "Lỗi cập nhật tọa độ!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(NhapThongTinKiemSoat.this, "Sai tên đăng nhập hoặc mật khẩu cũ!", Toast.LENGTH_LONG).show();

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NhapThongTinKiemSoat.this, "Lỗi cập nhật", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void xacNhanTruyenAnh(){
        android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(NhapThongTinKiemSoat.this);
        b.setTitle("Cảnh báo");
        b.setMessage("Bạn có chắc chắn truyền ảnh về?");
        b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //truyenAnh();

            }
        });
        b.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        b.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == TAKE_PHOTO || requestCode == 200) && resultCode == Activity.RESULT_OK) {
            //Phiên bản vsmart
            try {
                InputStream stream = getContentResolver().openInputStream(photoUri);
                selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 450);
                if (chkkhongdat.isChecked()) {
                    ok_status = 0;
                }
                imgPhoto.setImageBitmap(selectedBitmap);
                getContentResolver().delete(photoUri, null, null);
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //End phien ban Vsmart

        }
    }






    private void initView() {
        common = new CommonText();
        Intent intent = getIntent();
        //final ArrayList<DuLieuKhachHang> arrTTCT = (ArrayList<DuLieuKhachHang>) intent.getSerializableExtra("arrlist");
        holeData = (HoleData) intent.getSerializableExtra("holeData");
        khaibaobien();
        displayInfoCustomer();

        //getKhaNangDongHo(ttkh.get);

    }


    private void displayInfoCustomer(){
        edtmaho.setText(String.valueOf(holeData.getHole_Name()));
        edtloaiho.setText(holeData.getHoleType_Name());
        edttenpho.setText(holeData.getStreet_Name());
        edtDiaChi.setText(holeData.getHole_Address());


        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        if(null!=holeData.getInspect_Day()) {
            edtngayks.setText(format2.format(holeData.getInspect_Day()).toString());
        }else {
            edtngayks.setText("");
        }



        if(null!=holeData.getMaintain_Day()) {
            edtngaybd.setText(format2.format(holeData.getMaintain_Day()).toString());
        }else {
            edtngaybd.setText("");
        }
        if(!"".equals(holeData.getHole_Latitude())&&!"".equals(holeData.getHole_Longitude())) {
            edttoado.setText(holeData.getHole_Latitude() + ", " + holeData.getHole_Longitude());
        }else {
            edttoado.setText("");
        }

        /*if(holeData.getOk_Status()==1){
            chkkhongdat.setChecked(false);
        }else {
            chkkhongdat.setChecked(true);
        }*/
        if("".equals(holeData.getInspect_Pic())||holeData.getInspect_Pic().equals("null")){
            //imgPhoto.setImageBitmap(null);
        }else {
            imgPhoto.setImageBitmap(common.getBitmapFromURL(holeData.getInspect_Pic()));
        }
        if("".equals(holeData.getMaintain_Pic())||holeData.getMaintain_Pic().equals("null")){
            imgPhotoBd.setImageBitmap(null);
        }else {
            imgPhotoBd.setImageBitmap(common.getBitmapFromURL(holeData.getMaintain_Pic()));
        }

        //spindktt.setSelection(ttkh.getMs_dk_ks());

    }
    private void luuKiemSoat(Integer Hole_Id ,  String Inspect_Pic , Integer Inspect_Status, Integer Ok_Status, String Description, Integer Inspect_Count,  Integer isks, String Hole_Name, Integer Period_Id, String base_64_str) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        NoiDungKiemSoat request = new NoiDungKiemSoat(Hole_Id ,  Inspect_Pic , Inspect_Status  , Ok_Status, Description, Inspect_Count, isks, Hole_Name, Period_Id, base_64_str);
        try {
            boolean rp = BarcodeReaderApiManager.getInstance().waterApi().luuKiemSoat(request).execute().isSuccessful();
            if(rp){
                Toast.makeText(getApplication(), "Cập nhật kiểm soát thành công!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplication(), "Cập nhật KHÔNG thành công!", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
        //return Bitmap.createBitmap(image, 0,0, width, height);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed(){
        if (isConnected()) {

            Intent intent = new Intent(NhapThongTinKiemSoat.this, TabActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(NhapThongTinKiemSoat.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }
}
