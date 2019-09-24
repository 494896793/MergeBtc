package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.RedPacketDetailAdapter;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.bean.RedPacketMessageCopy;
import com.bochat.app.common.contract.readpacket.RedPacketDetailContract;
import com.bochat.app.common.router.RouterRedPacketDetail;
import com.bochat.app.model.bean.RedPacketRecordListEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/5/13
 * Author LDL
 **/
@Route(path = RouterRedPacketDetail.PATH)
public class RedPacketDetailActivity extends BaseActivity<RedPacketDetailContract.Presenter> implements RedPacketDetailContract.View {

    @BindView(R.id.head_img)
    ImageView head_img;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.notice_text)
    TextView notice_text;

    @BindView(R.id.value_person)
    TextView value_person;

    @BindView(R.id.receive_notice_text)
    TextView receive_notice_text;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.back_relative)
    RelativeLayout back_relative;

    @BindView(R.id.redpacket_history)
    TextView redpacket_history;

    @Inject
    RedPacketDetailContract.Presenter presenter;

    private RedPacketMessageCopy message;
    private String redPacketId;
    private RedPacketDetailAdapter adapter;
    private UserEntity userEntity;
    private String text;
    private int type;
    private String money;
    private String bitName;
    private RedPacketStatuEntity redPacketStatuEntity;
    private RedPacketRecordListEntity redPacketRecordListEntity;
    private UserEntity myUserInfo;
    private boolean isReceived=false;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RedPacketDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_redpacketdetail);
    }

    @OnClick({R.id.redpacket_history,R.id.back_relative})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_relative:
                finish();
                break;
            case R.id.redpacket_history:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        money = null;
        myUserInfo = CachePool.getInstance().user().getLatest();

        RouterRedPacketDetail extra = getExtra(RouterRedPacketDetail.class);
        redPacketRecordListEntity = extra.getRecordListEntity();
        redPacketStatuEntity = extra.getStatuEntity();
        userEntity = extra.getUserEntity();
        message = extra.getRedPacketMessage();
        
        if (message != null && userEntity != null && redPacketStatuEntity != null) {
            if (redPacketRecordListEntity != null && redPacketRecordListEntity.getItems() != null) {
                receive_notice_text.setText("已领取" + redPacketRecordListEntity.getItems().size() + "/" + (int) redPacketStatuEntity.getCount() + "个，剩余" + (int) (redPacketStatuEntity.getCount() - redPacketRecordListEntity.getItems().size()) + "个");
            } else {
                receive_notice_text.setText("已领取" + redPacketStatuEntity.getReadyGet() + "/" + (int) redPacketStatuEntity.getCount() + "个，剩余" + (int) (redPacketStatuEntity.getCount() - redPacketStatuEntity.getReadyGet()) + "个");
            }
            Glide.with(this).load(userEntity.getHeadImg()).transform(new CenterInside(), new GlideRoundTransform(getViewContext(),15)).into(head_img);
            title_text.setText(userEntity.getNickname() + "的红包");

            money=message.getMoney();
//            money="红包已被领完";
            bitName = message.getbName();
            type = message.getType();
            text = message.getText();
            redPacketId = message.getPacketId();
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            notice_text.setText(text);
//            if (type == 1) {        //糖果
//                value_person.setText(money + bitName);
//            } else {
//                value_person.setText("￥" + money);
//            }
            presenter.queryTakeRecord(Integer.valueOf(redPacketId), -1, -1);
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    @Override
    public void queryTakeRecordSuccess(RedPacketRecordListEntity entity) {
        if (entity != null && entity.getItems() != null) {
            adapter = new RedPacketDetailAdapter(entity.getItems(), this);
            recycler.setAdapter(adapter);
            for(int i=0;i<entity.getItems().size();i++){
                if(entity.getItems().get(i).getId()==myUserInfo.getId()){
                    money=entity.getItems().get(i).getReceive_money()+"";
                    isReceived=true;
                    break;
                }
            }
            receive_notice_text.setText("已领取" + entity.getItems().size() + "/" + (int) redPacketStatuEntity.getCount() + "个，剩余" + (int) (redPacketStatuEntity.getCount() - entity.getItems().size()) + "个");
        }
        initMoneyText();
    }

    @Override
    public void queryTakeRecordFailed(String msg) {
        showTips(msg);
        initMoneyText();
    }

    private void initMoneyText(){
        if(isReceived){
            if (type == 1) {        //糖果
                value_person.setText(money + bitName);
            } else {
                value_person.setText("￥" + money);
            }
        }else{
            value_person.setText("红包已被领完");
        }
    }
}