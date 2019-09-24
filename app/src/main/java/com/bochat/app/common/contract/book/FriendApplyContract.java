package com.bochat.app.common.contract.book;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/4/22
 * Author ZZW
 **/
public interface FriendApplyContract  {

    interface View extends IBaseView<FriendApplyContract.Presenter>{

    }

    interface Presenter extends IBasePresenter<FriendApplyContract.View>{

    }

}
