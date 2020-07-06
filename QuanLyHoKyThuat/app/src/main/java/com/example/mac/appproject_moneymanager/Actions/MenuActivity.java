package com.example.mac.appproject_moneymanager.Actions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.api.BarcodeReaderApiManager;
import com.example.mac.appproject_moneymanager.model.request.UpdateUserPwd;
import com.example.mac.appproject_moneymanager.utils.BaseActivity;
import com.example.mac.appproject_moneymanager.utils.CommonText;
import com.example.mac.appproject_moneymanager.utils.ReadJson;
import com.example.mac.appproject_moneymanager.utils.SharedPref;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    private static final String KEY_REMEMBER = "app_remember";
    private static final String KEY_PASS = "app_password";
    private static final String ID_USER = "ID_USER";
    private static final String NAME_USER = "NAME_USER";
    private static final String PASS_USER = "PASS_USER";
    private static final String USERTYPE_ID = "USERTYPE_ID";
    private static final String STATUS_USER = "STATUS_USER";
    private static final String PERIOD_ID = "PERIOD_ID";
    private static final String PERIOD_DATE = "PERIOD_DATE";
    ImageButton btnkiemsoat;
    ImageButton btnbaoduong;
    ImageButton btnmap;
    ImageButton btnchangepass;
    ImageButton btnlisthole;
    private Dialog dialog;

    EditText username;
    EditText password;
    EditText edttk;
    Button btnLogin;
    CheckBox rem_userpass;
    SharedPref config;
    String Period_Id="";
    String Period_Date="";
    Integer user_type = 0;
    CommonText common = new CommonText();

    EditText edtuserdialog;
    EditText edtpassword;
    EditText edtnewpassword;
    EditText edtrepeatpassword;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnkiemsoat = (ImageButton) findViewById(R.id.btnkiemsoat);
        btnbaoduong = (ImageButton) findViewById(R.id.btnbaoduong);
        btnmap = (ImageButton) findViewById(R.id.btnmap);
        btnchangepass = (ImageButton) findViewById(R.id.btnchangepass);
        edttk = (EditText) findViewById(R.id.edttk);
        btnlisthole = (ImageButton) findViewById(R.id.btnlisthole);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if(isConnected()){
            String url = common.URL_API + "/hkt/getmaxperiod";
            new HttpAsyncTaskGetMaxPeriod().execute(url);

        }else {
            Toast.makeText(MenuActivity.this, "Không có kết nối Internet!", Toast.LENGTH_LONG).show();

        }

        btnkiemsoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MenuActivity.this, TabActivity.class);
                startActivity(intent);*/
                user_type = 3;
                showDialogLogin();

            }
        });



        btnbaoduong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_type = 2;
                showDialogLogin();
            }
        });

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MenuActivity.this, MapActivity.class);
                startActivity(intent);
                //Intent intent = new Intent(NhapThongTinKiemSoat.this, MapActivity.class);
                //Create the bundle
                Bundle bundle = new Bundle();
                //Add your data from getFactualResults method to bundle
                bundle.putString("latforcus", "");
                bundle.putString("longforcus", "");
                //Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogChangePassword();
            }
        });
        btnlisthole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MenuActivity.this, DisplayListHole.class);
                startActivity(intent);
            }
        });


    }
    public void showDialogLogin() {

        dialog = new Dialog(MenuActivity.this);
        dialog.setTitle("Đăng Nhập");
        dialog.setContentView(R.layout.layout_login_from_menu);
        khaibaobien();
        //getthongtinhokythuatmaker(maker);
        dialog.show();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = "";
                String pass = "";



                if (isConnected()) {

                    if(!"Thời Kỳ: ".equals(edttk.getText().toString()) ) {
                        user = username.getText().toString();
                        pass = password.getText().toString();
                        if (!"".equals(user)) {
                            if (!"".equals(pass)) {
                                CheckLogin(user, pass);
                            } else {
                                Toast.makeText(MenuActivity.this, "Không để trống mật khẩu", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MenuActivity.this, "Không để trống tên đăng nhập", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(MenuActivity.this, "Không có kết nối Internet!", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(MenuActivity.this, "Không có kết nối internet", Toast.LENGTH_LONG).show();

                }

            }


        });

    }

    public void khaibaobien() {
        username = (EditText) dialog.findViewById(R.id.username);
        password = (EditText) dialog.findViewById(R.id.pass);
        btnLogin = (Button) dialog.findViewById(R.id.btnLogin);

        rem_userpass = (CheckBox) dialog.findViewById(R.id.checkBox);

        config = new SharedPref(this);
        if (config.getBoolean(KEY_REMEMBER, false)) {
            rem_userpass.setChecked(true);
        } else {
            rem_userpass.setChecked(false);
        }

        username.setText(config.getString(NAME_USER, ""));
        username.addTextChangedListener(MenuActivity.this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);




        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    username.setHint("");
                else
                    username.setHint("Tên đăng nhập");
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    password.setHint("");
                else
                    password.setHint("Mật khẩu");
            }
        });



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    private class HttpAsyncTaskGetMaxPeriod extends AsyncTask<String, JSONObject, Void> {

        ProgressDialog progressDialog = new ProgressDialog(MenuActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Vui lòng chờ trong giây lát...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String url = params[0];
            JSONObject objMaxPeriod;
            JSONArray jsonArray;

            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                objMaxPeriod = jsonArray.getJSONObject(0);
                Period_Id = objMaxPeriod.getString("period_id");
                Period_Date = objMaxPeriod.getString("period_date");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(Period_Date!=null && Period_Date.length()>8) {
                edttk.setText("Thời Kỳ: " + common.lay7kytudau(Period_Date));
            }else {
                Toast.makeText(MenuActivity.this, "Không có kết nối Internet!", Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();
            progressDialog.dismiss();
        }
    }

    public void CheckLogin(String msbd, String mat_khau) {
        if (isConnected()) {
            Integer Id = null;
            Integer UserType_Id= null;
            String Name = "";
            String Pass = "";
            String FullName= "";
            Integer Status = null;
            String UserType_Name = "";
            JSONObject objUser = null;
            JSONArray jsonArray = null;
            //CheckLogin?msbd=108&mat_khau=123456
            String url = common.URL_API + "/hkt/checklogin?name=" + msbd + "&pass=" + mat_khau ;


            try {
                jsonArray = ReadJson.readJSonArrayFromURL(url);
                if(jsonArray.length()>0) {
                    objUser = jsonArray.getJSONObject(0);
                    Id = objUser.getInt("Id");
                    UserType_Id = objUser.getInt("UserType_Id");
                    Name = objUser.getString("Name");
                    Pass = objUser.getString("Pass");
                    /*FullName = objUser.getString("FullName");
                    Status = objUser.getInt("Status");
                    UserType_Name = objUser.getString("UserType_Name");*/
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }



                if (null != objUser) {

                    if(UserType_Id.intValue()==user_type) {
                        config.clear();
                        config.remove(ID_USER);
                        config.remove(NAME_USER);
                        config.remove(PASS_USER);
                        config.remove(USERTYPE_ID);

                        config.putInt(ID_USER, Id);
                        config.putString(NAME_USER, Name);
                        config.putString(PASS_USER, Pass);
                        config.putInt(USERTYPE_ID, UserType_Id);
                        config.putString(PERIOD_ID, Period_Id);
                        config.putString(PERIOD_DATE, Period_Date);
                        config.commit();
                        if(UserType_Id==2) {
                            Intent intent = new Intent(MenuActivity.this, TabActivityBaoDuong.class);
                            startActivity(intent);
                        }else if(UserType_Id==3){
                            Intent intent = new Intent(MenuActivity.this, TabActivity.class);
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(MenuActivity.this, "Đăng Nhập Không Đúng Chức Năng", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(MenuActivity.this, "Sai tên đăng nhập hoặc mật khẩu.", Toast.LENGTH_LONG).show();
                }

        } else {
            Toast.makeText(this, "Không có kết nối internet", Toast.LENGTH_LONG).show();
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

    public void showDialogChangePassword() {
        ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.Theme_AppCompat_Light_Dialog);
        final AlertDialog.Builder dialogchangepass = new AlertDialog.Builder(ctw);

        dialogchangepass.setTitle("THAY ĐỔI MẬT KHẨU");
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.change_password, null);



        edtuserdialog = (EditText) layout.findViewById(R.id.edtuserdialog);
        edtpassword = (EditText) layout.findViewById(R.id.edtpassword);
        edtnewpassword = (EditText) layout.findViewById(R.id.edtnewpassword);
        edtrepeatpassword = (EditText) layout.findViewById(R.id.edtrepeatpassword);

        dialogchangepass.setView(layout);

        dialogchangepass.setPositiveButton("THAY ÐỔI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (isConnected()) {

                    if (!"".equals(edtuserdialog.getText().toString()) && !"".equals(edtpassword.getText().toString()) && !"".equals(edtnewpassword.getText().toString()) && !"".equals(edtrepeatpassword.getText().toString())) {
                        Log.d("edtpassword", edtpassword.getText().toString());

                        if (edtnewpassword.getText().toString().equals(edtrepeatpassword.getText().toString())) {
                            if (!edtpassword.getText().toString().equals(edtnewpassword.getText().toString())) {
                                String userId = "";
                                try {
                                    userId = edtuserdialog.getText().toString();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                updateUserPassword(userId, edtpassword.getText().toString(), edtnewpassword.getText().toString());
                                //UpdatePassword( "108",  "1234", "1") ;

                                //Toast.makeText(Login.this, "C?p nh?t m?t kh?u thành công!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MenuActivity.this, "Mật khẩu mới không được trùng mật khẩu cũ!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(MenuActivity.this, "Mật khẩu mới và Gõ lại mật khẩu phải trùng nhau!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(MenuActivity.this, "Mật khẩu không được để trống!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MenuActivity.this, "Không có kết nối internet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogchangepass.setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;
        dialogchangepass.show().getWindow().setLayout(displayWidth, (int) (displayHeight * 0.6));
    }

    private void updateUserPassword(String userId, String oldPwd, String newPwd){
        UpdateUserPwd request = new UpdateUserPwd(userId,oldPwd,newPwd);
        BarcodeReaderApiManager.getInstance().accountApi().updateUserPassword(""+userId,request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        if(null !=response.body()){
                            //Log.d("update", response.fbody().string().toString());
                            String result = response.body().string().toString();
                            if(result.equals("true")){
                                Toast.makeText(MenuActivity.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(MenuActivity.this, "Sai tên đăng nhập hoặc mật khẩu cũ!", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(MenuActivity.this, "Sai tên đăng nhập hoặc mật khẩu cũ!", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(MenuActivity.this, "Lỗi trong quá trình cập nhật mật khẩu!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
