package com.bochat.app.model.listenner;

import com.bochat.app.model.bean.HttpClientEntity;

/**
 * Created by ldl on 2019/4/9.
 */

public interface ResponseListener {
    void onSuccess(HttpClientEntity httpClientEntity);
    void onError(HttpClientEntity httpClientEntity);
}
