package com.bochat.app.model.modelImpl.MarketCenter;

import com.bochat.app.common.util.ALog;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/09 13:49
 * Description :
 */

public class MarketCenterModelTester {

    
    public MarketCenterModelTester() {
    }

    public void start(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i = 0; i < 200; i++){
                    try {
                        testTransaction(MarketCenterModel.createTest());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
    public void stop(){
        
    }
    
    private void testTransaction(final MarketCenterModel model) throws InterruptedException {
        model.addObserver(TransactionListEntity.class, new MarketCenterObserver<TransactionListEntity>() {
            @Override
            public void onReceive(TransactionListEntity entity) {
                String content = "empty";
                List<TransactionEntity> list = entity.getList();
                if(list != null && !list.isEmpty()){
                    content = list.get(0).getMarketId();
                }
                ALog.e("TEST  " + (1000 + model.instanceNum) + "  rec  " + entity.getTransactionType() + "   " + content);
            }
        });
        new Thread(){
            @Override
            public void run() {
                int i = 1;
                while(true){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ALog.e("TEST  " + (1000 + model.instanceNum) + "  cmd  " + i);
                    model.sendCommand(new TransactionCommand(String.valueOf(i++), "defaul"));
                    if(i > 3){
                        i = 1;
                    }
                }
            }
        }.start();
    }
    
    private void testKLine(final MarketCenterModel model){
        model.addObserver(KLineEntity.class, new MarketCenterObserver<KLineEntity>() {
            @Override
            public void onReceive(KLineEntity entity) {
                ALog.d(model.instanceNum + " kline " + entity);
            }
        });
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                model.sendCommand(new KLineCommand(String.valueOf(model.instanceNum), "37", 
                        KLineCommand.KLineType.INSTANT, 0, 300));
            }
        }.start();
    }
}
