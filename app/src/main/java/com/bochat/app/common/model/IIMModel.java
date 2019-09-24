package com.bochat.app.common.model;

import android.net.Uri;

import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.rong.RedPacketMessage;

import io.rong.imlib.model.Conversation;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/22 10:16
 * Description :
 */

public interface IIMModel {
    public boolean sendTextMsg(String targetId, String text);
    
    public boolean addFriendFromAccount(String targetId, String text);
    public boolean addFriendFromQR(String targetId, String text);
    public boolean addFriendFromGroup(String targetId, String text);
    public boolean deleteFriend(String targetId, String text);
    public boolean acceptFriend(String targetId, String text, int sourceType);
    public boolean sendRedPacket(String targetId,RedPacketEntity entity, int messgType, int SourceType,boolean isgroup,int type);
    public boolean getRedPacket(String targetId, RedPacketMessage message, int messgType, int SourceType, String receiverID);
    public boolean getGroupRedPacket(boolean isGroup,int type,String targetId,String sendUserId,String sendUserName,String receiverUserId,String receiveUserName,String statusId,int status);
//    public boolean getGroupRedPacket(int type,String targetId,String sendUserId,String sendUserName,String receiverUserId,String receiveUserName,String statusId,int status);
    public boolean sendSpeedConver(String targetId, String startId, String convertId, SendSpeedEntity speedEntity,boolean isGroup);
    public boolean sendImgMsg(Conversation.ConversationType type, String targetId,   Uri thumUri, Uri localUri, boolean isFull);
    public boolean cancelExchange(String targetId,boolean isGroup,String orderId);
    public boolean addGroup(String targetId, String text, int sourceType);
    public boolean sendGroupApply(String targetId, String text, int sourceType);
}
