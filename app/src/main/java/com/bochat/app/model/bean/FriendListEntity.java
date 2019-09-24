package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/15
 * Author LDL
 **/
public class FriendListEntity   extends CodeEntity implements Serializable {

    public List<FriendEntity> items;
    public int currentPage;
    public int pageSize;
    public int totalCount;
    public int isNext;
    public int totalPage;

    public List<FriendEntity> getData() {
        return data;
    }

    public void setData(List<FriendEntity> data) {
        this.data = data;
    }

    public List<FriendEntity> data;

    public List<FriendEntity> getItems() {
        return items;
    }

    public void setItems(List<FriendEntity> items) {
        this.items = items;
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

    @Override
    public String toString() {
        return "FriendListEntity{" +
                "items=" + items +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", isNext=" + isNext +
                ", totalPage=" + totalPage +
                '}';
    }
}
