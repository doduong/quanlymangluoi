package com.example.mac.appproject_moneymanager.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.Actions.NhapThongTinKiemSoat;
import com.example.mac.appproject_moneymanager.Actions.ScannerActivity;
import com.example.mac.appproject_moneymanager.R;
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

public class Tab1 extends Fragment {

    private View mRootView;
    Spinner spinstreet;
    EditText edtdanhba;
    EditText edtSearchName;
    TextView tvslcks;
    Spinner spidstimkiem;

    Button btntimkiem;
    Button btnquetmavach;
    ListView lstpro;
    CommonText common;
    SharedPref config;
    Integer User_ID;
    String ms_tk = "";
    int positionloaitk = 0;
    String ms_tql = "";
    ArrayList<HoleData> lstholedata = new ArrayList<>();
    private AdapterHoleData adapter;
    String keysearch = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_kiem_soat, container, false);
        config = new SharedPref(getActivity());
        User_ID = config.getInt("ID_USER", 0);


        initView();
        return mRootView;
    }

    public void initView() {
        common = new CommonText();
        lstpro = (ListView) mRootView.findViewById(R.id.lstpro);
        edtdanhba = (EditText) mRootView.findViewById(R.id.edtmaho);
        spinstreet = (Spinner) mRootView.findViewById(R.id.spinstreet);
        edtSearchName = (EditText) mRootView.findViewById(R.id.edtsearchname);
        spidstimkiem = (Spinner) mRootView.findViewById(R.id.spidstimkiem);

        btntimkiem = (Button) mRootView.findViewById(R.id.btnsearch);
        btnquetmavach = (Button) mRootView.findViewById(R.id.btnquetmavach);
        tvslcks = (TextView) mRootView.findViewById(R.id.tvsldaks);
        loadSpinerStreet();
        //Spinner tìm kiếm danh mục
        ArrayList<String> listTT1 = new ArrayList<String>();
        listTT1.add("Đường");
        listTT1.add("Tên Hố");
        listTT1.add("Địa chỉ");
        listTT1.add("Loại Hố");
        listTT1.add("Mã Hố");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, listTT1);
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
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setBeepEnabled(false);
                integrator.forSupportFragment(Tab1.this).setCaptureActivity(ScannerActivity.class).initiateScan();

            }


        });

        lstpro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isConnected()) {
                    if (lstholedata.size() > 0) {
                        HoleData holeData = lstholedata.get(i);
                        Intent intent = new Intent(getActivity(), NhapThongTinKiemSoat.class);
                        //intent.putExtra("arrlist", listkh);
                        intent.putExtra("holeData", holeData);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Không có dữ liệu kiểm soát!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Scan được hủy bỏ", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }
    }


    public void getThongTinHoKyThuat() {

        if (isConnected()) {

            String url = common.URL_API + "/hkt/getholedatainspectnotyet?user_id=" + User_ID;
            Log.d("URL", url);
            new HttpAsyncTaskGetHoleOfStreet().execute(url);

        } else {

            Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }


    }

    /*
     * Funtions: Tìm kiếm : Đường, Mã Hố, Địa Chỉ
     * Điều kiện Hố Chưa kiểm soát: Inspect_Status= 0;
     * */
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
                String url = common.URL_API + "/hkt/timkiemholedata?" + keywrord + field + "&user_id=" + User_ID;
                Log.d("SearchUsePointNotRead", url);
                new HttpAsyncTaskGetHoleOfStreet().execute(url);
            } else {
                Toast.makeText(getActivity(), "Chưa kết nối internet", Toast.LENGTH_LONG).show();
            }
        } else {
            getThongTinHoKyThuat();
        }

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

            ArrayAdapter<Street> adapter = new ArrayAdapter<Street>(getActivity(), R.layout.spinner_item, lststreet);
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


    private class HttpAsyncTaskGetHoleOfStreet extends AsyncTask<String, JSONObject, Void> {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Nạp dữ liệu...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONArray jsonArrayKH;
            lstholedata = new ArrayList<>();
            //get list HoleData from url
            common.getListHoleData(url, lstholedata);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter = new AdapterHoleData(getActivity(), lstholedata);
            adapter.notifyDataSetChanged();
            lstpro.setAdapter(adapter);
            lstpro.invalidateViews();
            lstpro.refreshDrawableState();
            tvslcks.setText("Số lượng: " + lstholedata.size());
            progressDialog.hide();
            progressDialog.dismiss();

        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


}