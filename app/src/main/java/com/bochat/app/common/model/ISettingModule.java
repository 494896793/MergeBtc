package com.bochat.app.common.model;

import com.bochat.app.model.bean.CheckPwdSettingEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.RealNameAuthEntity;

/**
 * 2019/4/18
 * Author LDL
 **/
public interface ISettingModule {

    /*设置登录密码*/
    public CodeEntity setLoginPwd(String password);
    /*修改登录密码*/
    public CodeEntity updateLogin(String oldPassword,String newPassword);
    /*忘记密码*/
    public CodeEntity forgetLogin(String phone,String code,String password);
    /*查询是否设置密码*/
    public CheckPwdSettingEntity getPassWord(String userId);
    /*更换手机号*/
    public CodeEntity updatePhone(String phone,String code);
    /*获取实名认证信息*/
    public RealNameAuthEntity getAuthentication(String userId);
    /*实名认证*/
    public CodeEntity authentication(String idCard,String name);        //测试通过
    /*设置支付密码
    类型type: 1:设置密码；2：忘记支付密码重置;*/
    public CodeEntity setTradePwd(String password,int type);
    /*修改支付密码*/
    public CodeEntity updateTradePwd(String oldPassword,String newPassword);
}
