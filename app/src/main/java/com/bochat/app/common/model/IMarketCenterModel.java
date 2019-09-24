package com.bochat.app.common.model;

import com.bochat.app.model.modelImpl.MarketCenter.EntrustCommand;
import com.bochat.app.model.modelImpl.MarketCenter.EntrustListEntity;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterCommand;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterData;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterObserver;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/26 19:23
 * Description :
 */

public interface IMarketCenterModel {
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/27 0027 下午 2:25
     * Description : 添加对指定Type的WebSocket消息的监听，可对同一Type添加多个
     * @param : 需要监听的消息的类型
 *              指定货币的交易对 {@link com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity}
 *              卖出委托 {@link EntrustListEntity}
 *              买入委托 {@link com.bochat.app.model.modelImpl.MarketCenter.EntrustBuyListEntity}
 *              K线数据 {@link com.bochat.app.model.modelImpl.MarketCenter.KLineEntity}
     * @param : 监听器
     */
    <T extends MarketCenterData> int addObserver(Class<T> tClass, MarketCenterObserver<T> listener);
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/27 0027 下午 2:25
     * Description : 移除对指定的WebSocket消息的监听    
     * @param : 需要移除监听的消息的类型
     * @param : 监听器
     */
    <T extends MarketCenterData> int removeObserver(Class<T> tClass, MarketCenterObserver<T> listener);
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/27 0027 下午 2:25
     * Description : 通过WebSocket发送命令（同步）    
     * @param : 命令 查询包含指定货币的交易对 {@link com.bochat.app.model.modelImpl.MarketCenter.TransactionCommand}
     *              查询卖出委托 {@link EntrustCommand}
     *              查询买入委托 {@link com.bochat.app.model.modelImpl.MarketCenter.EntrustBuyCommand}
     *              查询K线数据 {@link com.bochat.app.model.modelImpl.MarketCenter.KLineCommand}
     */
    SendCommandResult sendCommand(MarketCenterCommand command);
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/27 0027 下午 2:25
     * Description : 通过WebSocket发送命令（异步）    
     */
    void sendCommand(MarketCenterCommand command, SendCommandCallback callback);

    /**
     * Author      : FJ
     * CreateDate  : 2019/6/27 0027 下午 2:25
     * Description : 停止WebSocket，并移除所有监听     
     */
    void destroy();
    
    interface SendCommandCallback{
        void onComplete(SendCommandResult result);
    }
    
    class SendCommandResult {
        private boolean isSuccess;
        private String errorMessage;
        private MarketCenterCommand command;

        public SendCommandResult(MarketCenterCommand command, boolean isSuccess, String errorMessage) {
            this.isSuccess = isSuccess;
            this.errorMessage = errorMessage;
            this.command = command;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public MarketCenterCommand getCommand() {
            return command;
        }

        public void setCommand(MarketCenterCommand command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return "SendCommandResult{" +
                    "isSuccess=" + isSuccess +
                    ", errorMessage='" + errorMessage + '\'' +
                    ", command=" + command +
                    '}';
        }
    }
}
