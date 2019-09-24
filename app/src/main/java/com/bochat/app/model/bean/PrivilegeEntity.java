package com.bochat.app.model.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class PrivilegeEntity extends CodeEntity {

    /**
     * 创世居民
     * 对应字段{@link PrivilegeEntity#privilegeType}
     */
    public static final int PRIVILEGE_TYPE_GENESIS_RESIDENTS = 0;
    /**
     * 红包大厅
     * 对应字段{@link PrivilegeEntity#privilegeType}
     */
    public static final int PRIVILEGE_TYPE_RED_ENVELOPE_HALL = 1;

    /**
     * 永久有效
     * 对应字段{@link PrivilegeEntity#dataType}
     */
    public static final int PRIVILEGE_DATE_TYPE_FOREVER = 0;
    /**
     * 有效字段
     * 对应字段{@link PrivilegeEntity#dataType}
     */
    public static final int PRIVILEGE_DATE_TYPE_VAIL_DATE = 1;

    /**
     * 删除
     * 对应字段{@link PrivilegeEntity#enableFlag}
     */
    public static final int PRIVILEGE_FLAG_DELETE = 0;

    /**
     * 正常
     * 对应字段{@link PrivilegeEntity#enableFlag}
     */
    public static final int PRIVILEGE_FLAG_NORMAL = 1;

    /**
     * 无效
     * 对应字段{@link PrivilegeEntity#enableFlag}
     */
    public static final int PRIVILEGE_FLAG_INVALID = 2;

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @IntDef({PRIVILEGE_TYPE_GENESIS_RESIDENTS, PRIVILEGE_TYPE_RED_ENVELOPE_HALL})
    @interface PrivilegeTypeDef {
    }

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @IntDef({PRIVILEGE_DATE_TYPE_FOREVER, PRIVILEGE_DATE_TYPE_VAIL_DATE})
    @interface PrivilegeDateTypeDef {
    }

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @IntDef({PRIVILEGE_FLAG_DELETE, PRIVILEGE_FLAG_NORMAL, PRIVILEGE_FLAG_INVALID})
    @interface PrivilegeEnableFlagDef {
    }


    /**
     * 特权Id
     */
    private long id;

    /**
     * 用户名
     */
    private String userId;

    /**
     * 特权名称
     */
    private String privilegeName;

    /**
     * 特权描述
     */
    private String privilegeDesc;

    /**
     * 特权类型 1.BX生息 2.红包大厅
     */
    private @PrivilegeTypeDef
    int privilegeType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 数据类型
     */
    private @PrivilegeDateTypeDef
    int dataType;

    /**
     * 背景图片
     */
    private String backgrooundImg;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 特权状态(1:正常；2:无效)
     */
    private @PrivilegeEnableFlagDef
    int enableFlag;

    /**
     * 跳转地址
     */
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeDesc() {
        return privilegeDesc;
    }

    public void setPrivilegeDesc(String privilegeDesc) {
        this.privilegeDesc = privilegeDesc;
    }

    public int getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(int privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public @PrivilegeDateTypeDef
    int getDataType() {
        return dataType;
    }

    public void setDataType(@PrivilegeDateTypeDef int dataType) {
        this.dataType = dataType;
    }

    public String getBackgrooundImg() {
        return backgrooundImg;
    }

    public void setBackgrooundImg(String backgrooundImg) {
        this.backgrooundImg = backgrooundImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public @PrivilegeEnableFlagDef
    int getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(@PrivilegeEnableFlagDef int enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean equalType(@PrivilegeTypeDef int type) {
        return privilegeType == type;
    }

    public boolean equalDateType(@PrivilegeDateTypeDef int dateType) {
        return dataType == dateType;
    }

    public boolean equalEnableFlag(@PrivilegeEnableFlagDef int flag) {
        return enableFlag == flag;
    }

    @Override
    public String toString() {
        return "PrivilegeEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", privilegeName='" + privilegeName + '\'' +
                ", privilegeDesc=" + privilegeDesc +
                ", privilegeType=" + privilegeType +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dataType=" + dataType +
                ", backgrooundImg='" + backgrooundImg + '\'' +
                ", createTime='" + createTime + '\'' +
                ", enableFlag=" + enableFlag +
                ", url='" + url + '\'' +
                '}';
    }
}
