package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/5/6
 * Author LDL
 **/
public class RedPacketRecordListEntity extends CodeEntity implements Serializable {

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int isNext;
    private int totalPage;
    private List<RedPacketRecordEntity> items;

    @Override
    public String toString() {
        return "RedPacketRecordListEntity{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", isNext=" + isNext +
                ", totalPage=" + totalPage +
                ", items=" + items +
                '}';
    }

    public int getCurrentPage() {
        return currentPage;
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

    public List<RedPacketRecordEntity> getItems() {
        return items;
    }

    public void setItems(List<RedPacketRecordEntity> items) {
        this.items = items;
    }
}
