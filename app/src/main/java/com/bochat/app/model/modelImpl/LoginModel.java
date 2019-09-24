package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.ILoginModel;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.HttpResponseBean;
import com.bochat.app.model.net.JsonConvert;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.model.util.TextUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * 2019/4/10
 * Author LDL
 **/
public class LoginModel implements ILoginModel {
    
    @Override
    public UserEntity login(final String username, final String code, final String password, final int loginType){
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("username", username);
            params.put("loginType", loginType);
            if(loginType == 1){
                params.put("code", code);
            }else{
                params.put("password", MD5Util.lock(password));
            }
            RequestBody requestBody  = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(params));
            Call<String> call= HttpClient.getInstance().retrofit().create(RetrofitService.class).requestPost(Api.LOGIN_URL, requestBody);
           
            HttpClientEntity httpClientEntity = new HttpClientEntity();
            Response<String> response=call.execute();
            HttpResponseBean httpResponseBean= JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
            LogUtil.LogDebug("login onResponse="+response.body());
            httpClientEntity.setCode(httpResponseBean.getRetcode());
            httpClientEntity.setMessage(httpResponseBean.getMsg());
            
            if(httpResponseBean.getRetcode() == 0){
                String responseStr = response.body();
                if(!TextUtil.isEmptyString(responseStr)){
                    httpClientEntity.setJsonData(responseStr);
                    try{
                        httpClientEntity.setObj(JsonConvert.analysisJson(responseStr, UserEntity.class, "data"));
                        return (UserEntity)httpClientEntity.getObj();
                    }catch (Exception e2){
                        e2.printStackTrace();
                    }
                }
            }
            UserEntity userEntity=new UserEntity();
            userEntity.setCode(httpClientEntity.getCode());
            userEntity.setRetcode(httpClientEntity.getCode());
            userEntity.setMsg(httpClientEntity.getMessage());
            return userEntity;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    @Override
    public CodeEntity loginOut(String token) {
        CodeEntity codeEntity=new CodeEntity();
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class)
                .loginOut(Api.LOGIN_OUT,token));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity.setRetcode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }
}
