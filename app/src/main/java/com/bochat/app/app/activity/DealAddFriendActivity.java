package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.DealAddFriendContract;
import com.bochat.app.common.router.RouterDealApply;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bochat.app.app.view.SpImageView.TYPE_ROUND;

/**
 * 2019/4/22
 * Author LDL
 **/
@Route(path = RouterDealApply.PATH)
public class DealAddFriendActivity extends BaseActivity<DealAddFriendContract.Presenter> implements DealAddFriendContract.View {
    
    FriendApplyEntity friendApply;

    GroupApplyServerEntity groupApply;
    
    @Inject
    DealAddFriendContract.Presenter presenter;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;

    @BindView(R.id.head_img)
    SpImageView head_img;

    @BindView(R.id.userName)
    TextView userName;

    @BindView(R.id.info_tx)
    TextView info_tx;

    @BindView(R.id.refuse_bt)
    Button refuse_bt;

    @BindView(R.id.accept_bt)
    Button accept_bt;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected DealAddFriendContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dealaddfriend);
    }

    @OnClick({R.id.accept_bt,R.id.refuse_bt})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.accept_bt:
                if(friendApply != null) {
                    presenter.acceptFriend(friendApply.getProposer_id(), CachePool.getInstance().user().getLatest().getNickname()+"\t添加你为好友，现在可以聊天啦",friendApply.getSourceType());
                } else {
                    presenter.acceptGroup(groupApply);
                }
                break;
            case R.id.refuse_bt:
                if(friendApply != null){
                    presenter.refuseFriend(friendApply.getProposer_id(), "拒绝添加好友",friendApply.getSourceType());
                } else {
                    presenter.refuseGroup(groupApply);
                }
                break;
        }
    }

    @Override
    protected void initWidget() {
        bochat_topbar.setRightButtonInvisble(true);
        bochat_topbar.setReturnBtVisible(true);
    }

    @Override
    public void onRefresh(FriendApplyEntity friendApplyEntity) {
        groupApply = null;
        bochat_topbar.setTitleText("好友请求");
        friendApply = friendApplyEntity;
        userName.setText(friendApply.getNickname());
        info_tx.setText(friendApply.getApply_text());
        head_img.setType(TYPE_ROUND);
        head_img.setRoundRadius(getResources().getDimensionPixelSize(R.dimen.dp_10));
        Glide.with(this).load(friendApply.getHead_img()).error(R.mipmap.ic_default_head).into(head_img);
    }

    @Override
    public void onRefresh(GroupApplyServerEntity groupApplyEntity) {
        friendApply = null;
        bochat_topbar.setTitleText("群聊请求");
        groupApply = groupApplyEntity;
        userName.setText(groupApply.getNickname());
        String content = "申请加入"+ groupApplyEntity.getGroupName()+"\n" + groupApplyEntity.getApplyText();
        info_tx.setText(content);
        head_img.setType(TYPE_ROUND);
        head_img.setRoundRadius(getResources().getDimensionPixelSize(R.dimen.dp_10));
        Glide.with(this).load(groupApply.getHeadImg()).error(R.mipmap.ic_default_head).into(head_img);
    }
}
