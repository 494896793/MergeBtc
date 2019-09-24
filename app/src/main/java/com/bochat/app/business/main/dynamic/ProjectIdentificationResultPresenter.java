package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.ProjectIdentificationResultContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.router.RouterDynamicProjectIdentificationResult;
import com.bochat.app.model.bean.ProjectIdentificationEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * 2019/5/27
 * Author LDL
 **/
public class ProjectIdentificationResultPresenter extends BasePresenter<ProjectIdentificationResultContract.View> implements ProjectIdentificationResultContract.Presenter {

    @Inject
    IDynamicModel dynamicModel;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterDynamicProjectIdentificationResult extra = getExtra(RouterDynamicProjectIdentificationResult.class);

        if(extra.getProjectIdentificationEntity() != null){
            ProjectIdentificationEntity entity= extra.getProjectIdentificationEntity();
            if(entity!=null){
                getView().refreshData(entity);
            }
        }
    }
}
