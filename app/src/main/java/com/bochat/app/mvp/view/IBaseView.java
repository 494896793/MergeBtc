package com.bochat.app.mvp.view;

import android.content.Context;
import android.content.Intent;

import com.bochat.app.mvp.presenter.IBasePresenter;

/**
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/08 09:49
 * Description:
 */
public interface IBaseView<P extends IBasePresenter> {
    void showLoading(Object extras);
    void hideLoading(Object extras);
    void showTips(TipsType tipsType);
    void showTips(String tips);
    void finish();
    void finishAll(boolean containSelf);
    Context getViewContext();
    Intent getViewIntent();
    void setViewIntent(Intent intent);
}
