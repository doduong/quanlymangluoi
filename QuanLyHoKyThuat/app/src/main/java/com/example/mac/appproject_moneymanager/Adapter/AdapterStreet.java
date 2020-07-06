package com.example.mac.appproject_moneymanager.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.model.Street;
import com.example.mac.appproject_moneymanager.model.ToQuanLyModel;

import java.util.ArrayList;

public class AdapterStreet extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Street> lststreet;
    private LayoutInflater mInflater = null;
    private Street tempValue = null;

    public AdapterStreet(Activity a, ArrayList<Street> lststreet) {
        super();
        this.mActivity = a;
        this.lststreet = lststreet;
    }


    @Override
    public int getCount() {
        Log.d("lsttuyendoc", String.valueOf(lststreet.size()));
        return lststreet.size();
    }

    @Override
    public Object getItem(int i) {
        return lststreet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        public TextView tvtql;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater)mActivity.getLayoutInflater();
        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.activity_adapter_street,null);
            holder = new ViewHolder();
            holder.tvtql = view.findViewById(R.id.tvstreet);
            view.setTag(holder);
        }else {
            holder = (AdapterStreet.ViewHolder) view.getTag();
        }
        try {
            if(lststreet.size()<=0){
                holder.tvtql.setText("NoData");

            }else  {
                tempValue = null;
                tempValue = (Street) lststreet.get(i);
                //Log.d("ms_tuyen: ", String.valueOf(tempValue.getMs_tuyen())+ " --- mo_ta_tuyen: " + tempValue.getMo_ta_tuyen() );

                holder.tvtql.setBackgroundColor(Color.WHITE);
                holder.tvtql.setText(String.valueOf(tempValue.getStreet_Route())+ ": "+ tempValue.getStreet_Name());

            }
        }catch (Exception ex) {

            ex.printStackTrace();
        }

        return view;
    }
}
