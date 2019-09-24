package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.ISendMessageModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

/**
 * 2019/4/10
 * Author LDL
 **/
public class SendMessageModel implements ISendMessageModel {
    
    @Override
    public CodeEntity sendMessage(final String phone, final int type) {
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).sendCode(Api.GET_CODE, phone, type));
        if(httpClientEntity!=null) {
            if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
                return (CodeEntity) httpClientEntity.getObj();
            } else {
                CodeEntity codeEntity = new CodeEntity();
                codeEntity.setMsg(httpClientEntity.getMessage());
                codeEntity.setCode(httpClientEntity.getCode());
                codeEntity.setRetcode(httpClientEntity.getCode());
                return codeEntity;
            }
        }else{
            CodeEntity codeEntity = new CodeEntity();
            codeEntity.setMsg("请求失败");
            codeEntity.setCode(-1);
            codeEntity.setRetcode(-1);
            return codeEntity;
        }
    }

    @Override
    public CodeEntity checkMessage(final String phone, final String type, final String code) {
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,null,
                HttpClient.getInstance().retrofit().create(RetrofitService.class).checkCode(Api.CHECK_CODE, phone, type,code));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            return (CodeEntity)httpClientEntity.getObj() ;
        }else{
            CodeEntity codeEntity=new CodeEntity();
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setRetcode(httpClientEntity.getCode());
            return codeEntity;
        }
    }
}
