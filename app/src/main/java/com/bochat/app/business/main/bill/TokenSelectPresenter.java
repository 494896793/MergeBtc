package com.bochat.app.business.main.bill;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.common.contract.bill.TokenSelectContract;
import com.bochat.app.common.router.RouterTokenSelect;
import com.bochat.app.model.bean.TokenEntity;
import com.bochat.app.model.bean.TokenListEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Author      : FJ
 * CreateDate  : 2019/4/26 0026 16:15
 * Description :
 */
public class TokenSelectPresenter extends BasePresenter<TokenSelectContract.View> implements TokenSelectContract.Presenter {
    
    private TokenEntity userCurrencyEntity;
    private boolean isStartTokenEntity;
    private RouterTokenSelect routerTokenSelect;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        routerTokenSelect = getExtra(RouterTokenSelect.class);
        isStartTokenEntity = routerTokenSelect.isStartToken();
        boolean isIgnoreBx = routerTokenSelect.isIgnoreBx();
        
        if(routerTokenSelect.getSelectToken() != null){
            userCurrencyEntity = routerTokenSelect.getSelectToken();
        }
        
        if (routerTokenSelect.getTokenList() != null) {
            TokenListEntity userCurrencyDataEntity = routerTokenSelect.getTokenList();
            List<TokenEntity> data = userCurrencyDataEntity.getData();
            if(!data.isEmpty()){
                ArrayList<TokenEntity> list = new ArrayList<>();
                for(int i = 0; i < data.size(); i++){
                    TokenEntity tokenEntity = data.get(i);
                    if(isIgnoreBx && "BX".equals(tokenEntity.getbName())){
                        continue;
                    }
                    if(userCurrencyEntity != null && tokenEntity.getbId() == userCurrencyEntity.getbId()){
                        continue;
                    }
                    list.add(data.get(i));
                }
                getView().updateList(list);
            }
        }
    }

    @Override
    public void onItemClick(TokenEntity data) {
        if(routerTokenSelect != null){
            routerTokenSelect.back(data, isStartTokenEntity);
        }
        getView().finish();
    }
}
