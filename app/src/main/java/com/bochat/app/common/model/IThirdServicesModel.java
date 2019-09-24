package com.bochat.app.common.model;

import com.bochat.app.model.bean.BitMallEntity;
import com.bochat.app.model.bean.GameGoEntity;

/**
 * 2019/5/30
 * Author LDL
 **/
public interface IThirdServicesModel {

    GameGoEntity gameLogin();
    
    BitMallEntity bitMallLogin();

}
