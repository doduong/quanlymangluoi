package com.example.mac.appproject_moneymanager.Actions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.Adapter.AdapterHole;
import com.example.mac.appproject_moneymanager.Adapter.AdapterHoleData;
import com.example.mac.appproject_moneymanager.Adapter.Tab1;
import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.model.Hole;
import com.example.mac.appproject_moneymanager.model.HoleData;
import com.example.mac.appproject_moneymanager.model.Street;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.ReadJson;
import com.example.mac.appproject_moneymanager.utils.SharedPref;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayListHole extends AppCompatActivity {
    Spinner spinstreet;
    EditText edtdanhba;
    EditText edtSearchName;
    TextView tvslcks;
    Spinner spidstimkiem;

    Button btntimkiem;
    Button btnquetmavach;
    ListView lstpro;
    CommonText common;
    int positionloaitk = 0;
    ArrayList<Hole> lsthole = new ArrayList<>();
    private AdapterHole adapter;
    String keysearch = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_hole);
        initView();


    }
    public void initView() {
        common = new CommonText();
        lstpro = (ListView) findViewById(R.id.lstpro);
        edtdanhba = (EditText) findViewById(R.id.edtmaho);
        spinstreet = (Spinner) findViewById(R.id.spinstreet);
        edtSearchName = (EditText) findViewById(R.id.edtsearchname);
        spidstimkiem = (Spinner) findViewById(R.id.spidstimkiem);

        btntimkiem = (Button) findViewById(R.id.btnsearch);
        btnquetmavach = (Button) findViewById(R.id.btnquetmavach);
        tvslcks = (TextView) findViewById(R.id.tvsldaks);
        loadSpinerStreet();
        ArrayList<String> listTT1 = new ArrayList<String>();
        listTT1.add("Đường");
        listTT1.add("Tên Hố");
        listTT1.add("Địa chỉ");
        listTT1.add("Loại Hố");
        listTT1.add("Mã Hố");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(DisplayListHole.this, R.layout.spinner_item, listTT1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spidstimkiem.setAdapter(dataAdapter1);
        spidstimkiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionloaitk = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //getThongTinHoKyThuat();


        btntimkiem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                keysearch = edtSearchName.getText().toString();
                searchUsePointNotControl(keysearch, positionloaitk);
            }


        });
        btnquetmavach.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(DisplayListHole.this);
                integrator.setBeepEnabled(false);
                //integrator.forSupportFragment(getApplicationContext()).setCaptureActivity(ScannerActivity.class).initiateScan();
                integrator.setCaptureActivity(ScannerActivity.class).initiateScan();

            }


        });

        lstpro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isConnected()) {
                    if (lsthole.size() > 0) {
                        Hole hole = lsthole.get(i);
                        Intent intent = new Intent(DisplayListHole.this, HienThiThongTinHo.class);
                        //intent.putExtra("arrlist", listkh);
                        intent.putExtra("hole", hole);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DisplayListHole.this, "Không có dữ liệu kiểm soát!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(DisplayListHole.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void loadSpinerStreet() {
        setSpinnerToQuanLy();
        spinstreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Street street = (Street) spinstreet.getSelectedItem();
                if (street.getStreet_Name().equals("Tất cả")) {
                    searchUsePointNotControl("", 0);
                } else {
                    searchUsePointNotControl(street.getStreet_Name(), 0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setSpinnerToQuanLy() {
        String url = common.URL_API + "/hkt/getstreets";

        List<Street> lststreet = new ArrayList<>();
        JSONArray lstjson;
        try {
            lstjson = ReadJson.readJSonArrayFromURL(url);
            Street streetfirst = new Street(0, 0, "Tất cả", "");
            lststreet.add(streetfirst);
            for (int i = 0; i < lstjson.length(); i++) {
                Street street;
                JSONObject objCN = lstjson.getJSONObject(i);
                Integer Street_Id = objCN.getInt("Street_Id");
                Integer Street_Route = objCN.getInt("Street_Route");
                String Street_Name = objCN.getString("Street_Name");
                String Description = objCN.getString("Street_Name");
                street = new Street(Street_Id, Street_Route, Street_Name, Description);
                lststreet.add(street);
            }

            ArrayAdapter<Street> adapter = new ArrayAdapter<Street>(DisplayListHole.this, R.layout.spinner_item, lststreet);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinstreet.setAdapter(adapter);
            //ToQuanLy tql = (ToQuanLy) spitoquanly.getSelectedItem();
            //getThongTinDiemDung(tql.getMs_tql());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //End Tìm kiếm
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getApplicationContext().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(DisplayListHole.this, "Scan được hủy bỏ", Toast.LENGTH_LONG).show();
            } else {
                Log.d("showResultDialogue:", result.getContents());
                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    String ms_db = "";


    public void showResultDialogue(final String resultscan) {


        //ms_db = common.strimBarcode(resultscan);
        if (isConnected()) {
            searchUsePointNotControl(resultscan, 4);
        } else {
            Toast.makeText(DisplayListHole.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }
    }
    public void getThongTinHoKyThuat() {

        if (isConnected()) {

            String url = common.URL_API + "/hkt/getholes";
            Log.d("URL", url);
            new HttpAsyncTaskGetHoleOfStreet().execute(url);

        } else {

            Toast.makeText(DisplayListHole.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }

    }

    private class HttpAsyncTaskGetHoleOfStreet extends AsyncTask<String, JSONObject, Void> {

        ProgressDialog progressDialog = new ProgressDialog(DisplayListHole.this);
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Nạp dữ liệu...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArrayKH;
            lsthole = new ArrayList<>();
            //get list HoleData from url
            common.getListHole(url, lsthole);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new AdapterHole(DisplayListHole.this, lsthole);
            adapter.notifyDataSetChanged();
            lstpro.setAdapter(adapter);
            lstpro.invalidateViews();
            lstpro.refreshDrawableState();
            tvslcks.setText("Số lượng: " + lsthole.size());
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    public void searchUsePointNotControl(String keysearch, int conditionfield) {
        String field = "";
        String keywrord = "keyWord=" + keysearch;
        if (conditionfield == 0) {
            field = "&conditionField=pho";
        } else if (conditionfield == 1) {
            field = "&conditionField=ma";
        } else if (conditionfield == 2) {
            field = "&conditionField=diachi";
        } else if (conditionfield == 3) {
            field = "&conditionField=loai";
        } else if (conditionfield == 4) {
            field = "&conditionField=hole_id";
        }
        if (!"".equals(keysearch)) {
            if (isConnected()) {
                //SearchUsePointWereRead?ms_tuyen=5115&ms_tk=247&keyWord=%22%22&conditionField=customer'
                String url = common.URL_API + "/hkt/searchholes?" + keywrord + field ;
                Log.d("SearchUsePointNotRead", url);
                new HttpAsyncTaskGetHoleOfStreet().execute(url);
            } else {
                Toast.makeText(DisplayListHole.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
            }
        } else {
            getThongTinHoKyThuat();
        }

    }







}
