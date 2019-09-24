package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/5/23
 * Author LDL
 **/
public class ViewSpotListEntity extends CodeEntity implements Serializable {

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int isNext;     //是否有下一页 0-否；1-是
    private int totalPage;
    private List<ViewSpotItemEntity> items;

    @Override
    public String toString() {
        return "ViewSpotListEntity{" +
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

    public List<ViewSpotItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ViewSpotItemEntity> items) {
        this.items = items;
    }
}
