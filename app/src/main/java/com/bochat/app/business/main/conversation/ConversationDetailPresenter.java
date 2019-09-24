package com.bochat.app.business.main.conversation;

import android.net.Uri;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.ConversationDetailContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterQuickExchangeHall;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketRecordListEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.bean.SpeedConverStatusEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.rong.BoChatMessage;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.model.Conversation;

import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_ADD_FRIEND;

/**
 * 2019/4/25
 * Author LDL
 **/
@SuppressWarnings({"CheckResult", "ResultOfMethodCallIgnored"})
public class ConversationDetailPresenter extends BasePresenter<ConversationDetailContract.View> implements ConversationDetailContract.Presenter {


    @Inject
    IRedPacketModel model;

    @Inject
    IIMModel iimModel;

    @Inject
    IUserModel userModel;

    @Inject
    ISpeedConverModel speedConverModel;

    @Inject
    IUserModel iUserModel;

    @Inject
    IOSSModel iossModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onFlashExchangeBtnClick() {
        Router.navigation(new RouterQuickExchangeHall());
    }

    @Override
    public void onSettingBtnClick(String id, boolean isSingleChat) {
        if (isSingleChat) {
            Router.navigation(new RouterFriendDetail(id));
        } else {
            Router.navigation(new RouterGroupDetail(id));
        }
    }

    @Override
    public void getFriendUserInfo(final String userId) {
        Observable.create(new ObservableOnSubscribe<FriendListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<FriendListEntity> emitter) throws Exception {
                FriendListEntity friendListEntity = iUserModel.getFriendInfo(userId, -1, -1);
                if (friendListEntity != null && friendListEntity.getItems() != null && friendListEntity.getItems().size() != 0) {
                    emitter.onNext(friendListEntity);
                } else {
                    emitter.onError(new Throwable("拉取好友信息失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<FriendListEntity>() {
            @Override
            public void accept(FriendListEntity entity) throws Exception {
                if (isActive()) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setHeadImg(entity.getItems().get(0).getHead_img());
                    userEntity.setNickname(entity.getItems().get(0).getNickname());
                    userEntity.setId(entity.getItems().get(0).getId());
                    getView().getUserInfo(userEntity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getWelfareFailed(throwable.getMessage());
                getView().showTips(throwable.getMessage());
            }
        });
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
//        RouterConversationDetail extra = getExtra(RouterConversationDetail.class);
//        if(extra!=null){
//            RongMentionManager.getInstance().mentionMember(new UserInfo(extra.getUserInfo().getMember_id()+"",extra.getUserInfo().getNickname(),Uri.parse(extra.getUserInfo().getHead_img())));
//        }

//        if(getView().getViewIntent().hasExtra("ROUTER")){
//            GroupMemberEntity groupMemberEntity= (GroupMemberEntity) getView().getViewIntent().getSerializableExtra("ROUTER");
//            RongMentionManager.getInstance().mentionMember(new UserInfo(groupMemberEntity.getMember_id()+"",groupMemberEntity.getNickname(),Uri.parse(groupMemberEntity.getHead_img())));
//        }
    }

    public void sendImgMsg(final Conversation.ConversationType type, final String targetId, final String localPath) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {

                File file = new File(localPath);

//                Uri image = Uri.fromFile(file);
                Uri image = Uri.parse("file://" + file);
                /*  ALog.d("send image ############## uriPath " + image);
              ALog.d("send image ############## localPath " + localPath);
                ALog.d("send image ############## uriPath " + image);

                String checkUrl = UriUtil.getFilePathByUri(MainApplication.getContext(), image);
                
                ALog.d("send image ############## checkUrl " + checkUrl);

                byte[] byteFromUri = FileUtils.getByteFromUri(image);

                ALog.d("send image ############## rong " + (byteFromUri == null ? " no " : "ok"));

                String rongPath = image.toString().substring(5);
                ALog.d("rongPath ############## rongPath " + rongPath);
                File rongFile = new File(rongPath);
                if(!rongFile.exists()){
                    emitter.onError(new Throwable("图片名称不合法，请换个名称再试一下"));
                    return;
                }*/
                boolean isRight = iimModel.sendImgMsg(type, targetId, image, image, false);
                if (!isRight) {
                    emitter.onError(new Throwable("发送失败"));
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object entity) throws Exception {
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().showTips(new ResultTipsType(throwable.getMessage(), false));
            }
        });
    }

    @Override
    public void sendSpeedConver(final boolean isGRoup, final int startId, final int converId, final double startNum, final double converNum, final int site, final int isSync, final String payPwd, final int relevanceId) {
        Observable.create(new ObservableOnSubscribe<SendSpeedEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SendSpeedEntity> emitter) throws Exception {
                SendSpeedEntity entity = speedConverModel.sendSpeedConver(startId, converId, startNum, converNum, site, isSync, payPwd, relevanceId);
                if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {
                    SpeedConverStatusEntity statuEntity = DBManager.getInstance().getSpeedConverById(Integer.valueOf(converId));
                    if (statuEntity == null) {
                        statuEntity = new SpeedConverStatusEntity();
                        statuEntity.setId(Integer.valueOf(converId));
                        statuEntity.setStatus(1);
                    } else {
                        statuEntity.setStatus(2);
                    }
                    DBManager.getInstance().saveOrUpdateSpeedConverStatu(statuEntity);
                    boolean isRight = false;
                    isRight = iimModel.sendSpeedConver(relevanceId + "", startId + "", converId + "", entity, isGRoup);
                    emitter.onNext(entity);
                } else {
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SendSpeedEntity>() {
            @Override
            public void accept(SendSpeedEntity entity) throws Exception {
                if (isActive()) {
                    getView().sendSpeedConverSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getWelfareFailed(throwable.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return "操作失败";
            }
        });
    }

    @Override
    public void getWelfare(final RedPacketMessage message, final int welfareId, final String userName, final String targetId) {
        final UserEntity userEntity = CachePool.getInstance().user().getLatest();
        Observable.create(new ObservableOnSubscribe<RedPacketPeopleEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedPacketPeopleEntity> emitter) throws Exception {
                try {
                    RedPacketPeopleEntity entity = model.getWelfare(welfareId, userName);
                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {
                        RedPacketStatuEntity statuEntity = DBManager.getInstance().getRedPacketById(welfareId);
                        statuEntity.setId(Long.valueOf(message.getPacketID()));
                        statuEntity.setStatus(1);
                        statuEntity.setCount(statuEntity.getCount());
                        statuEntity.setReadyGet(statuEntity.getReadyGet() + 1);
                        DBManager.getInstance().saveOrUpdateRedPacket(statuEntity);
                        boolean isRight = false;
                        if (message.isIsgroup()) {
                            isRight = iimModel.getGroupRedPacket(true, Constan.GET_GROUP_REDPACKET, targetId,
                                    message.getFromId(), message.getFromUserName(), userEntity.getId() + "",
                                    userEntity.getNickname(), message.getPacketID(), Constan.GET_GROUP_REDPACKET);
                        } else {
                            isRight = iimModel.getRedPacket(targetId, message, MESSAGE_TYPE_ADD_FRIEND, BoChatMessage.SOURCE_TYPE_ACCOUNT, userEntity.getId() + "");
                        }
                        emitter.onNext(entity);
                    } else {
                        if (entity != null && entity.getMsg() != null) {
                            emitter.onError(new Throwable(entity.getMsg()));
                        } else {
                            emitter.onError(new Throwable("请求失败"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedPacketPeopleEntity>() {
            @Override
            public void accept(RedPacketPeopleEntity entity) throws Exception {
                if (isActive()) {
                    getView().getWelfareSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getWelfareFailed(throwable.getMessage());
                getView().showTips(throwable.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }

    @Override
    public void queryTakeRecord(final int rewardId, final int start, final int offset) {
        Observable.create(new ObservableOnSubscribe<RedPacketRecordListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedPacketRecordListEntity> emitter) throws Exception {
                RedPacketRecordListEntity entity = model.queryTakeRecord(rewardId, start, offset);
                if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {
                    emitter.onNext(entity);
                } else {
                    emitter.onError(new RxErrorThrowable(entity));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedPacketRecordListEntity>() {
            @Override
            public void accept(RedPacketRecordListEntity entity) throws Exception {
                if (isActive()) {
                    getView().queryTakeRecordSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().queryTakeRecordFailed(throwable.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return "网络繁忙，请稍后再试";
            }
        });
    }

}
