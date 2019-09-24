package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.DevelopingContract;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.modelImpl.UserModule;
import com.bochat.app.mvp.view.BaseFragment;

import javax.inject.Inject;


/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */

public class DevelopingFragment extends BaseFragment<DevelopingContract.Presenter> implements DevelopingContract.View{

    @Inject
    DevelopingContract.Presenter presenter;

    private View mLayoutView;

    private BoChatTopBar topBar;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected DevelopingContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mLayoutView == null) {
            mLayoutView = inflater.inflate(R.layout.fragment_developing, container, false);
            topBar = mLayoutView.findViewById(R.id.developing_top_bar);
            final UserModule userModule = new UserModule();
            UserEntity localUserInfo = userModule.getLocalUserInfo();
            topBar.setTitleText(localUserInfo.getNickname()+"("+localUserInfo.getId()+")");

            Button button = mLayoutView.findViewById(R.id.developing_out_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ALog.d("logout!!!!!!");
                    CachePool.getInstance().user().clear();
                    Router.navigation(new RouterLogin());
                }
            });
        }
        return mLayoutView;
    }

    //    @Subscribe
//    public void onEventMainThread(RechargeEntity dddddEntity){
//        PayReq req = new PayReq();
//        req.appId           =  Constan.WECHAT_APPID;//你的微信appid
//        req.partnerId       = "1900000109";//商户号
//        req.prepayId        = "WX1217752501201407033233368018";//预支付交易会话ID
//        req.nonceStr        = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";//随机字符串
//        req.timeStamp       = "1412000000";//时间戳
//        req.packageValue    = "Sign=WXPay";     //扩展字段,这里固定填写Sign=WXPay
//        req.sign            =  dddddEntity.getSign();//签名
////              req.extData         = "app data"; // optional
//        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//        api.sendReq(req);
//    }


    @Override
    protected void initWidget() {

    }

}