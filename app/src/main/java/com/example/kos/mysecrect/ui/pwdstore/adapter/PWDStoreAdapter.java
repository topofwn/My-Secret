package com.example.kos.mysecrect.ui.pwdstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.kos.mysecrect.data.model.DataPWD;

public class PWDStoreAdapter extends ArrayAdapter<DataPWD>{
    public PWDStoreAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public PWDStoreAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public PWDStoreAdapter(@NonNull Context context, int resource, @NonNull DataPWD[] objects) {
        super(context, resource, objects);
    }
}
