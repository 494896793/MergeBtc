package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketRecordListEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import io.rong.imlib.model.Conversation;

/**
 * 2019/4/25
 * Author LDL
 **/
public interface ConversationDetailContract {

    interface View extends IBaseView<Presenter>{
        void getWelfareSuccess(RedPacketPeopleEntity entity);
        void getWelfareFailed(String msg);
        void queryTakeRecordSuccess(RedPacketRecordListEntity entity);
        void queryTakeRecordFailed(String msg);
        void sendSpeedConverSuccess(SendSpeedEntity entity);
        void getUserInfo(UserEntity userEntity);
        void getUserInfoFailed();
        void sendImgSuccess();
    }

    interface Presenter extends IBasePresenter<View>{
        public void onFlashExchangeBtnClick();
        public void onSettingBtnClick(String id, boolean isSingleChat);
        public void getWelfare(final RedPacketMessage message, final int welfareId, final String userName, final String targetId);
        public void queryTakeRecord(int rewardId,int start,int offset);
        public void sendSpeedConver(final boolean isGRoup, final int startId, final int converId, final double startNum, final double converNum, final int site, final int isSync, final String payPwd, final int relevanceId);
        void getFriendUserInfo(final String userId);
        void sendImgMsg(Conversation.ConversationType type, String target, String path);
    }
}
