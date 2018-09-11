package com.example.kos.mysecrect.ui.dialog;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.utils.EncrytedUtils;
import com.example.kos.mysecrect.utils.UIUtils;

import static com.example.kos.mysecrect.R.color.transparent_black_forty;

public class ShowDataDialog extends Dialog{
    private Context mContext;
    private int check = 0;
    private ShowDialogListener mListener;


    public ShowDataDialog(@NonNull Context context, DataPWD mData,boolean isFreeze) throws Exception {
        super(context);
        this.mContext = context;
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_show_data);
        if (getWindow() != null) {
            View v = getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
        }
        Button btnSave = findViewById(R.id.btnSave);
        EditText txtFieldName = findViewById(R.id.txtKeyName);
        EditText txtKey = findViewById(R.id.txtYourKey);
        txtFieldName.setEnabled(isFreeze);
        txtKey.setEnabled(isFreeze);
        txtFieldName.setText(mData.getFieldName());
        txtKey.setText(mData.getEncrytKey());
        if (!isFreeze){
            btnSave.setVisibility(View.VISIBLE);
            txtKey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtKey.setText("");
                }
            });
            txtFieldName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtFieldName.setText("");
                }
            });
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  DataPWD ndata = new DataPWD();
                  ndata.setFieldName(txtFieldName.getText().toString());
                  ndata.setMyKeySpec(txtFieldName.getText().toString());
                  ndata.setEncrytKey(txtKey.getText().toString());
                  mListener.editData(ndata);
                }
            });
        }
        Button btnCrypt = findViewById(R.id.btnCrypt);
        Button btnCopy = findViewById(R.id.btnCopyClipboard);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", txtKey.getText().toString());
                    clipboard.setPrimaryClip(clip);
                UIUtils.showToast(mContext,"Copied");
            }
        });
        ImageButton btnClose = findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        btnCrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check == 0) {
                    try {
                        txtKey.setText(EncrytedUtils.Decrypt(mData));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    check = 1;
                    btnCrypt.setText("ENCRYPT");
                }else if(check == 1){
                    try {
                        txtKey.setText(mData.getEncrytKey());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    check = 0;
                    btnCrypt.setText("DECRYPT");
                }
            }
        });
    }
}
