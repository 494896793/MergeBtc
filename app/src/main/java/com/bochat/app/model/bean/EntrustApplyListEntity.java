package com.bochat.app.model.bean;

import java.util.List;

public class EntrustApplyListEntity extends CodeEntity {

    private int currentPage;
    private int pageSize;

    private int totalCount;
    private int isNext;
    private int totalPage;
    private List<EntrustApplyEntity> items;

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

    public List<EntrustApplyEntity> getItems() {
        return items;
    }

    public void setItems(List<EntrustApplyEntity> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "EntrustApplyListEntity{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", isNext=" + isNext +
                ", totalPage=" + totalPage +
                ", items=" + items +
                '}';
    }
}
