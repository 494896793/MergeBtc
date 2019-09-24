package com.bochat.app.model.event;

import com.bochat.app.model.bean.FriendApplyEntity;

/**
 * 2019/4/23
 * Author ZZW
 **/
public class DealAddFriendEvent {

    public FriendApplyEntity friendApplyEntityl;


    public DealAddFriendEvent(FriendApplyEntity friendApplyEntityl){
        this.friendApplyEntityl=friendApplyEntityl;
    }

}
