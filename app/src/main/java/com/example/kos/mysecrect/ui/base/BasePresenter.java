/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.kos.mysecrect.ui.base;


import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.androidnetworking.error.ANError;

import com.example.kos.mysecrect.R;
import com.example.kos.mysecrect.data.DataManager;
import com.example.kos.mysecrect.utils.SchedulerProvider;



import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MVPView> implements MVPPresenter<V> {

    private static final String TAG = "BasePresenter";

    private SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    protected DataManager dataManager;


    private V mMvpView;

    public BasePresenter(SchedulerProvider schedulerProvider, DataManager dataManager) {
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
        this.dataManager = dataManager;

    }

    public BasePresenter() {

    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mMvpView = null;
    }

    public void addDisposableObserver(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void onViewInitialized() {
    }

    @Override
    public void handleThrowable(Throwable throwable) {
        if (throwable instanceof ANError) {
            handleApiError((ANError) throwable);
        } else {
            getMvpView().onError(R.string.some_error);
        }
    }

    @Override
    public void handleApiError(@NonNull ANError error) {
        if ( error.getErrorBody() == null) {
            getMvpView().onConnectToServerError();
            return;
        }

        getMvpView().onError(TextUtils.isEmpty(error.getMessage()) ? "" : error.getMessage());
    }

    @Override
    public void setUserAsLoggedOut() {
        mMvpView.onTokenExpired();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MVPView) before" +
                    " requesting data to the Presenter");
        }
    }
}
