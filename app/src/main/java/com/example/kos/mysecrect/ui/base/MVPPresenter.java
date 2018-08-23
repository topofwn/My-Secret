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

/**
 * Created by tuan.giao on 27/07/17.
 */


import android.support.annotation.NonNull;

import com.androidnetworking.error.ANError;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MVPView type that wants to be attached with.
 */
public interface MVPPresenter<V extends MVPView> {
    void onAttach(V mvpView);

    void onDetach();

    void onViewInitialized();

    void handleApiError(@NonNull ANError error);

    void handleThrowable(Throwable throwable);

    void setUserAsLoggedOut();
}
