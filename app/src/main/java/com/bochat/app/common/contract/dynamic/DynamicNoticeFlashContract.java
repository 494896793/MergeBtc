package com.bochat.app.common.contract.dynamic;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface DynamicNoticeFlashContract {
     interface View extends IBaseView<DynamicNoticeFlashContract.Presenter>{
          void onUpdateSlidTab(int type);

     }
     interface Presenter extends IBasePresenter<DynamicNoticeFlashContract.View>{

     }
}
