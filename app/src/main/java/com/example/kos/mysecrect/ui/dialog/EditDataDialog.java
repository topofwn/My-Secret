package com.example.kos.mysecrect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.utils.EncrytedUtils;
import com.example.kos.mysecrect.utils.UIUtils;

public class EditDataDialog extends Dialog{
private Context mContext;
private EditDataDialogListener mListener;
private DataPWD data;
private String key,name,keySpec;

    public EditDataDialog(@NonNull Context context, DataPWD mData, EditDataDialogListener listener) throws Exception {
        super(context);
        this.mContext = context;
        this.mListener = listener;
        this.data = mData;
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_data);
        if (getWindow() != null) {
            View v = getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
        }
        EditText edtName = findViewById(R.id.edtKeyName);
        edtName.setText(data.getFieldName());
        name = data.getFieldName();
        keySpec = data.getMyKeySpec();
        EditText edtKey = findViewById(R.id.edtYourKey);
        edtKey.setText(EncrytedUtils.Decrypt(data));
        key = EncrytedUtils.Decrypt(data);
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtKey.getText().toString().equals(key) || !edtName.getText().toString().equals(name)){
                    DataPWD newData = new DataPWD(edtName.getText().toString(),edtKey.getText().toString(),keySpec);
                    try {
                        newData = EncrytedUtils.Encrypt(newData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mListener.setData(newData);
                    dismiss();
                }else {
                    UIUtils.showToast(mContext,"Data doesn't change");
                }
            }
        });
        ImageButton btnClose = findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

    }
}
