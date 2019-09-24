package com.bochat.app.common.router;

/**
 * 2019/7/18
 * Author LDL
 **/
public class RouterRedHallDetail extends AbstractRouter {

    public static final String PATH ="/path/RouterRedHallDetail";
    private String rewardId;
    private String isReceive;       //0未领取 1 已领取

    public String getRewardId() {
        return rewardId;
    }

    public String getIsReceive(){
        return isReceive;
    }


    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public RouterRedHallDetail(String rewardId,String isReceive){
        this.rewardId=rewardId;
        this.isReceive=isReceive;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
