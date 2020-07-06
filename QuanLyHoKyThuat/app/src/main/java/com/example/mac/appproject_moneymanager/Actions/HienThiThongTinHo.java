package com.example.mac.appproject_moneymanager.Actions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.model.Hole;
import com.example.mac.appproject_moneymanager.utils.BaseActivity;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.SharedPref;

public class HienThiThongTinHo extends BaseActivity {

    EditText edtmaho;
    EditText edtloaiho;
    EditText edttenpho;
    EditText edtDiaChi;
    EditText edtkt;
    EditText edttrangthai;
    EditText edtnvbd;
    EditText edtnvks;
    EditText edtghichu;
    EditText edttoado;
    Button btnvitri;

    CommonText common;
    SharedPref config;
    Hole hole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_thong_tin_ho);

        initView();
        btnvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HienThiThongTinHo.this, MapActivity.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data from getFactualResults method to bundle
                if(hole.getHole_Latitude()!=null&&hole.getHole_Latitude()!=null) {
                    bundle.putString("latforcus", hole.getHole_Latitude());
                    bundle.putString("longforcus", hole.getHole_Longitude());
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
        hole = (Hole) intent.getSerializableExtra("hole");
        khaibaobien();
        displayInfoHole();

    }

    private void khaibaobien() {

        edtmaho = (EditText) findViewById(R.id.edtmaho);
        edtloaiho = (EditText) findViewById(R.id.edtloaiho);
        edttenpho = (EditText) findViewById(R.id.edttenpho);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtkt = (EditText) findViewById(R.id.edtkt);
        edttrangthai = (EditText) findViewById(R.id.edttrangthai);
        edtnvbd = (EditText) findViewById(R.id.edtnvbd);
        edtnvks = (EditText) findViewById(R.id.edtnvks);
        edtghichu = (EditText) findViewById(R.id.edtghichu);
        edttoado = (EditText) findViewById(R.id.edttoado);
        btnvitri = (Button) findViewById(R.id.btnvitri);


    }

    private void displayInfoHole() {
        edtmaho.setText(String.valueOf(hole.getHole_Name()));
        edtloaiho.setText(hole.getHoleType_Name());
        edttenpho.setText(hole.getStreet_Name());
        edtDiaChi.setText(hole.getHole_Address());
        edtkt.setText(hole.getHoleSize_Name());
        edttrangthai.setText(hole.getHoleStatus_Name());
        edtnvbd.setText(hole.getMaintain_Name());
        edtnvks.setText(hole.getInspect_Name());
        edtghichu.setText(hole.getDescription());
        if (!"".equals(hole.getHole_Latitude()) && !"".equals(hole.getHole_Longitude())) {
            edttoado.setText(hole.getHole_Latitude() + ", " + hole.getHole_Longitude());
        } else {

        }

    }
}
