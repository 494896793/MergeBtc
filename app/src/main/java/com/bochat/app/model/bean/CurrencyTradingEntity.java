package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/29
 * Author LDL
 **/
public class CurrencyTradingEntity extends CodeEntity implements Serializable {

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int isNext;
    private int totalPage;
    private int tradeMoney;
    private int cost;
    private int tradeDepict;
    private String bName;
    private int tradeStatus;
    private String time;
    private int tradeType;
    private String tradeAddress;
    private List<CurrencyTradingItemEntity> items;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<CurrencyTradingItemEntity> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "CurrencyTradingEntity{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", isNext=" + isNext +
                ", totalPage=" + totalPage +
                ", tradeMoney=" + tradeMoney +
                ", cost=" + cost +
                ", tradeDepict=" + tradeDepict +
                ", bName='" + bName + '\'' +
                ", tradeStatus=" + tradeStatus +
                ", time='" + time + '\'' +
                ", tradeType=" + tradeType +
                ", tradeAddress='" + tradeAddress + '\'' +
                ", items=" + items +
                '}';
    }

    public void setItems(List<CurrencyTradingItemEntity> items) {
        this.items = items;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getIsNext() {
        return isNext;
    }

    public void setIsNext(int isNext) {
        this.isNext = isNext;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(int tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTradeDepict() {
        return tradeDepict;
    }

    public void setTradeDepict(int tradeDepict) {
        this.tradeDepict = tradeDepict;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeAddress() {
        return tradeAddress;
    }

    public void setTradeAddress(String tradeAddress) {
        this.tradeAddress = tradeAddress;
    }
    
}
