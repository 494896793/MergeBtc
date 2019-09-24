package com.bochat.app.business.main.conversation;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.QRCardContract;
import com.bochat.app.common.router.RouterQRCard;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 11:44
 * Description :
 */

public class QRCardPresenter extends BasePresenter<QRCardContract.View> implements QRCardContract.Presenter{
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterQRCard extra = getExtra(RouterQRCard.class);
        if(extra.getFriendEntity() != null){
            getView().updateFriendQRCard(extra.getFriendEntity());
        } else if(extra.getGroupEntity() != null){
            getView().updateGroupQRCard(extra.getGroupEntity());
        } else {
            UserEntity latest = CachePool.getInstance().user().getLatest();
            getView().updateUserQRCard(latest);
        }
    }
}
