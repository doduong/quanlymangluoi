package com.example.mac.appproject_moneymanager.Actions;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.api.BarcodeReaderApiManager;
import com.example.mac.appproject_moneymanager.model.HoleData;
import com.example.mac.appproject_moneymanager.model.NoiDungKiemSoat;
import com.example.mac.appproject_moneymanager.model.ThongTinKiemSoat;
import com.example.mac.appproject_moneymanager.utils.BaseActivity;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.ReadJson;
import com.example.mac.appproject_moneymanager.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

public class HienThiThongTinKhongDat extends BaseActivity {
    EditText edtmaho;
    EditText edtloaiho;
    EditText edttenpho;
    EditText edtDiaChi;
    EditText edtngayks;
    EditText edtngaybd;
    EditText edtghichu;
    EditText edttoado;
    HoleData holeData;
    CheckBox chkkhongdat;
    Button btnLuu;
    ImageButton btncamera;
    Button btnvitri;


    ImageView imgPhoto;
    ImageView imgPhotoBd;
    CommonText common;
    SharedPref config;
    Integer user_type_id=0;
    String base_64_image;
    int ok_status = 1;
    Bitmap selectedBitmap;
    private Uri photoUri;
    private final static int TAKE_PHOTO = 100;
    private final static String PHOTO_URI = "photoUri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_thong_tin_khong_dat);
        config = new SharedPref(HienThiThongTinKhongDat.this);
        user_type_id = config.getInt("USERTYPE_ID", 0);
        initView();

        btnLuu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(HienThiThongTinKhongDat.this);
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

        btnvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HienThiThongTinKhongDat.this, MapActivity.class);
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


    }

    private void initView() {
        common = new CommonText();
        Intent intent = getIntent();
        //final ArrayList<DuLieuKhachHang> arrTTCT = (ArrayList<DuLieuKhachHang>) intent.getSerializableExtra("arrlist");
        holeData = (HoleData) intent.getSerializableExtra("holeData");
        khaibaobien();

        if(user_type_id== 2){
            btnLuu.setEnabled(false);
            btnLuu.setBackgroundColor(Color.GRAY);
        }

        displayInfoCustomer();

    }

    private void displayInfoCustomer(){


        if (isConnected()) {

            String url = common.URL_API + "/hkt/getholedatabyid?hole_id=" + holeData.getHole_Id();
            new HttpAsyncTaskGetHoleDataById().execute(url);

        } else {

            Toast.makeText(HienThiThongTinKhongDat.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }



    }


    private void khaibaobien() {

        edtmaho = (EditText) findViewById(R.id.edtmaho);
        edtloaiho = (EditText) findViewById(R.id.edtloaiho);
        edttenpho = (EditText) findViewById(R.id.edttenpho);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtngaybd = (EditText) findViewById(R.id.edtngaybd);
        edtngayks = (EditText) findViewById(R.id.edtngayks);
        edtghichu = (EditText) findViewById(R.id.edtghichu) ;
        chkkhongdat = (CheckBox) findViewById(R.id.chkkhongdat);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgPhotoBd = (ImageView) findViewById(R.id.imgPhotoBd);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btncamera = (ImageButton) findViewById(R.id.btncamera);
        edttoado = (EditText) findViewById(R.id.edttoado);
        btnvitri = (Button) findViewById(R.id.btnvitri);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if ((requestCode == TAKE_PHOTO || requestCode == 200) && resultCode == Activity.RESULT_OK) {
            //Phiên bản vsmart
            try {
                InputStream stream = getContentResolver().openInputStream(photoUri);
                selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 450);
                /*if(chkkhongdat.isChecked()){
                    Log.d("ảnh net", "...");
                    //selectedBitmap = BitmapFactory.decodeStream(stream);
                    ok_status = 0;
                }else {
                    //selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 450);
                }*/
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

    public void luuThongTinKiemSoat(){

        if(chkkhongdat.isChecked()){
            Log.d("ảnh net", "...");
            //selectedBitmap = BitmapFactory.decodeStream(stream);
            ok_status = 0;
        }else {
            //selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 450);
        }

        String Inspect_Pic ="";

        Integer Inspect_Status = 1;

        String Description = edtghichu.getText().toString();
        Integer Inspect_Count = holeData.Inspect_Count + 1;
        int isks = 1;
        if(common.coAnh(imgPhoto)==true) {

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            BitmapDrawable drawable = (BitmapDrawable) imgPhoto.getDrawable();
            selectedBitmap = drawable.getBitmap();
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            base_64_image = Base64.encodeToString(ba, Base64.DEFAULT);
        }else {
            base_64_image = "";
        }


        luuKiemSoat(holeData.getHole_Id() ,  Inspect_Pic , Inspect_Status, ok_status, Description, Inspect_Count, isks, holeData.Hole_Name, holeData.Period_Id, base_64_image );

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



    private class HttpAsyncTaskGetHoleDataById extends AsyncTask<String, JSONObject, Void> {
        HoleData holeDataFirst;
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            holeDataFirst = common.getListHoleDataById(url);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            edtmaho.setText(String.valueOf(holeDataFirst.getHole_Name()));
            edtloaiho.setText(holeDataFirst.getHoleType_Name());
            edttenpho.setText(holeDataFirst.getStreet_Name());
            edtDiaChi.setText(holeDataFirst.getHole_Address());
            edtghichu.setText(holeDataFirst.getDescription_holedata());
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            if(null!=holeDataFirst.getInspect_Day()) {
                edtngayks.setText(common.cat10kitucuoi(format2.format(holeDataFirst.getInspect_Day())));
            }else {
                edtngayks.setText("");
            }
            if(null!=holeDataFirst.getMaintain_Day()) {
                edtngaybd.setText(common.cat10kitucuoi(format2.format(holeDataFirst.getMaintain_Day())));
            }else {
                edtngaybd.setText("");
            }
            if(holeDataFirst.getOk_Status()==1){
                chkkhongdat.setChecked(false);
            }else {
                chkkhongdat.setChecked(true);
            }
            if(!"".equals(holeDataFirst.getHole_Latitude())&&!"".equals(holeDataFirst.getHole_Longitude())) {
                edttoado.setText(holeDataFirst.getHole_Latitude() + ", " + holeDataFirst.getHole_Longitude());
            }else {
                edttoado.setText("");
            }
            if("".equals(holeDataFirst.getInspect_Pic())||holeDataFirst.getInspect_Pic().equals("null")){
                imgPhoto.setImageBitmap(null);
            }else {
                imgPhoto.setImageBitmap(common.getBitmapFromURL(holeDataFirst.getInspect_Pic()));
            }
            if("".equals(holeDataFirst.getMaintain_Pic())||holeDataFirst.getMaintain_Pic().equals("null")){
                imgPhotoBd.setImageBitmap(null);
            }else {
                imgPhotoBd.setImageBitmap(common.getBitmapFromURL(holeDataFirst.getMaintain_Pic()));
            }

        }


    }



    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed(){
        if (isConnected()) {
            if(user_type_id==3) {
                Intent intent = new Intent(HienThiThongTinKhongDat.this, TabActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(HienThiThongTinKhongDat.this, TabActivityBaoDuong.class);
                startActivity(intent);
            }
        } else {
            Toast.makeText(HienThiThongTinKhongDat.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }
}
