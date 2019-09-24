package com.bochat.app.common.model;


import com.bochat.app.model.bean.Address;
import com.bochat.app.model.bean.AddressTeamListEntity;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.InviteCodeEntity;
import com.bochat.app.model.bean.OnLineEntity;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.bean.ShakyStatuEntity;
import com.bochat.app.model.bean.UserEntity;

import java.util.List;

/**
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/08 13:52
 * Description:
 */

public interface IUserModel {



    /*查询用户信息*/
    public UserEntity getUserInfo();

//    获取好友列表
//    start 初始页
//    offset 每页条数
    public FriendListEntity getFriendListInfo(String userId, String start, String offset);

    /*查询好友信息*/
    public FriendListEntity getFriendInfo(String userId,int start,int offset);

//    处理好友请求
//    apply_id 申请编号
//    apply_state :1-同意   2-拒绝
    public CodeEntity dealFriendApply(String apply_id,String apply_state,String refuse_text);

//    发送添加申请
//    target_id:1-同意    2-拒绝
    public CodeEntity sendAddFriendApply(String proposer_id,String target_id,String refuse_text,String apply_from);

    /*查询好友申请列表/ 查询我申请的列表*/
    public List<FriendApplyEntity> getAllFriendApply(String userId,int start,int offset);

    /*检查用户在线状态*/
    public OnLineEntity checkUserOnlineStatu(String userId);

    /*修改好友备注*/
    public CodeEntity changeFriendInfo(int userId,String note,int friendId);

    public CodeEntity updateUserInfo(String nickname,int sex,String birthday,String area,String mySign,String headImg);

//    删除好友：			拉黑（取消）好友：		屏蔽好友：			取消屏蔽好友：
//    type : 1				type:  2					type:  3				type：3
//    targetId: 				targetId:					targetId:				targetId:
//    black: 拉黑时传		  relation: token		  realation:好友token
    public CodeEntity updateFriendRelation(String targetId,String type,String relation,String black);

    /*拉黑*/
    public CodeEntity blockFriend(String targetId,String type,String black);

    /*取消拉黑*/
    public CodeEntity unBlockFriend(String targetId,String type);

    /*删除*/
    public CodeEntity deleteFriend(String targetId,String type);

    /*屏蔽好友或者解除屏蔽*/
    public CodeEntity shieldFriend(String type,String targetId,String relation);

    /*获取地区列表*/
    public Address getArea(String userId);

    /*添加好友
     userToken:自己的融云token
     targetToken:好友融云token
     targetId:好友编号*/
    public CodeEntity addFriend(String targetId);

    /*获取银行卡列表*/
    public BankCardListEntity getBank(String userId);               //测试通过

    /*绑定银行卡*/
    public CodeEntity bindBank(int bankId,String bankNo,String bankBranch);     //测试通过

    /*解绑银行卡*/
    public CodeEntity unbindUserBank(int bankId);       //测试通过

    /*查询用户银行卡*/
    public BankCardListEntity getUserBank(int type);            //测试通过

    public InviteCodeEntity getUserInviteCode();

    /*判断领糖果活动是否开启*/
    public ShakyStatuEntity getSweetsActivity();
    /*领取糖果接口*/
    public ShakyCandyEntity insertActivityRecord();
    /*领取糖果接口*/
    public ShakyCandyEntity getActivityRecord();

    /*查询通讯录团队列表*/
    public AddressTeamListEntity queryTeamMembers(String currentPage,String pageSize);



    
}
