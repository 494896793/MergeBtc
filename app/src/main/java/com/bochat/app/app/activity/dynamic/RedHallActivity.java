package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.RedHallAdapter;
import com.bochat.app.app.fragment.RedPacketDialogFragment;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.VipRedDialog;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.RedHallContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterPrivilege;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.common.router.RouterRedHall;
import com.bochat.app.common.router.RouterRedHallDetail;
import com.bochat.app.model.bean.RedHallListEntity;
import com.bochat.app.model.bean.RedHallListItemEntity;
import com.bochat.app.model.bean.RedPacketDialogDataEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.model.event.RedHallEvent;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/7/16
 * Author LDL
 **/
@Route(path = RouterRedHall.PATH)
public class RedHallActivity extends BaseActivity<RedHallContract.Presenter> implements RedHallContract.View, SpringView.OnFreshListener, RedHallAdapter.OnRedItemClickListener
        , RedPacketDialogFragment.OnGetRedPacket {

    @Inject
    RedHallContract.Presenter presenter;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;

    private VipRedDialog vipRedDialog;
    private RedHallAdapter adapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private RedHallListEntity entity;
    private VipStatuEntity vipStatuEntity;
    private RedPacketDialogFragment dialogFragment;
    private String dialogTag;
    private UserEntity userEntity;
    private RedPacketDialogDataEntity redPacketDialogDataEntity;


    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RedHallContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_redhall);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        userEntity = CachePool.getInstance().user().getLatest();
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RedHallAdapter(this, new ArrayList<RedHallListItemEntity>());
        adapter.setOnRedItemClickListener(this);
        recycler.setAdapter(adapter);
        initSpringView();
        bochat_topbar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onRightExtButtonClick() {
                Router.navigation(new RouterPrivilege());
            }
        });
        dialogFragment = new RedPacketDialogFragment();
        dialogFragment.setGetRedPacket(this);
        dialogFragment.show(getSupportFragmentManager(), "");
        dialogFragment.dismiss();
    }

    public void showRedDialog() {
        try {
            dialogTag = "" + System.currentTimeMillis();
            dialogFragment = new RedPacketDialogFragment();
            dialogFragment.setGetRedPacket(this);
            dialogFragment.setRedPacketDialogDataEntity(redPacketDialogDataEntity);
            dialogFragment.setMessage(null);
            if (!dialogFragment.isAdded() && getSupportFragmentManager().findFragmentByTag(dialogTag) == null) {
                dialogFragment.show(getSupportFragmentManager(), dialogTag);
                dialogFragment.dismiss();
            } else {
                getSupportFragmentManager().beginTransaction().remove(dialogFragment).commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        presenter.getRewardHallList(pageIndex + "", pageSize + "", true);
    }

    @Override
    public void onLoadmore() {
        if (entity != null && !entity.getIsNext().equals("0")) {
            pageIndex++;
            presenter.getRewardHallList(pageIndex + "", pageSize + "", false);
        } else {
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    public void setRedDialog(VipStatuEntity entity) {
        vipStatuEntity = entity;
        if (entity != null) {
            if (entity.isShow()) {
                vipRedDialog = new VipRedDialog(this, entity);
                vipRedDialog.setCanceledOnTouchOutside(false);
                vipRedDialog.setCancelable(false);
                vipRedDialog.show();
            }
        }
    }

    @Subscribe
    public void onEventMainThread(RedHallEvent event) {
        finish();
    }

    @Override
    public void refreshRedHall(RedHallListEntity entity, boolean isRefresh) {
        this.entity = entity;
        springView.onFinishFreshAndLoad();
        if (entity != null && entity.getItems() != null) {
            if (isRefresh) {
                adapter.refreshData(entity.getItems());
            } else {
                adapter.loadData(entity.getItems());
            }
        }
    }

    @Override
    public void getWelfareSuccess(RedPacketPeopleEntity entity) {
        Router.navigation(new RouterRedHallDetail(entity.getReward_id() + "", "1"));
        dialogFragment.stopAnimation();
        dialogFragment.dismiss();
    }

    @Override
    public void getWelfareFailed(String msg) {
        dialogFragment.stopAnimation();
        dialogFragment.dismiss();
    }

    @Override
    public void onRedClick(int position, RedHallListItemEntity entity) {
        if(vipStatuEntity!=null&&vipStatuEntity.getIsOpen()!=null&&vipStatuEntity.getIsOpen().equals("1")){
            if (entity.getRewardState().equals("0")) {        //未领完
                if (entity.getIsReceive().equals("0")) {      //未领取
                    redPacketDialogDataEntity = new RedPacketDialogDataEntity();
                    redPacketDialogDataEntity.setImg(entity.getHeadImg());
                    redPacketDialogDataEntity.setName(entity.getNickname());
                    redPacketDialogDataEntity.setText(entity.getSentText());
                    redPacketDialogDataEntity.setRedId(entity.getRewardId());
                    if (dialogFragment.isAdded()) {
                        showRedDialog();
                    } else {
                        dialogFragment.setRedPacketDialogDataEntity(redPacketDialogDataEntity);
                        dialogFragment.setMessage(null);
                        dialogFragment.show(getSupportFragmentManager(), "");
                    }
                } else {
                    Router.navigation(new RouterRedHallDetail(entity.getRewardId(), entity.getIsReceive()));
                }
            } else if (entity.getRewardState().equals("1")) {  //已领完
                Router.navigation(new RouterRedHallDetail(entity.getRewardId(), entity.getIsReceive()));
            } else {
                showTips(new ResultTipsType("该红包已失效",false));
            }
        }
    }

    @Override
    public void onOpenRedPacket(RedPacketMessage message) {
    }

    @Override
    public void onOpenRedPacketOther(RedPacketDialogDataEntity entity) {
        dialogFragment.startAnimation();
        presenter.getWelfare(Integer.valueOf(entity.getRedId()), userEntity.getNickname());
    }
}
