package com.bochat.app.business.fetcher;

import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.fetcher.BaseEntityFetcher;
import com.bochat.app.common.fetcher.IEntityRequest;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.greendao.BoChatDbHelper;
import com.bochat.app.model.greendao.FriendEntityDao;

import java.util.List;

import javax.inject.Inject;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/19 10:00
 * Description :
 */

public class FriendEntityProvider extends BaseEntityFetcher<FriendEntityProvider.FriendEntityRequest, FriendEntity> {
    
    @Inject
    IUserModel userModel;
    
    public class FriendEntityRequest implements IEntityRequest {
        
        private String userId;
        private String start;
        private String offset;
        
        private String friendId;
        
        public FriendEntityRequest(String userId, String start, String offset) {
            this.userId = userId;
            this.start = start;
            this.offset = offset;
        }

        public FriendEntityRequest(String friendId) {
            this.friendId = friendId;
        }

        public String getUserId() {
            return userId;
        }

        public String getStart() {
            return start;
        }

        public String getOffset() {
            return offset;
        }

        public String getFriendId() {
            return friendId;
        }
    }
    
    public class NetworkHanler extends DefaultProvider {

        @Override
        public List<FriendEntity> getEntity(FriendEntityRequest request) {
            FriendListEntity friends = userModel.getFriendListInfo(request.getUserId(), request.getStart(), request.getOffset());
            if(friends != null && friends.getRetcode() == 0 && friends.getItems() != null){
                return friends.getItems();
            }
            return null;
        }

        @Override
        public void setEntity(FriendEntityRequest request, List<FriendEntity> entity) {
        }
    }
    
    public class CacheHanler extends DefaultProvider {

        @Override
        public List<FriendEntity> getEntity(FriendEntityRequest request) {
            if ("0".equals(request.getStart()) && "0".equals(request.getOffset())){
                return BoChatDbHelper.getInstance().getFriendEntityDao().queryBuilder().list();
                
            } else if(request.getFriendId() != null) {
                return BoChatDbHelper.getInstance().getFriendEntityDao().queryBuilder().where(
                        FriendEntityDao.Properties.Id.eq(request.getFriendId())).list();
            }
            return null;
        }

        @Override
        public void setEntity(FriendEntityRequest request, List<FriendEntity> entity) {
            FriendEntityDao friendEntityDao = BoChatDbHelper.getInstance().getFriendEntityDao();
            if ("0".equals(request.getStart()) && "0".equals(request.getOffset())){
                friendEntityDao.deleteAll();
                friendEntityDao.insertOrReplaceInTx(entity);
                
            } else if(request.getFriendId() != null) {
                friendEntityDao.insertOrReplaceInTx(entity);
            }
        }
    }
}
