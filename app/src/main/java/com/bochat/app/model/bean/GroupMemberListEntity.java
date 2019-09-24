package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/26
 * Author LDL
 **/
public class GroupMemberListEntity extends CodeEntity implements Serializable {

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int isNext;
    private int totalPage;
    private List<GroupMemberEntity> items;
    private List<GroupMemberEntity> data;

    public List<GroupMemberEntity> getData() {
        return data;
    }

    public void setData(List<GroupMemberEntity> data) {
        this.data = data;
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

    public List<GroupMemberEntity> getItems() {
        return items;
    }

    public void setItems(List<GroupMemberEntity> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GroupMemberListEntity{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", isNext=" + isNext +
                ", totalPage=" + totalPage +
                ", items=" + items +
                '}';
    }
}
