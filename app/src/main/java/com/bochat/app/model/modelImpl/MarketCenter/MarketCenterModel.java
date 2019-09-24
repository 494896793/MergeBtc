package com.bochat.app.model.modelImpl.MarketCenter;

import android.os.Handler;
import android.os.HandlerThread;

import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.IMarketCenterModel;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.modelImpl.DynamicModel;
import com.bochat.app.model.util.QuotationApi;
import com.bochat.security.Encrypt;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 09:27
 * Description :
 */

public class MarketCenterModel implements IMarketCenterModel {

    private static int instanceCount;
    public int instanceNum;
    
    private static MarketCenterModel marketCenterModel = new MarketCenterModel();
    
    private HandlerThread handlerThread;
    private Handler handler;

    private HashMap<MarketCenterType, ArrayList<MarketCenterObserver>> listenerMap = new HashMap<>();

    private WebSocketClient webSocket;
    
    private DynamicModel dynamicModel;
    
    private HashMap<MarketCenterType, MarketCenterCommand> commandMap = new HashMap<>();
    
    private String userId;
    
    private MarketCenterModel() {
        instanceNum = instanceCount++;
        handlerThread = new HandlerThread(MarketCenterModel.class.getSimpleName() + instanceNum);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        dynamicModel = new DynamicModel();
        userId = String.valueOf(CachePool.getInstance().loginUser().getLatest().getId());
        checkAlive(0);
    }

    public static MarketCenterModel getInstance() {
        if(marketCenterModel == null){
            marketCenterModel = new MarketCenterModel();
        }
        return marketCenterModel;
    }
    
    public static MarketCenterModel createTest() {
        return new  MarketCenterModel();
    }
    
    @Override
    public <T extends MarketCenterData> int addObserver(Class<T> tClass, MarketCenterObserver<T> listener) {
        try {
            MarketCenterType type = tClass.newInstance().getType();
            ArrayList<MarketCenterObserver> listenerArray = listenerMap.get(type);
            if (listenerArray == null) {
                listenerArray = new ArrayList<>();
                listenerMap.put(type, listenerArray);
            }
            listenerArray.add(listener);

            return listenerArray.size();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public <T extends MarketCenterData> int removeObserver(Class<T> tClass, MarketCenterObserver<T> listener) {
        try {
            MarketCenterType type = tClass.newInstance().getType();
            ArrayList<MarketCenterObserver> listenerArray = listenerMap.get(type);
            if (listenerArray == null || listenerArray.isEmpty()) {
                return 0;
            }
            listenerArray.remove(listener);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SendCommandResult sendCommand(MarketCenterCommand command) {
        try {
            if(command instanceof KLineCommand){
                if(((KLineCommand) command).getKLineType() != KLineCommand.KLineType.INSTANT){
                    KLineCommand cmd = (KLineCommand) command;
                    queryRecord(cmd);
                    return new SendCommandResult(command, true, "请求发送成功");
                }
            }
            ALog.e("发送命令 " + command.convertToJson().toString());
            commandMap.put(command.getType(), command);
            webSocket.send(command.convertToJson().toString());
            return new SendCommandResult(command, true, "消息发送成功");
        } catch (Exception ignore) {
        }
        return new SendCommandResult(command, false, "消息发送失败");
    }

    private void queryRecord(final KLineCommand cmd) {
        if(cmd == null){
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<KLineListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<KLineListEntity> emitter) throws Exception {
                try {
                    KLineListEntity entity = dynamicModel.queryKLineRecord(Long.valueOf(cmd.getMarketId()), cmd.getKLineType().getType(),
                            cmd.getStartId(), (int)cmd.getOffset());
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<KLineListEntity>() {
            @Override
            public void accept(KLineListEntity entity) throws Exception {
                ArrayList<MarketCenterObserver> observers = listenerMap.get(cmd.getType());
                entity.setStartId(String.valueOf(cmd.getStartId()));
                entity.setOffset(String.valueOf(cmd.getOffset()));
                KLineEntity kLineEntity = new KLineEntity();
                kLineEntity.setMarketId(cmd.getMarketId());
                kLineEntity.setListEntity(entity);
                kLineEntity.setkLineType(cmd.getKLineType());
                
                ALog.d("K线记录 " + kLineEntity);
                
                if(observers != null && !observers.isEmpty()){
                    for(MarketCenterObserver observer : observers){
                        observer.onReceive(kLineEntity);
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
    }
    
    @Override
    public void sendCommand(final MarketCenterCommand command, final SendCommandCallback callback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onComplete(sendCommand(command));
            }
        });
    }

    @Override
    public void destroy() {
        marketCenterModel = null;
        listenerMap.clear();
        if(webSocket != null){
            webSocket.close();
        }
        webSocket = null;
        handlerThread.quitSafely();
    }
    
    public void initSync() {
        try {
            UserEntity userEntity = CachePool.getInstance().user().getLatest();
            String token = userEntity.getToken();
            long time = System.currentTimeMillis();
            int random = (int) (Math.random() * 1000000);

            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("Authorization", token);
            httpHeaders.put("timestamp", String.valueOf(time));
            httpHeaders.put("nonce", String.valueOf(random));
            httpHeaders.put("sign", Encrypt.nativeSHA1(String.valueOf(time), String.valueOf(random)));
            
            webSocket = new WebSocketClient(new URI(QuotationApi.WS_SERVER_URI), httpHeaders) {

                @Override
                public void onOpen(ServerHandshake handshake) {
                    ALog.d("已连接");
                }

                @Override
                public void onMessage(String message) {

                    try {
                        ALog.v("收到消息" + message);
                        JSONObject jsonObject = new JSONObject(message);
                        ALog.v("收到消息" + jsonObject.toString(2));
                        String channel = jsonObject.getString("channel");
                        MarketCenterType centerType = MarketCenterType.search(channel);
                        if (centerType == null) {
                            ALog.d("消息类型错误");
                            return;
                        }
                        ArrayList<MarketCenterObserver> list = listenerMap.get(centerType);
                        if (list == null || list.isEmpty()) {
                            ALog.d(centerType + " 观察者列表为空");
                            return;
                        }
                        MarketCenterData data = centerType.getClazz().newInstance();

                        data.parseFromJson(jsonObject);
                        for (MarketCenterObserver observer : list) {
                            observer.onReceive(data);
                        }
                        ALog.d(centerType + " 消息分发完成 [观察者数量" + list.size() + "]");
                    } catch (Exception e) {
                        ALog.d(e.getLocalizedMessage());
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    ALog.d("连接关闭 " + reason + " " + code);
                }

                @Override
                public void onWebsocketPong(WebSocket conn, Framedata f) {
                    super.onWebsocketPong(conn, f);
                    ALog.d("onWebsocketPong ");
                }

                @Override
                public void onWebsocketPing(WebSocket conn, Framedata f) {
                    super.onWebsocketPing(conn, f);
                    ALog.d("收到心跳");
                } 

                @Override
                public void onError(Exception ex) {
                    ALog.d("连接错误 " + ex.getMessage());
                }
            };
            
            if(webSocket.connectBlocking()){
                ALog.d("连接成功");
                return;
            }
        } catch (Exception ignore) {
        }
        webSocket = null;
        ALog.d("连接失败");
    }
    
    private void reconnectSync() {
        if(webSocket != null){
            try {
                if(webSocket.reconnectBlocking()){
                    ALog.d("重连成功");
                    if(!commandMap.isEmpty()){
                        for(MarketCenterCommand command : commandMap.values()){
                            sendCommand(command);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                ALog.d("重连失败");
            }
        }
    }
    
    private int count = 0;
    
    private void checkAlive(long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(marketCenterModel == null){
                    ALog.e("已销毁，请重新初始化");
                    return;
                }
                if (webSocket == null) {
                    ALog.e("未连接，开始连接");
                    initSync();
                } else if (!webSocket.isOpen()) {
                    ALog.e("连接已断开，重新连接");
                    reconnectSync();
                } else if (count++ > 30) {
                    count = 0;
                    ALog.e("发送心跳");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("channel", "0");
                                jsonObject.put("userid", userId);
                                webSocket.send(jsonObject.toString());
                                ALog.e("发送心跳成功");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    ALog.d("连接正常");
                }
                checkAlive(2000);
            }
        }, delay);
    }
}