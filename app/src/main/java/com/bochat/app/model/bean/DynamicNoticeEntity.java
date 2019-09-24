package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/6
 * Author LDL
 **/
public class DynamicNoticeEntity extends CodeEntity implements Serializable {


    /**
     * id : 6
     * type :
     * title : BoChat官方公告
     * source : BoChat
     * content : <p>BUG修复：</p><p>1.解散群聊后群聊仍然出现在消息列表的缓存问题</p><p>2.优化闪退问题</p><p>3.优化IM稳定性及网络波动造成的访问异常和请求繁忙功能</p><p>4.BX生息，锁仓，部分界面更新</p><p>5.修复收发通证问题</p>
     * releases :
     * releaseTime : 2019-06-18
     * updateTime :
     * isDelete :
     * picture :
     * group :
     * returnUrl : http://www.bochat.net/activity/announcement.html
     */

    private int id;
    private String type;
    private String title;
    private String source;
    private String content;
    private String releases;
    private String releaseTime;
    private String updateTime;
    private String isDelete;
    private String picture;
    private String group;
    private String returnUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleases() {
        return releases;
    }

    public void setReleases(String releases) {
        this.releases = releases;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String toString() {
        return "DynamicNoticeEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", content='" + content + '\'' +
                ", releases='" + releases + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", picture='" + picture + '\'' +
                ", group='" + group + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                '}';
    }
}
