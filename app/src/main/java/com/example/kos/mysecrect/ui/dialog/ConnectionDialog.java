package com.example.kos.mysecrect.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.utils.AppConstants;
import com.example.kos.mysecrect.utils.OGILVYLog;

public class ConnectionDialog extends Dialog implements View.OnClickListener{
   private Context mContext;
   private ConnectionDialogListener mListener;

    public ConnectionDialog(@NonNull Context context) {
        super(context);
        mContext = context;

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notify);
        if (getWindow() != null) {
            View v = getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
        }
        TextView message = findViewById(R.id.txtMessage);
        message.setText(AppConstants.NO_CONNECTION);
        Button btnOK = findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);

    }
    public void setDialogListener(ConnectionDialogListener dialogListener) {
        this.mListener = dialogListener;
    }

    @Override
    public void dismiss() {
        try {
            Activity act = (Activity) mContext;
            if (act != null && !act.isFinishing()) {
                super.dismiss();
                if (mListener != null) {
                    mListener.DismissDialog();
                }
            }
        } catch (RuntimeException e) {
            OGILVYLog.l(e);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOK){
            dismiss();
        }
    }
    public interface ConnectionDialogListener {
        void DismissDialog();
    }
}
