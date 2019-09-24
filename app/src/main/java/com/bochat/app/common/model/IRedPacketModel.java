package com.bochat.app.common.model;

import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketRecordListEntity;

/**
 * 2019/5/5
 * Author LDL
 **/
public interface IRedPacketModel {

    RedPacketEntity sendWelfare(double money, int count, int type, String text, String password, long groupId, int coin,String isSync);

    RedPacketPeopleEntity getWelfare(int welfareId,String userName);

    RedPacketRecordListEntity queryTakeRecord(int rewardId, int start, int offset);

}