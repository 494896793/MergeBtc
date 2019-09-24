package com.bochat.app.model.rong;


import android.net.Uri;
import android.util.Log;

import com.bochat.app.common.util.ALog;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;

/**
 * 2019/4/12
 * Author ZZW
 **/
public class SendMessageUtil {

    private static SendMessageUtil sendMessageUtil;

    public static SendMessageUtil getInstance(){
        if(sendMessageUtil==null){
            sendMessageUtil=new SendMessageUtil();
        }
        return sendMessageUtil;
    }

    /**
     * <p>发送消息。
     * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
     * 中的方法回调发送的消息状态及消息体。</p>
     *
     * @param message     将要发送的消息体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
     */
    public void sendTextMsg(Message message, String pushContent, String pushData, final IRongCallback.ISendMessageCallback callback){
        if(callback==null){
            RongIM.getInstance().sendMessage(message, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
                @Override
                public void onAttached(Message message) {
                    //消息本地数据库存储成功的回调
                }

                @Override
                public void onSuccess(Message message) {
                    //消息通过网络发送成功的回调
                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    //消息发送失败的回调
                }
            });
        }else{
            RongIM.getInstance().sendMessage(message, pushContent, pushData, callback);
        }
    }
    /**
     * <p>发送地理位置消息。并同时更新界面。</p>
     * <p>发送前构造 {@link Message} 消息实体，消息实体中的 content 必须为 {@link LocationMessage}, 否则返回失败。</p>
     * <p>其中的缩略图地址 scheme 只支持 file:// 和 http:// 其他暂不支持。</p>
     *
     * @param message             消息实体。
     * @param pushContent         当下发 push 消息时，在通知栏里会显示这个字段。
     *                            如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                            如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData            push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param sendMessageCallback 发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
     */
    public void sendLocationMsg(Message message, String pushContent, final String pushData, final IRongCallback.ISendMessageCallback sendMessageCallback){
        if(sendMessageCallback==null){
            RongIM.getInstance().sendLocationMessage(message, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
                @Override
                public void onAttached(Message message) {

                }

                @Override
                public void onSuccess(Message message) {

                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                }
            });
        }else{
            RongIM.getInstance().sendLocationMessage(message, pushContent, pushData, sendMessageCallback);
        }
    }

    /**
     * <p>根据会话类型，发送图片消息。</p>
     *
     * @param type        会话类型。
     * @param targetId    目标 Id。根据不同的 conversationType，可能是用户 Id、群组 Id 或聊天室 Id。
     * @param content     消息内容，例如 {@link TextMessage}, {@link ImageMessage}。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param callback    发送消息的回调。
     */


    /**
     * <p>发送消息。
     * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
     * 中的方法回调发送的消息状态及消息体。</p>
     *
     * @param message     将要发送的消息体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
     */
    public void sendMediaMsg(Message myMessage,String pushContent,String pushData,IRongCallback.ISendMessageCallback callback){
       if(callback==null){
           RongIM.getInstance().sendMessage(myMessage, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
               @Override
               public void onAttached(Message message) {
                   //消息本地数据库存储成功的回调
               }

               @Override
               public void onSuccess(Message message) {
                   //消息通过网络发送成功的回调
               }

               @Override
               public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                   //消息发送失败的回调
               }
           });
       }else{
           RongIM.getInstance().sendMessage(myMessage, pushContent, pushData,callback);
       }
    }

    /**
     * <p>发送消息。
     * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
     * 中的方法回调发送的消息状态及消息体。</p>
     *
     * @param message     将要发送的消息体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
     *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
     */
    public void sendPhotoMsg(Message myMessage,String pushContent,String pushData,IRongCallback.ISendMessageCallback callback){
        if(callback==null){
            RongIM.getInstance().sendMessage(myMessage, pushContent, pushData, new IRongCallback.ISendMessageCallback() {
                @Override
                public void onAttached(Message message) {
                    //消息本地数据库存储成功的回调
                }

                @Override
                public void onSuccess(Message message) {
                    //消息通过网络发送成功的回调
                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    //消息发送失败的回调
                }
            });
        }else{
            RongIM.getInstance().sendMessage(myMessage, pushContent, pushData, callback);
        }
    }


    /**
     * <p>发送多媒体消息</p>
     * <p>发送前构造 {@link Message} 消息实体</p>
     * @param message     发送消息的实体。
     * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
     *                    发送文件消息时，此字段必须填写，否则会收不到 push 推送。
     * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
     * @param callback    发送消息的回调 {@link io.rong.imlib.RongIMClient.SendMediaMessageCallback}。
     */
    public void sendFileMsg(Message message,String pushContent, final String pushData, final IRongCallback.ISendMediaMessageCallback callback ){
        if(callback==null){
            RongIM.getInstance().sendMediaMessage(message, pushContent, pushData, new IRongCallback.ISendMediaMessageCallback() {
                @Override
                public void onProgress(Message message, int i) {

                }

                @Override
                public void onCanceled(Message message) {

                }

                @Override
                public void onAttached(Message message) {

                }

                @Override
                public void onSuccess(Message message) {

                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                }
            });
        }else{
            RongIM.getInstance().sendMediaMessage(message, pushContent, pushData, callback);
        }
    }

    /**
     * 下载多媒体消息。
     * 用来获取媒体原文件时调用。如果本地缓存中包含此文件，则从本地缓存中直接获取，否则将从服务器端下载。
     *
     * @param message          文件消息。
     * @param callback         下载文件的回调。
     */
    public void downloadMsgFile(Message message, final IRongCallback.IDownloadMediaMessageCallback callback){
        if(callback==null){
            RongIM.getInstance().downloadMediaMessage(message, new IRongCallback.IDownloadMediaMessageCallback(){

                @Override
                public void onSuccess(Message message) {

                }

                @Override
                public void onProgress(Message message, int i) {

                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                }

                @Override
                public void onCanceled(Message message) {

                }
            });
        }else{
            RongIM.getInstance().downloadMediaMessage(message, callback);
        }
    }

    public void cancelDownMsgFile(Message message, RongIMClient.OperationCallback callback){
        if(callback==null){
            RongIM.getInstance().cancelDownloadMediaMessage(message, new RongIMClient.OperationCallback(){

                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }else{
            RongIM.getInstance().cancelDownloadMediaMessage(message, callback);
        }
    }

    /**
     * 向本地会话中插入一条消息。这条消息只是插入本地会话，不会实际发送给服务器和对方。该消息不一定插入本地数据库，是否入库由消息的属性决定。
     *
     * @param type           会话类型。
     * @param targetId       目标会话Id。比如私人会话时，是对方的id； 群组会话时，是群id;
     * @param senderUserId   发送用户 Id。如果是模拟本人插入的消息，则该id设置为当前登录用户即可。如果要模拟对方插入消息，则该id需要设置为对方的id.
     * @param content        消息内容。如{@link TextMessage} {@link ImageMessage}等。
     * @param sentTime       消息的发送时间。
     * @param resultCallback 获得消息发送实体的回调。
     */
    public void insertMessage(final Conversation.ConversationType type, final String targetId, final String senderUserId, final MessageContent content, final long sentTime, final RongIMClient.ResultCallback<Message> resultCallback){
        if(resultCallback==null){
            RongIM.getInstance().insertMessage(type, targetId, senderUserId, content, sentTime, new RongIMClient.ResultCallback<Message>() {
                @Override
                public void onSuccess(Message message) {
                    Log.i("=====","=====数据插入成功"+message.toString());
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("=====","=====数据插入失败");
                }
            });
        }else{
            RongIM.getInstance().insertMessage(type,targetId,senderUserId,content,sentTime,resultCallback);
        }
    }


}
