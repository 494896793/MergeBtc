package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 14:04
 * Description :
 */

public class RouterBankCardList extends AbstractRouter {
    public static final String PATH ="/path/RouterBankCardList";
    private boolean isOnclickFinish;

    public RouterBankCardList (){
    }

    public RouterBankCardList(boolean isOnclickFinish) {
        this.isOnclickFinish = isOnclickFinish;
    }

    public boolean getOnclickFinish() {
        return isOnclickFinish;
    }

    public void setOnclickFinish(boolean onclickFinish) {
        isOnclickFinish = onclickFinish;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
