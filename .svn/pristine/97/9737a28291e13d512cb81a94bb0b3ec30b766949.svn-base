package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.ProtocolBookContract;
import com.bochat.app.common.router.RouterDynamicProjectIdentificationBook;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/5/29
 * Author LDL
 **/
public class ProtocolBookPresenter extends BasePresenter<ProtocolBookContract.View> implements ProtocolBookContract.Presenter {


    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterDynamicProjectIdentificationBook extra = getExtra(RouterDynamicProjectIdentificationBook.class);

        if(extra.getProtocolBookEntity() != null){
            try{
                ProtocolBookEntity entity= extra.getProtocolBookEntity();
                getView().refreshData(entity);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
