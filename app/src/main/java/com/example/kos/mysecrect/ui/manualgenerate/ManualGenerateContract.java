package com.example.kos.mysecrect.ui.manualgenerate;

import com.example.kos.mysecrect.data.model.DataPWD;
import com.example.kos.mysecrect.data.model.UserD;
import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

import java.util.List;

public interface ManualGenerateContract {
    interface Presenter<V extends ManualGenerateContract.View> extends MVPPresenter<V> {
        List<DataPWD> getList();
        void setList(List<DataPWD> data);
        String getMyDeviceId();
        UserD getUser();
        void setUser(UserD user);

    }
    interface View extends MVPView {

    }
}
