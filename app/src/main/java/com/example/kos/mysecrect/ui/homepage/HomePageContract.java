package com.example.kos.mysecrect.ui.homepage;

import android.graphics.Bitmap;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;

public interface HomePageContract {
    interface Presenter<V extends View> extends MVPPresenter<V> {
        UserD getUser();

        void setListData(List<DataPWD> data);

        void setUser(UserD user);

    }

    interface View extends MVPView {
        void updateHomePage(Bitmap bitmap);
    }
}
