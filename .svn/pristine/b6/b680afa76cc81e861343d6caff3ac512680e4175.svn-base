package com.bochat.app.common.contract.dynamic;


import com.bochat.app.model.bean.DynamicIncomeOfTodayEntity;
import com.bochat.app.model.bean.DynamicQueryUserBXInfoEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface DynamicRecruitContract {


    interface View extends IBaseView<Presenter> {
      public void updateUserImage(UserEntity userInfo);
      public void  updateRegisterLayout(DynamicQueryUserBXInfoEntity entity);
      public void updateTodayIncome(DynamicIncomeOfTodayEntity entity);
      public void showShareDialog(String shareUrl);
    }

    interface Presenter extends IBasePresenter<View> {
        //点击按钮加入
        public void onJoinButtonOnClick();
        public void onShareClick();
    }
}
