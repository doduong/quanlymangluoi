package com.example.mac.appproject_moneymanager.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mac.appproject_moneymanager.R;
import com.example.mac.appproject_moneymanager.model.HoleData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterHoleDataNotOk extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<HoleData> lstholedata;
    private HoleData tempValue = null;
    int stt = 0;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");



    public AdapterHoleDataNotOk(Activity a, ArrayList<HoleData> d) {
        this.mActivity = a;
        this.lstholedata = d;
        stt = 0;
        // mInflater = (LayoutInflater)mActivity.getSystemService(
        //Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (lstholedata.size() <= 0) {
            return 1;
        }
        return lstholedata.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        private TextView tvhole_route;
        private TextView tvStreet_Name;
        private TextView tvhole_name;
        private TextView tvhole_address;
        private TextView tvInspect_Day;
        private TextView tvMaintain_Day;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //int stt = 0;

        LayoutInflater mInflater = (LayoutInflater) mActivity.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        View vi = view;
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            vi = mInflater.inflate(R.layout.item_tab2_column, viewGroup, false);
            vi.requestFocus();
            vi.setMinimumHeight(50);
            holder.tvhole_route = vi.findViewById(R.id.tvhole_route);
            holder.tvStreet_Name = vi.findViewById(R.id.tvStreet_Name);
            holder.tvhole_name = vi.findViewById(R.id.tvhole_name);
            holder.tvhole_address = vi.findViewById(R.id.tvhole_address);
            holder.tvInspect_Day = vi.findViewById(R.id.tvInspect_Day);
            holder.tvMaintain_Day = vi.findViewById(R.id.tvMaintain_Day);
            vi.setTag(holder);
        }
        //else {
        holder = (ViewHolder) vi.getTag();
        //holder = new ViewHolder();
        try {
            if (lstholedata.size() <= 0) {
                //holder.tvMaKH.setText("");
                //holder.tvTenKH.setText("");

            } else {
                tempValue = null;
                tempValue = (HoleData) lstholedata.get(i);
                Date ngay_loc = null;
                Date ngay_hien_tai = null;
                int difference = 0;
                holder.tvhole_route.setBackgroundColor(Color.WHITE);
                holder.tvStreet_Name.setBackgroundColor(Color.WHITE);
                holder.tvhole_name.setBackgroundColor(Color.WHITE);
                holder.tvhole_address.setBackgroundColor(Color.WHITE);
                holder.tvInspect_Day.setBackgroundColor(Color.WHITE);
                holder.tvMaintain_Day.setBackgroundColor(Color.WHITE);


                holder.tvhole_route.setText(String.valueOf(tempValue.getHole_Route()));
                holder.tvStreet_Name.setText(String.valueOf(tempValue.getStreet_Name()));
                holder.tvhole_name.setText(String.valueOf(tempValue.getHole_Name()));
                holder.tvhole_address.setText(tempValue.getHole_Address());
                holder.tvInspect_Day.setText(String.valueOf(tempValue.getHoleType_Name()));
                holder.tvMaintain_Day.setText(String.valueOf(tempValue.getHoleSize_Name()));



            }


        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return vi;
    }
}


