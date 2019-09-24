package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicPlushEntity extends CodeEntity implements Serializable {

    @Override
    public String toString() {
        return "DynamicPlushEntity{" +
                "meet=" + meet +
                ", flash=" + flash +
                '}';
    }



    private MeetBean meet;
    private FlashBean flash;

    public MeetBean getMeet() {
        return meet;
    }

    public void setMeet(MeetBean meet) {
        this.meet = meet;
    }

    public FlashBean getFlash() {
        return flash;
    }

    public void setFlash(FlashBean flash) {
        this.flash = flash;
    }

    public static class MeetBean implements Serializable{


        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class FlashBean implements Serializable{
        /**
         * title : 1111
         * content : 一问三四五
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
