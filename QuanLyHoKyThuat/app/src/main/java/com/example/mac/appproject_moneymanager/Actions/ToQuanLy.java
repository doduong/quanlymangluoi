package com.example.mac.appproject_moneymanager.Actions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.Adapter.AdapterStreet;
import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.model.Street;
import com.example.mac.appproject_moneymanager.model.ToQuanLyModel;
import com.example.mac.appproject_moneymanager.utils.BaseActivity;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.ReadJson;
import com.example.mac.appproject_moneymanager.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class ToQuanLy extends BaseActivity {

    private static final String STREET_ID = "STREET_ID";
    private static final String STREET_NAME = "STREET_NAME";

    TextView lblheader;
    ListView lsttql;
    Connection conn;
    TextView tbltentql;
    String ms_bd;
    String ms_tk = "";
    SharedPref config;
    ProgressDialog progress;
    private Activity ac;
    ArrayList<Street> liststreet;
    AdapterStreet adapterStreet;
    CommonText common = new CommonText();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_quan_ly);

        lblheader = (TextView) findViewById(R.id.lblheader);
        lsttql = (ListView) findViewById(R.id.lsttql);
        tbltentql = (TextView) findViewById(R.id.lbltentql);
        config = new SharedPref(this);
        String ms_userthanhtra = config.getString("ms_userthanhtra", "");

        if (isConnected()) {
            String url = common.URL_API + "/hkt/getstreets";
            new HttpAsyncTaskGetTQL().execute(url);


            lsttql.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (isConnected()) {
                        Street objtql = liststreet.get(i);
                        config.putInt(STREET_ID, objtql.getStreet_Id());
                        config.putString(STREET_NAME, String.valueOf(objtql.getStreet_Name()));
                        config.commit();

                        Intent intent = new Intent(ToQuanLy.this, TabActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ToQuanLy.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            Toast.makeText(ToQuanLy.this, "Chưa kết nối internet", Toast.LENGTH_LONG).show();
        }


    }

    private class HttpAsyncTaskGetTQL extends AsyncTask<String, JSONObject, Void> {
        ProgressDialog progressDialog = new ProgressDialog(ToQuanLy.this);

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            Integer streetid = null ;
            Integer streetroute = null;
            String Street_Name ="" ;
            String Descristion = "";


            JSONArray lstjson;
            try {
                liststreet = new ArrayList<>();
                lstjson = ReadJson.readJSonArrayFromURL(url);
                for (int i = 0; i < lstjson.length(); i++) {
                    Street street;
                    JSONObject objCN = lstjson.getJSONObject(i);
                    streetid = objCN.getInt("Street_Id");
                    streetroute = objCN.getInt("Street_Route");
                    Street_Name = objCN.getString("Street_Name");
                    Descristion = objCN.getString("Descristion");
                    street = new Street(streetid, streetroute, Street_Name, Descristion);
                    liststreet.add(street);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapterStreet = new AdapterStreet(ToQuanLy.this, liststreet);
            lsttql.setAdapter(adapterStreet);
            adapterStreet.notifyDataSetChanged();
            super.onPostExecute(result);
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
                myOwnBackPress();
            }
        }, 500);
    }

    private void myOwnBackPress() {
        if (!isFinishing()) {
            //super.onBackPressed();
            Intent intent = new Intent(ToQuanLy.this, MenuActivity.class);
            startActivity(intent);
        }
    }
}
