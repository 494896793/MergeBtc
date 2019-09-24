package com.bochat.app.model.net;


import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.bochat.app.MainApplication;
import com.bochat.app.R;
import com.bochat.app.app.view.ToastHelper;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.ResponseCode;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.model.util.NetworkUtil;
import com.bochat.app.model.util.QuotationApi;
import com.bochat.app.model.util.TextUtil;
import com.bochat.app.model.util.TimeUtils;
import com.bochat.security.Encrypt;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.bochat.app.model.constant.Constan.CONNECT_TIMEOUT;
import static com.bochat.app.model.constant.Constan.READ_TIME_OUT;
import static com.bochat.app.model.constant.Constan.WRITE_TIME_OUT;


/**
 * Created by ldl on 2019/4/9.
 */

public class QuotationHttpClient {

    private static QuotationHttpClient httpClient;
    private Retrofit retrofit;

    public static QuotationHttpClient getInstance() {
        if (httpClient == null) {
            httpClient = new QuotationHttpClient();
        }
        return httpClient;
    }
    
    public static void release(){
        httpClient = null;
    }
    
    public Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(QuotationApi.BASE_URL)
                    .client(new OkHttpClient.Builder().
                            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).
                            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                            //                        .addNetworkInterceptor(new HeaderInterceptor())
                            //                        .addInterceptor(new AuthInterceptor())
                            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS).build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    
    private QuotationHttpClient() {
        retrofit = new Retrofit.Builder().baseUrl(QuotationApi.BASE_URL)
                .client(new OkHttpClient.Builder().
                        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).
                        readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                        .addNetworkInterceptor(new HeaderInterceptor())
                        .addInterceptor(new AuthInterceptor())
                        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS).build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public Object sends(Type type, String key, final Call<String> call) {
        HttpClientEntity httpClientEntity = new HttpClientEntity();
        Object data = httpClientEntity;
        if (NetworkUtil.isNetworkConnected(MainApplication.getInstance())) {
            try { 
                Response<String> response = call.execute();
                if (response != null) {
//                    JsonWarnUtils.analysisJson(false,response.body());
                    LogUtil.LogDebug("enqueue onResponse=" + response.body());
                    HttpResponseBean httpResponseBean = JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
                    if (httpResponseBean == null) {
                        httpClientEntity.setCode(-1);
                        httpClientEntity.setMessage("请求失败");
                    } else {
                        httpClientEntity.setCode(httpResponseBean.getRetcode());
                        httpClientEntity.setMessage(httpResponseBean.getMsg());
                        httpClientEntity.setCount(httpResponseBean.getCount());
                        if (httpResponseBean.getRetcode() == 0) {
                            String responseStr = response.body();
                            if (!TextUtil.isEmptyString(responseStr)) {
                                httpClientEntity.setJsonData(responseStr);
                                if (type != null) {
                                    if (key != null) {
                                        try {
                                            httpClientEntity.setObj(JsonConvert.analysisJson(responseStr, type, key));
                                        } catch (Exception q) {
                                            q.printStackTrace();
                                        }
                                    } else {
                                        httpClientEntity.setObj(JsonConvert.analysisJson(responseStr, type));
                                    }
                                }
                            } else {
                                //// TODO: 2017/5/22  待优化
                                responseStr = "{}";
                                httpClientEntity.setCodeMsg(ResponseCode.RESPONSE_BODY_NULL);
                                httpClientEntity.setJsonData(responseStr);
                            }
                        } else {
                            httpClientEntity.setMessage(httpResponseBean.getMsg());
                        }
                    }
                    data = httpClientEntity;
                } else {
                    HttpClientEntity httpClientEntity2 = new HttpClientEntity();
                    httpClientEntity2.setCode(-1);
                    httpClientEntity2.setMessage("请求失败");
                    data = httpClientEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Looper.prepare();
                Toast toast = ToastHelper.makeToast(MainApplication.getInstance(), "请检查网络", R.mipmap.bg_tips_fail);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            data = httpClientEntity;
        }
        if(data==null){
            if(httpClientEntity==null){
                httpClientEntity=new HttpClientEntity();
            }
            httpClientEntity.setCode(-1);
            data=httpClientEntity;
        }
        return data;
    }

    public Object sends(Type type, final Call<String> call) {
        return sends(type, null, call);
    }

    // 请求拦截器
    public static class HeaderInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            UserEntity userEntity = CachePool.getInstance().user().getLatest();
            if (userEntity != null && userEntity.getToken() != null) {
                String token = userEntity.getToken();

                long time = System.currentTimeMillis() - TimeUtils.getNetworkTimeOffset();
                int random = (int)(Math.random() * 1000000);
                if(random < 100000) {
                    random = random + 100000;
                }
                //  String channel = WalletApp.instance.getChannel();
                Request updateRequest = originalRequest.newBuilder()
                        .header("Authorization", token)
                        .header("timestamp", String.valueOf(time))
                        .header("nonce", String.valueOf(random))
                        .header("sign", Encrypt.nativeSHA1(String.valueOf(time), String.valueOf(random)))
                        .build();
                return chain.proceed(updateRequest);
            } else {
                return chain.proceed(originalRequest);
            }
        }
    }
    
    // 返回拦截器
    private static class AuthInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = null;
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            //            String path=originalResponse.request().url().url().getPath();
            //            if(path.equals("/"+LOGIN_URL)){
            //                String authorization = originalResponse.header("Authorization", null);
            //                if (!TextUtils.isEmpty(authorization)) {
            //                    UserEntity userEntity=DBManager.getInstance().getUserInfo();
            //                    if(userEntity!=null){
            //                        userEntity.setToken(authorization);
            //                        DBManager.getInstance().saveOrUpdateUser(userEntity);
            //                        Request newRequest = chain.request().newBuilder().header("Authorization", authorization)
            //                                .build();
            //
            //                        // retry the request
            //                        originalResponse.body().close();
            //                        response=chain.proceed(newRequest);
            //                    }else{
            //                        response=originalResponse;
            //                    }
            //                }else{
            //                    response=originalResponse;
            //                }
            //            }else{
            //                response=originalResponse;
            //            }

            //            String authorization = originalResponse.networkResponse().request().header("Authorization");
            //            UserEntity userEntity=DBManager.getInstance().getUserInfo();
            //            if(authorization!=null){
            //                userEntity.setToken(authorization);
            //                DBManager.getInstance().saveOrUpdateUser(userEntity);
            //            }
            /*
            String authorization = originalResponse.header("Authorization", null);
            if (!TextUtils.isEmpty(authorization)) {
                HashSet<String> cookies = new HashSet<>();
                for (String header : originalResponse.headers("Authorization")) {
                    cookies.add(header);
                    WalletApp.instance.setAuth(header);
                }
            }
             */
            return originalResponse;
        }
    }
}