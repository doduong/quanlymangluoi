package com.example.mac.appproject_moneymanager.Actions;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.mac.appproject_moneymanager.model.NoiDungBaoDuong;
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

public class HienThiThongTinBaoDuong extends BaseActivity {
    EditText edtmaho;
    EditText edtloaiho;
    EditText edttenpho;
    EditText edtDiaChi;
    EditText edtngayks;
    EditText edtngaybd;
    EditText edtghichu;
    HoleData holeData;
    CheckBox chkkhongdat;
    Button btnLuu;
    Button btnvitri;
    EditText edttoado;

    ImageButton btncamera;
    ImageView imgPhoto;
    ImageView imgPhotoBd;
    CommonText common;
    SharedPref config;
    String ms_userthanhtra = "";
    String base_64_image;
    int ok_status = 1;


    List<Bitmap> bitmaps = null;
    ThongTinKiemSoat ttks;
    String[] listStrImages = null;
    Bitmap selectedBitmap;
    private Uri photoUri;
    private final static int TAKE_PHOTO = 100;
    private final static String PHOTO_URI = "photoUri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_thong_tin_bao_duong);
        config = new SharedPref(HienThiThongTinBaoDuong.this);
        ms_userthanhtra = config.getString("ms_userthanhtra", "");
        initView();

        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(HienThiThongTinBaoDuong.this);
                b.setTitle("Cảnh báo");
                b.setMessage("Bạn có chắc chắn muốn lưu thông tin bảo dưỡng?");
                b.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        luuThongTinBaoDuong();

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

        btnvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HienThiThongTinBaoDuong.this, MapActivity.class);
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

        //phien ban vsmart
        if (savedInstanceState != null) {
            photoUri = (Uri) savedInstanceState.get(PHOTO_URI);
        }


    }


    private void initView() {
        common = new CommonText();
        Intent intent = getIntent();
        //final ArrayList<DuLieuKhachHang> arrTTCT = (ArrayList<DuLieuKhachHang>) intent.getSerializableExtra("arrlist");
        holeData = (HoleData) intent.getSerializableExtra("holeData");
        khaibaobien();
        displayInfoCustomer();

    }

    private void displayInfoCustomer() {


        if (isConnected()) {

            String url = common.URL_API + "/hkt/getholedatabyid?hole_id=" + holeData.getHole_Id();
            new HttpAsyncTaskGetHoleDataById().execute(url);

        } else {

            Toast.makeText(HienThiThongTinBaoDuong.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }


    }


    private void khaibaobien() {

        edtmaho = (EditText) findViewById(R.id.edtmaho);
        edtloaiho = (EditText) findViewById(R.id.edtloaiho);
        edttenpho = (EditText) findViewById(R.id.edttenpho);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtngaybd = (EditText) findViewById(R.id.edtngaybd);
        edtngayks = (EditText) findViewById(R.id.edtngayks);
        edtghichu = (EditText) findViewById(R.id.edtghichu);
        chkkhongdat = (CheckBox) findViewById(R.id.chkkhongdat);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgPhotoBd = (ImageView) findViewById(R.id.imgPhotoBd);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btncamera = (ImageButton) findViewById(R.id.btncamera);
        btnvitri = (Button) findViewById(R.id.btnvitri);
        edttoado = (EditText) findViewById(R.id.edttoado);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == TAKE_PHOTO || requestCode == 200) && resultCode == Activity.RESULT_OK) {
            //Phiên bản vsmart
            try {
                InputStream stream = getContentResolver().openInputStream(photoUri);
                selectedBitmap = getResizedBitmap(BitmapFactory.decodeStream(stream), 450);
                imgPhotoBd.setImageBitmap(selectedBitmap);
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

    public void luuThongTinBaoDuong() {
        String Maintain_Pic = "";

        Integer Maintain_Status = 1;

        String Description = edtghichu.getText().toString();
        if (common.coAnh(imgPhotoBd) == true) {

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            byte[] ba = bao.toByteArray();
            base_64_image = Base64.encodeToString(ba, Base64.DEFAULT);
        } else {
            base_64_image = "";
        }


        luuBaoDuong(holeData.getHole_Id(), Maintain_Pic, Maintain_Status, Description, holeData.Hole_Name, holeData.Period_Id, base_64_image);

    }


    private void luuBaoDuong(Integer Hole_Id, String Maintain_Pic, Integer Maintain_Status, String Description, String Hole_Name, Integer Period_Id, String base_64_str) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        NoiDungBaoDuong request = new NoiDungBaoDuong(Hole_Id, Maintain_Pic, Maintain_Status, Description, Hole_Name, Period_Id, base_64_str);
        try {
            boolean rp = BarcodeReaderApiManager.getInstance().waterApi().luuBaoDuong(request).execute().isSuccessful();
            if (rp) {
                Toast.makeText(getApplication(), "Cập Nhật Bảo Dưỡng Thành Công!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(), "Cập Nhật KHÔNG Thành Công!", Toast.LENGTH_LONG).show();
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
            if (null != holeDataFirst.getInspect_Day()) {
                edtngayks.setText(common.cat10kitucuoi(format2.format(holeDataFirst.getInspect_Day())));
            } else {
                edtngayks.setText("");
            }
            if (null != holeDataFirst.getMaintain_Day()) {
                edtngaybd.setText(common.cat10kitucuoi(format2.format(holeDataFirst.getMaintain_Day())));
            } else {
                edtngaybd.setText("");
            }
            if(!"".equals(holeDataFirst.getHole_Latitude())&&!"".equals(holeDataFirst.getHole_Longitude())) {
                edttoado.setText(holeDataFirst.getHole_Latitude() + ", " + holeDataFirst.getHole_Longitude());
            }else {
                edttoado.setText("");
            }
            /*if (holeDataFirst.getOk_Status() == 1) {
                chkkhongdat.setChecked(false);
            } else {
                chkkhongdat.setChecked(true);
            }*/
            if ("".equals(holeDataFirst.getInspect_Pic()) || holeDataFirst.getInspect_Pic().equals("null")) {
                //imgPhoto.setImageBitmap(null);
            } else {
                imgPhoto.setImageBitmap(common.getBitmapFromURL(holeDataFirst.getInspect_Pic()));
            }
            if ("".equals(holeDataFirst.getMaintain_Pic()) || holeDataFirst.getMaintain_Pic().equals("null")) {
                //imgPhotoBd.setImageBitmap(null);
            } else {
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
    public void onBackPressed() {
        if (isConnected()) {
            Intent intent = new Intent(HienThiThongTinBaoDuong.this, TabActivityBaoDuong.class);
            startActivity(intent);
        } else {
            Toast.makeText(HienThiThongTinBaoDuong.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }
}
