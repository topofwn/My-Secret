package com.example.kos.mysecrect.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.ui.pwdstore.PWDStoreContract;
import com.example.kos.mysecrect.utils.EncrytedUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ShowDataDialog extends Dialog implements View.OnClickListener{
    private Context mContext;
    private ShowDataDialogListener mListener;

    public ShowDataDialog(@NonNull Context context, DataPWD mData, ShowDataDialogListener mListener) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        super(context);
        this.mContext = context;
        this.mListener = mListener;
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
        TextView txtKey = findViewById(R.id.txtKey);
        txtFieldName.setText(mData.getFieldName());
        txtKey.setText(EncrytedUtils.getDecrypt(mData.getEncrytKey()));
    }

    public ShowDataDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onClick(View v) {

    }
}
