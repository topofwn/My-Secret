package com.example.kos.mysecrect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.utils.EncrytedUtils;
import com.example.kos.mysecrect.utils.UIUtils;

public class ShowDataDialog extends Dialog{
    private Context mContext;


    public ShowDataDialog(@NonNull Context context, DataPWD mData) throws Exception {
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
        TextView txtFieldName = findViewById(R.id.txtKeyName);
        TextView txtKey = findViewById(R.id.txtYourKey);
        txtFieldName.setText(mData.getFieldName());
        txtKey.setText(EncrytedUtils.Decrypt(mData));
        Button btnEncrypt = findViewById(R.id.btnEncrypt);
        Button btnDecrypt = findViewById(R.id.btnDecrypt);
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
        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    txtKey.setText(EncrytedUtils.Decrypt(mData));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    txtKey.setText(mData.getEncrytKey());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
