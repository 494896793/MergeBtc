package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/20 15:08
 * Description :
 */

public class RouterHeaderDetail extends AbstractRouter {
    public static final String PATH ="/path/RouterHeaderDetail";
    
    private String headImage;

    public RouterHeaderDetail(String headImage) {
        this.headImage = headImage;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
