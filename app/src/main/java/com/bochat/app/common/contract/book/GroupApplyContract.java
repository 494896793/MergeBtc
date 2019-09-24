package com.bochat.app.common.contract.book;

import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * 2019/4/22
 * Author ZZW
 **/
public interface GroupApplyContract {

    interface View extends IBaseView<GroupApplyContract.Presenter>{
        void updateList(int page, List<GroupApplyServerEntity> list);
    }

    interface Presenter extends IBasePresenter<GroupApplyContract.View>{
        void loadMore(int page);
    }
}