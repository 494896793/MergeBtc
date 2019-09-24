package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicFlashEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface DynamicFlashContract {
    interface View extends IBaseView<Presenter>{
        void onUpdateList(List<DynamicFlashEntity> entities,int type);
        void dissLoadMore();
    }
    interface Presenter extends IBasePresenter<View> {
        void loadeFlashList(int page,int type); //1 刷新 2 加载
        void sendLiked(String flashId,String option );
    }
}
