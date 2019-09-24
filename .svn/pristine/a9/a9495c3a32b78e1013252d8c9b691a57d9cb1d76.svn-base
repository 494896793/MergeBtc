package com.bochat.app.business.main.conversation;

import android.net.Uri;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.GroupMentionedSeleteContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.RouterGroupMentionedSelete;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupForbiddenListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.model.UserInfo;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupMentionedSeletePresenter extends BasePresenter<GroupMentionedSeleteContract.View> implements GroupMentionedSeleteContract.Presenter {
    @Inject
    IGroupModule groupModule;
    private List<UserInfo> userInfoList;
    private List<GroupMemberEntity> list;
    private String mGroupId;
    private GroupMemberProvider provider;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterGroupMentionedSelete extra = getExtra(RouterGroupMentionedSelete.class);
        if (extra != null) {
            if (userInfoList != null) {
//                userInfoList.clear();
            }

            mGroupId = extra.getGroupId();
//            userInfoList = initGroupMember(mGroupId);
            if (provider == null){
                provider = new GroupMemberProvider(groupModule);

                Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
                        try {
                            list = provider.getGroupMemberList(Long.valueOf(mGroupId),false);
                            emitter.onNext(emitter);
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                            e.printStackTrace();
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object Object) throws Exception {
                        list.size();
                       /* for (int i = 0;i<list.size();i++){
                            if (list.get(i).getMember_id() != 0){
                                GroupMemberEntity entity = list.get(i);
                                userInfoList.add(new UserInfo(entity.getMember_id() + "", entity.getNickname(), Uri.parse(entity.getHead_img())));

                            }
                        }*/
                        getView().onUpdateMentioneList(list, mGroupId);
                        getView().hideLoading("");
                    }
                }, new RxErrorConsumer<Throwable>(this) {

                    @Override
                    public void acceptError(Throwable throwable) {
                        getView().hideLoading("");
                    }
                });
                getView().showLoading(subscribe);

            }

        }


    }



}
