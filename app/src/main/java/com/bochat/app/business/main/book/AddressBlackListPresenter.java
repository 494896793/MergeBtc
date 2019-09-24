package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.book.AddressPublicContract;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/30 09:20
 * Description :
 */

public class AddressBlackListPresenter extends BasePresenter<AddressPublicContract.View> implements AddressPublicContract.Presenter {
   
    @Override
    public void requestBlackList() {
        getView().updateBlackList(createTestList());
    }

    @Override
    public void onBlackListItemClick(FriendEntity blackListItem) {

    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }


    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        getView().updateBlackList(createTestList());
    }
    
    private ArrayList<FriendEntity> createTestList(){
        ArrayList<FriendEntity> list = new ArrayList<>();
//        for(int i = 0; i < 10000; i++){
//            FriendEntity friendEntity = new FriendEntity();
//            friendEntity.setNickname("好友" + i);
//            friendEntity.setHead_img("http://bochatoss.oss-cn-beijing.aliyuncs.com/headImg/IMG_20190512_214544.jpg/45515e50-41c0-4042-9375-dc5fea431abe.jpg");
//            friendEntity.setId(100000 + i);
//            list.add(friendEntity);
//        }
        return list;
    }
}
