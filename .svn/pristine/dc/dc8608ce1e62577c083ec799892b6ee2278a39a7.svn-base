package com.bochat.app.common.bean;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.util.PinYinUtil;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/18 14:44
 * Description :
 */

public class SearchedEntity implements Serializable {

    public static int TYPE_NONE = 0;
    public static int TYPE_FRIEND = 1;
    public static int TYPE_GROUP = 2;
    public static int TYPE_GROUP_MEMBER = 3;
    public static int TYPE_PINYIN = 4;
    

    private String name;
    private String id;
    private String icon;
    private String extra;
    private Object object;
    private int type;
    private String notFoundTips = "用户不存在";
    private String firstPinyin;
    
    public SearchedEntity(){
        type = TYPE_NONE;
    }

    public SearchedEntity(String firstPinyin) {
        this.firstPinyin = firstPinyin;
        type = TYPE_PINYIN;
    }

    public SearchedEntity(FriendEntity friendEntity){
        type = TYPE_FRIEND;
        object = friendEntity;
        setName(friendEntity.getNickname());
        setIcon(friendEntity.getHead_img());
        setId(String.valueOf(friendEntity.getId()));
        setExtra("");
        setNotFoundTips("用户不存在");
        setFirstPinyin(PinYinUtil.getFirstSpell(getName()).substring(0, 1).toUpperCase());
    }
    
    
    public SearchedEntity(GroupEntity groupEntity){
        type = TYPE_GROUP;
        object = groupEntity;
        setName(groupEntity.getGroup_name());
        setIcon(groupEntity.getGroup_head());
        setId(String.valueOf(groupEntity.getGroup_id()));
        setExtra(groupEntity.getLevel_num() + "人");
        setNotFoundTips("群聊不存在");
        setFirstPinyin(PinYinUtil.getFirstSpell(getName()).substring(0, 1).toUpperCase());
    }

    public SearchedEntity(GroupMemberEntity groupMemberEntity){
        type = TYPE_GROUP_MEMBER;
        object = groupMemberEntity;
        setName(groupMemberEntity.getNickname());
        setIcon(groupMemberEntity.getHead_img());
        setId(String.valueOf(groupMemberEntity.getMember_id()));
        setExtra("");
        setNotFoundTips("群成员不存在");
        setFirstPinyin(PinYinUtil.getFirstSpell(getName()).substring(0, 1).toUpperCase());
    }
    
    public String getFirstPinyin() {
        return firstPinyin;
    }

    public void setFirstPinyin(String firstPinyin) {
        this.firstPinyin = firstPinyin;
    }

    public String getNotFoundTips() {
        return notFoundTips;
    }

    public void setNotFoundTips(String notFoundTips) {
        this.notFoundTips = notFoundTips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SearchedEntity{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", icon='" + icon + '\'' +
                ", extra='" + extra + '\'' +
                ", object=" + object +
                ", type=" + type +
                ", notFoundTips='" + notFoundTips + '\'' +
                ", firstPinyin='" + firstPinyin + '\'' +
                '}';
    }
}
