package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class AddressTeamListEntity extends CodeEntity implements Serializable {


    private String currentPage;
    private String pageSize;
    private int totalCount;
    private int isNext;
    private String totalPage;
    private List<TeamEntity> items;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<TeamEntity> getItems() {
        return items;
    }

    public void setItems(List<TeamEntity> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AddressTeamListEntity{" +
                "currentPage='" + currentPage + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", totalCount=" + totalCount +
                ", isNext=" + isNext +
                ", totalPage='" + totalPage + '\'' +
                ", items=" + items +
                '}';
    }
}
