package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicFlashListEntity extends CodeEntity implements Serializable {





    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int isNext;
    private int totalPage;
    private List<DynamicFlashEntity> items;

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

    public List<DynamicFlashEntity> getItems() {
        return items;
    }

    public void setItems(List<DynamicFlashEntity> items) {
        this.items = items;
    }


}