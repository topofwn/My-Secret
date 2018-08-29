package com.example.kos.mysecrect.ui.pwdstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;

import java.util.List;

public class PWDStoreAdapter extends ArrayAdapter<DataPWD>{
    private List<DataPWD> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public PWDStoreAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public PWDStoreAdapter( Context context,int resourceId,List<DataPWD> data) {
        super(context,resourceId);
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mData = data;    }


    private class ViewHolder {
        TextView name;

    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_data, null);
            holder.name = view.findViewById(R.id.txtKeyName);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
            DataPWD myData = mData.get(position);
        holder.name.setText(myData.getFieldName());
        return view;
    }

    public DataPWD getData(int pos) {
        if (pos > -1 && pos < mData.size()) {
            return mData.get(pos);
        }
        return null;
    }


    public void updateData(List<DataPWD> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
    public void set(List<DataPWD> setting) {
        mData.clear();
        mData.addAll(setting);
        notifyDataSetChanged();
    }

    public void remove(DataPWD object) {
        mData.remove(object);
        notifyDataSetChanged();
    }




    @Nullable
    @Override
    public DataPWD getItem(int position) {
        if (position < mData.size()) {
            return this.mData.get(position);
        }
        return null;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

}
