package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class NewGroupManagerListEntity extends CodeEntity implements Serializable {


    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {

        private int currentPage;
        private int pageSize;
        private int totalCount;
        private int isNext;
        private int totalPage;
        private List<NewGroupManagerEntivity> items;

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

        public List<NewGroupManagerEntivity> getItems() {
            return items;
        }

        public void setItems(List<NewGroupManagerEntivity> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "ItemBean{" +
                    "currentPage=" + currentPage +
                    ", pageSize=" + pageSize +
                    ", totalCount=" + totalCount +
                    ", isNext=" + isNext +
                    ", totalPage=" + totalPage +
                    ", items=" + items +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewGroupManagerListEntity{" +
                "item=" + item +
                '}';
    }
}
