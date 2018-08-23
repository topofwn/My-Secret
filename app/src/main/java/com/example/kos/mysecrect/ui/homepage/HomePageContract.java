package com.example.kos.mysecrect.ui.homepage;

import android.support.annotation.NonNull;

import com.example.kos.mysecrect.ui.base.MVPPresenter;
import com.example.kos.mysecrect.ui.base.MVPView;

public interface HomePageContract {
    interface Presenter<V extends HomePageContract.View> extends MVPPresenter<V> {
        void saveCommand(@NonNull String command);

    }
    interface View extends MVPView {
        void onSavedCommand();
    }
}
