package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/29
 * Author LDL
 **/
public interface ProtocolBookContract {

    interface View extends IBaseView<ProtocolBookContract.Presenter>{
        void refreshData(ProtocolBookEntity entity);
    }

    interface Presenter extends IBasePresenter<ProtocolBookContract.View>{

    }

}
