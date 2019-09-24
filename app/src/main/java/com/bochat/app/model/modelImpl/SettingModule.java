package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.CheckPwdSettingEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.RealNameAuthEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

/**
 * 2019/4/18
 * Author LDL
 **/
public class SettingModule implements ISettingModule {

    @Override
    public CodeEntity setLoginPwd(String password) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).setLoginPwd(Api.setLoginPwd,MD5Util.lock(password)));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setCode(httpClientEntity.getCode());
        }
        return codeEntity;
    }


    @Override
    public CodeEntity updateLogin(String oldPassword, String newPassword) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updateLogin(Api.updateLogin,MD5Util.lock(oldPassword),MD5Util.lock(newPassword) ));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity forgetLogin(String phone, String code, String password) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).forgetLogin(Api.forgetLogin, phone, code, MD5Util.lock(password) ));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }

    @Override
    public CheckPwdSettingEntity getPassWord(String userId) {
        CheckPwdSettingEntity checkPwdSettingEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CheckPwdSettingEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getPassWord(Api.getPassWord,userId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            checkPwdSettingEntity=httpClientEntity.getObj();
        }else{
            checkPwdSettingEntity=new CheckPwdSettingEntity();
            checkPwdSettingEntity.setCode(httpClientEntity.getCode());
            checkPwdSettingEntity.setRetcode(httpClientEntity.getCode());
            checkPwdSettingEntity.setMsg(httpClientEntity.getMessage());
        }
        return checkPwdSettingEntity;
    }

    @Override
    public CodeEntity updatePhone(String phone, String code) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updatePhone(Api.updatePhone,phone,code));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CheckPwdSettingEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }
    
    @Override
    public CodeEntity authentication(String idCard, String name) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).authentication(Api.authentication,idCard,name));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }

    @Override
    public RealNameAuthEntity getAuthentication(String userId) {
        RealNameAuthEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RealNameAuthEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).authDetail(Api.authDetail, userId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity = new RealNameAuthEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }
    
    @Override
    public CodeEntity setTradePwd(String password, int type) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).setTradePwd(Api.setTradePwd,MD5Util.lock(password),type));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CheckPwdSettingEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }

    @Override
    public CodeEntity updateTradePwd(String oldPassword, String newPassword) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).updateTradePwd(Api.updateTradePwd, MD5Util.lock(oldPassword),MD5Util.lock(newPassword)));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CheckPwdSettingEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
        }
        return codeEntity;
    }

}
