package com.bochat.app.app.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.GroupMentioneAdapeter;
import com.bochat.app.app.util.UriUtil;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.GroupMentionedSeleteContract;
import com.bochat.app.common.router.RouterGroupMentionedSelete;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.view.BaseActivity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.mention.RongMentionManager;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterGroupMentionedSelete.PATH)
public class GroupMentionedSeleteActivity extends BaseActivity<GroupMentionedSeleteContract.Presenter> implements GroupMentionedSeleteContract.View, GroupMentioneAdapeter.OnGroupMentioneItemListener {
    @Inject
    GroupMentionedSeleteContract.Presenter presenter;
    @BindView(R.id.select_mentione_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;
    private List<UserInfo> userInfoList;
    private GroupMentioneAdapeter adapeter;
    private String mTagetId;
    private String userId;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupMentionedSeleteContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_mentione);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        UserEntity latest = CachePool.getInstance().loginUser().getLatest();
        userId = latest.getId()+"";

        bochat_topbar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                super.onTextExtButtonClick();

                TextMessage textMessage = TextMessage.obtain("@所有人");


                MentionedInfo mentionedInfo = new MentionedInfo(MentionedInfo.MentionedType.ALL, null, null);
                textMessage.setMentionedInfo(mentionedInfo);


                RongIM.getInstance().sendMessage(Message.obtain(mTagetId, Conversation.ConversationType.GROUP, textMessage), null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {

                    }

                    @Override
                    public void onSuccess(Message message) {

                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {

                    }
                });

                finish();
            }
        });




        userInfoList = new ArrayList<>();


        adapeter = new GroupMentioneAdapeter(this, userInfoList);
        adapeter.setOnGroupMentioneItemListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapeter);


    }

    @Override
    public void onUpdateMentioneList(List<GroupMemberEntity> userInfos, String tagetId) {
        mTagetId = tagetId;
        List<UserInfo> userlist = new ArrayList<>();
        for (int i = 0; i < userInfos.size(); i++) {
            if (userInfos.get(i).getMember_id() != 0 && userInfos.get(i).getMember_id() != Long.parseLong(userId)) {
                GroupMemberEntity entity = userInfos.get(i);
                userlist.add(new UserInfo(entity.getMember_id() + "", entity.getNickname(), Uri.parse(entity.getHead_img())));

            }
        }

        adapeter.add(userlist);
    }

    @Override
    public void onItemOnclik(UserInfo userInfo) {
//        RongMentionManager.getInstance().mentionMember(userInfo);


        List<String> userIdList = new ArrayList<>();
        userIdList.add(userInfo.getUserId());

        TextMessage textMessage = TextMessage.obtain("@" + userInfo.getName());

        MentionedInfo mentionedInfo = new MentionedInfo(MentionedInfo.MentionedType.PART, userIdList, userInfo.getName());
        textMessage.setMentionedInfo(mentionedInfo);
        RongIM.getInstance().sendMessage(Message.obtain(mTagetId, Conversation.ConversationType.GROUP, textMessage), null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {

            }

            @Override
            public void onSuccess(Message message) {

            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {

            }
        });

        finish();
    }
}