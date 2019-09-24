package com.bochat.app.business.fetcher;

import com.bochat.app.common.fetcher.BaseEntityFetcher;
import com.bochat.app.common.fetcher.IEntityRequest;
import com.bochat.app.common.model.IThirdServicesModel;
import com.bochat.app.model.bean.BitMallEntity;
import com.bochat.app.model.bean.RealNameAuthEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/24 09:52
 * Description :
 */

public class BitMallLoginFetcher extends BaseEntityFetcher<BitMallLoginFetcher.BitMallLoginRequest, BitMallEntity> {
    private IThirdServicesModel thirdServicesModel;
    private NetworkProvider networkProvider;
    private BitMallEntity bitMallEntity;

    public BitMallLoginFetcher(IThirdServicesModel tokenAssetModel) {
        this.thirdServicesModel = tokenAssetModel;
        networkProvider = new NetworkProvider();
    }

    public static class BitMallLoginRequest implements IEntityRequest {

    }

    public class NetworkProvider extends DefaultProvider{
        @Override
        public List<BitMallEntity> getEntity(BitMallLoginRequest request) {
            bitMallEntity = thirdServicesModel.bitMallLogin();
            List<BitMallEntity> entities = new ArrayList<>();
            if (bitMallEntity != null){
                entities.add(bitMallEntity);
                return entities;
            }
            return super.getEntity(request);
        }
    }

    public NetworkProvider getNetworkProvider() {
        return networkProvider;
    }
}
