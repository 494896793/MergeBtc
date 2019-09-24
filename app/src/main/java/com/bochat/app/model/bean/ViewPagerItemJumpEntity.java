package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/8
 * Author LDL
 **/
public class ViewPagerItemJumpEntity implements Serializable {

    public boolean linkJump;
    public String index;
    public boolean isJump;

    public ViewPagerItemJumpEntity(){

    }

    public ViewPagerItemJumpEntity(String index,boolean linkJump,boolean isJump){
        this.linkJump=linkJump;
        this.index=index;
        this.isJump=isJump;
    }

}
