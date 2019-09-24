package com.bochat.app.model.rong;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bochat.app.MainApplication;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupApplyEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.RongCmdEntity;
import com.bochat.app.model.bean.SpeedConverStatusEntity;
import com.bochat.app.model.bean.SweetContent;
import com.bochat.app.model.bean.SweetSystemEntity;
import com.bochat.app.model.bean.SweetSystemSerializable;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.event.FriendApplyEvent;
import com.bochat.app.model.event.GroupApplyEvent;
import com.bochat.app.model.event.MessageListAdapterEvent;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.modelImpl.UserModule;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.CommandMessage;
import io.rong.message.ImageMessage;

import static com.bochat.app.model.constant.Constan.GET_SWEET_RECEIVER;
import static com.bochat.app.model.constant.Constan.SWEET_SYSTEM_MESSAGE;
import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_ADD_FRIEND;
import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_AGREEN_ADD_FRIEND;
import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_DELETE_FRIEND;
import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_SEND_GROUP_APPLY;

/**
 * 2019/4/18
 * Author LDL
 **/
public class RongReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

    private static RongReceiveMessageListener instance;

    public static RongReceiveMessageListener getInstance() {
        synchronized (RongReceiveMessageListener.class) {
            if (instance == null) {
                instance = new RongReceiveMessageListener();
            }
            return instance;
        }
    }

    /**
     * 收到消息的处理。
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示自己处理铃声和后台通知，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(final Message message, int left) {
//        ToastUtils.s(MainApplication.getInstance(),"收到消息");
        try {
            if (message.getContent() instanceof BoChatMessage) {
                if (message.getContent().getUserInfo() == null) {
                    FriendEntity userEntity = new UserModule().getFriendInfo(message.getSenderUserId(), 1, 1).getItems().get(0);
                    if (userEntity.getHead_img() == null) {
                        userEntity.setHead_img("");
                    }
                    message.getContent().setUserInfo(new UserInfo(message.getSenderUserId(), userEntity.getNickname(), Uri.parse(userEntity.getHead_img())));
                }
                BoChatMessage boChatMessage = (BoChatMessage) message.getContent();
                if (message.getObjectName().equals("BC:DataMessge")) {
                    if (boChatMessage.getMessgType() == MESSAGE_TYPE_ADD_FRIEND || boChatMessage.getMessgType() == MESSAGE_TYPE_SEND_GROUP_APPLY) {
                        try {
                            boolean isFriend = CachePool.getInstance().friend().get(Long.valueOf(message.getSenderUserId())) != null;
                            if (!isFriend) {
                                ALog.d("不是好友，删除会话");
                                RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, message.getSenderUserId(), null);
                            } else {
                                ALog.d("是好友，不删除会话");
                            }
//                            RongIM.getInstance().deleteMessages(message.getConversationType(), message.getSenderUserId(), null);
                            RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (boChatMessage.getMessgType() == MESSAGE_TYPE_ADD_FRIEND) {
                    saveFriendApply(boChatMessage.getUserInfo(), boChatMessage.getContent(), boChatMessage.getSourceType(), boChatMessage.getDesc(), boChatMessage.getExtra(), "1");
                } else if (boChatMessage.getMessgType() == MESSAGE_TYPE_AGREEN_ADD_FRIEND) {
                    FriendEntity friendEntity = new FriendEntity();
                    friendEntity.setId(Integer.valueOf(boChatMessage.getUserInfo().getUserId()));
                    friendEntity.setNickname(boChatMessage.getUserInfo().getName());
                    friendEntity.setHead_img(boChatMessage.getUserInfo().getPortraitUri().toString());
                    friendEntity.setFriend_state(1);
                    CachePool.getInstance().friend().put(friendEntity);
                    saveFriendApply(boChatMessage.getUserInfo(), boChatMessage.getContent(), boChatMessage.getSourceType(), boChatMessage.getDesc(), boChatMessage.getExtra(), "2");
                    Log.i("======", "=======对方同意加您为好友");
                } else if (boChatMessage.getMessgType() == MESSAGE_TYPE_DELETE_FRIEND) {
                    FriendEntity friendEntity = CachePool.getInstance().friend().get(Integer.valueOf(message.getSenderUserId()));
                    if (friendEntity != null) {
                        friendEntity.setFriend_state(2);
                        CachePool.getInstance().friend().put(friendEntity);
                    }
                    FriendApplyEntity friendApply = CachePool.getInstance().friendApply().get(Integer.valueOf(message.getSenderUserId()));
                    if (friendApply != null) {
                        friendApply.setApply_state("4");
                        CachePool.getInstance().friendApply().put(friendApply);
                    }
                } else if (boChatMessage.getMessgType() == MESSAGE_TYPE_SEND_GROUP_APPLY) {
                    saveGroupApply(boChatMessage.getUserInfo(), boChatMessage.getContent(), boChatMessage.getSourceType(), boChatMessage.getDesc(), boChatMessage.getExtra(), "1");
                }
                return true;
            } else if (message.getContent() instanceof RedPacketMessage) {
                RedPacketMessage redPacketMessage = (RedPacketMessage) message.getContent();
                if (message.getObjectName().equals("SC:SCRedPacket")) {
                    RedPacketStatuEntity redPacketStatuEntity = new RedPacketStatuEntity();
                    RedPacketStatuEntity tempera = DBManager.getInstance().getRedPacketById(Integer.valueOf(redPacketMessage.getPacketID()));
//                    UserEntity userEntity = CachePool.getInstance().user().getLatest();
//                    tempera.setUserId(userEntity.getId());
                    if (tempera != null) {
                        redPacketStatuEntity.setStatus(1);
                        redPacketStatuEntity.setCount(tempera.getCount());
                        redPacketStatuEntity.setReadyGet(tempera.getReadyGet() + 1);
                    } else {
                        redPacketStatuEntity.setStatus(0);
                        redPacketStatuEntity.setCount(redPacketMessage.getCount());
                        redPacketStatuEntity.setCoin(redPacketMessage.getCoin());
                        redPacketStatuEntity.setReadyGet(0);
                    }
                    redPacketStatuEntity.setId(Integer.valueOf(redPacketMessage.getPacketID()));
                    DBManager.getInstance().saveOrUpdateRedPacket(redPacketStatuEntity);
                }
                return true;
            } else if (message.getContent() instanceof GetRedPacketMessage) {
                String userId = null;
                if (CachePool.getInstance().user().getLatest() != null) {
                    userId = CachePool.getInstance().user().getLatest().getId() + "";
                }
                GetRedPacketMessage getRedPacketMessage = (GetRedPacketMessage) message.getContent();
                if (getRedPacketMessage.getType() == 1001) {        //闪兑
                    SpeedConverStatusEntity statusEntity = new SpeedConverStatusEntity();
                    statusEntity.setId(Integer.valueOf(getRedPacketMessage.getStatusId()));
                    statusEntity.setStatus(getRedPacketMessage.getStatus());
                    DBManager.getInstance().saveOrUpdateSpeedConverStatu(statusEntity);
                } else if (getRedPacketMessage.getType() == 1002) {
                    RedPacketStatuEntity redPacketStatuEntity = new RedPacketStatuEntity();
                    RedPacketStatuEntity tempera = DBManager.getInstance().getRedPacketById(Integer.valueOf(getRedPacketMessage.getStatusId()));
                    if (tempera != null) {
                        if (tempera.getStatus() == 0) {
                            if (userId != null) {
                                if (getRedPacketMessage.getReceiverUserId().equals(userId)) {
                                    redPacketStatuEntity.setStatus(1);
                                } else {
                                    redPacketStatuEntity.setStatus(0);
                                }
                            } else {
                                redPacketStatuEntity.setStatus(0);
                            }
                        } else {
                            redPacketStatuEntity.setStatus(1);
                        }
                        redPacketStatuEntity.setCount(tempera.getCount());
                        redPacketStatuEntity.setReadyGet(tempera.getReadyGet() + 1);
                    }
                    redPacketStatuEntity.setId(Integer.valueOf(getRedPacketMessage.getStatusId()));
                    DBManager.getInstance().saveOrUpdateRedPacket(redPacketStatuEntity);
                }
            } else if (message.getContent() instanceof SpeedConverMessage) {
                SpeedConverMessage speedConverMessage = (SpeedConverMessage) message.getContent();
                SpeedConverStatusEntity statusEntity = new SpeedConverStatusEntity();
                SpeedConverStatusEntity statusEntity1 = DBManager.getInstance().getSpeedConverById(Integer.valueOf(speedConverMessage.getOrderId()));
                if (statusEntity1 == null) {
                    statusEntity.setStatus(1);
                } else {
                    statusEntity.setStatus(speedConverMessage.getConverStatus());
                }
                statusEntity.setId(Integer.valueOf(speedConverMessage.getOrderId()));
                DBManager.getInstance().saveOrUpdateSpeedConverStatu(statusEntity);
            } else if (message.getContent() instanceof CommandMessage) {
                String data = ((CommandMessage) message.getContent()).getData();
                RongCmdEntity rongCmdEntity = new Gson().fromJson(data, RongCmdEntity.class);
                SpeedConverStatusEntity statusEntity = new SpeedConverStatusEntity();
                statusEntity.setStatus(rongCmdEntity.getStatus());
                statusEntity.setId(Integer.valueOf(rongCmdEntity.getOrderId()));
                DBManager.getInstance().saveOrUpdateSpeedConverStatu(statusEntity);
                EventBus.getDefault().post(new MessageListAdapterEvent());
                Log.i("==", "==");
            } else if (message.getContent() instanceof SweetSystemMessage) {
               try{
                   SweetSystemMessage sweetSystemMessage = (SweetSystemMessage) message.getContent();
                   Intent sweetMessageIntent = new Intent();
                   List<SweetContent> list=sweetSystemMessage.getSweetContent();
                   List<SweetSystemSerializable> serializables=new ArrayList<>();
                   for(int i=0;i<list.size();i++){
                       SweetSystemSerializable sweetSystemSerializable=new SweetSystemSerializable(list.get(i).getHead(),list.get(i).getNum(),list.get(i).getCurrency(),list.get(i).getUserName(),list.get(i).getId());
                       serializables.add(sweetSystemSerializable);
                   }
                   SweetSystemEntity sweetSystemEntity=new SweetSystemEntity(serializables);
                   sweetMessageIntent.putExtra(SWEET_SYSTEM_MESSAGE, sweetSystemEntity);
                   sweetMessageIntent.setAction(GET_SWEET_RECEIVER);
                   MainApplication.getInstance().sendBroadcast(sweetMessageIntent);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
//            }else if(message.getContent() instanceof TextMessage){
//                Intent sweetIntent=new Intent();
//                TextMessage textMessage= (TextMessage) message.getContent();
//                sweetIntent.putExtra("haha",textMessage.getContent());
//                sweetIntent.setAction(GET_SWEET_RECEIVER);
//                MainApplication.getInstance().sendBroadcast(sweetIntent);
//            }


            if (message.getContent() instanceof ImageMessage) {
                ImageMessage imageMessage = (ImageMessage) message.getContent();
                ALog.d("base64 " + imageMessage.getBase64());
                ALog.d("LocalUri " + imageMessage.getLocalUri());
                ALog.d("RemoteUri " + imageMessage.getRemoteUri());
                ALog.d("ThumUri " + imageMessage.getThumUri());
            }

            if (message.getContent() instanceof ErrorTipsMessage) {
                ULog.d("onReceived-ErrorTipsMessage");
//                RongIM.getInstance().insertMessage(message.getConversationType(),
//                        message.getTargetId(), message.getSenderUserId(), message.getReceivedStatus(), message.getContent(), message.getReadTime(), new RongIMClient.ResultCallback<Message>() {
//                            @Override
//                            public void onSuccess(Message message) {
//                                ULog.d("insert success");
//                            }
//
//                            @Override
//                            public void onError(RongIMClient.ErrorCode errorCode) {
//                                ULog.d("insert fail");
//                            }
//                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void saveFriendApply(UserInfo info, String content, int sourceType, String sign, String extra, String applyStatu) {
        if (info != null) {
            FriendApplyEntity friendApplyEntity = new FriendApplyEntity();
            friendApplyEntity.setProposer_id(info.getUserId());
            friendApplyEntity.setAddress("");
            friendApplyEntity.setHead_img(info.getPortraitUri().toString());
            friendApplyEntity.setApply_from("");
            friendApplyEntity.setNickname(info.getName());
            friendApplyEntity.setAge("");
            friendApplyEntity.setIsRead("1");
            friendApplyEntity.setApply_text(content);
            friendApplyEntity.setApply_state(applyStatu);
            friendApplyEntity.setSourceType(sourceType);
            friendApplyEntity.setDesc(sign);
            friendApplyEntity.setExtra(extra);
            CachePool.getInstance().friendApply().put(friendApplyEntity);
            EventBus.getDefault().post(new FriendApplyEvent());
            ALog.d("post FriendApplyEvent");
        }
    }

    private void saveGroupApply(UserInfo info, String content, int sourceType, String sign, String extra, String applyStatu) {
        if (info != null) {
            GroupApplyEntity groupApplyEntity = new GroupApplyEntity();
            //TODO group id
            groupApplyEntity.setProposer_id(info.getUserId());
            groupApplyEntity.setApply_time("");
            groupApplyEntity.setApply_state(applyStatu);
            groupApplyEntity.setApply_text(content);
            groupApplyEntity.setApply_from("");
            //TODO group name
            groupApplyEntity.setGroup_name(info.getName());
            //TODO group head
            groupApplyEntity.setGroup_head(info.getPortraitUri().toString());
            groupApplyEntity.setIsRead("1");
            groupApplyEntity.setSourceType(sourceType);
            groupApplyEntity.setExtra(extra);
            groupApplyEntity.setDesc(sign);
            CachePool.getInstance().groupApply().put(groupApplyEntity);
            EventBus.getDefault().post(new GroupApplyEvent());
        }
    }

}