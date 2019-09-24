package com.bochat.app.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.SoftKeyboardUtil;
import com.bochat.app.app.view.MessageDialog;
import com.bochat.app.app.view.PayPasswordDialog;
import com.bochat.app.common.contract.conversation.GroupManageUpgradeContract;
import com.bochat.app.common.router.RouterGroupManageUpgrade;
import com.bochat.app.model.bean.GroupLevelEntity;
import com.bochat.app.model.bean.GroupLevelListEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupManageUpgrade.PATH)
public class GroupManageUpgradeActivity extends BaseActivity<GroupManageUpgradeContract.Presenter> implements GroupManageUpgradeContract.View{

    @Inject
    GroupManageUpgradeContract.Presenter presenter;
    
    @BindView(R.id.cv_group_manage_upgrade_list)
    ListView listView;
    
    private ArrayList<GroupLevelEntity> list;
    
    private CommonAdapter<GroupLevelEntity> adapter;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageUpgradeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage_upgrade);
    }

    @Override
    protected void initWidget() {
        list = new ArrayList<>();
        adapter = new CommonAdapter<GroupLevelEntity>(this, R.layout.item_cv_group_manage_upgrade, list) {
            @Override
            protected void convert(ViewHolder viewHolder, final GroupLevelEntity item, int position) {
                viewHolder.setText(R.id.cv_group_upgrade_name, item.getLevel_num()+"人群");
                viewHolder.setText(R.id.cv_group_upgrade_charge, "价格："+item.getPrice()+"金币");
                Button button = viewHolder.getView(R.id.cv_group_upgrade_btn);
                if(item.getRank() == 1){
                    button.setText("当前");
                    button.setEnabled(false);
                } else {
                    button.setText("升级");
                    button.setEnabled(true);
                }
                ImageView view = viewHolder.getView(R.id.cv_group_upgrade_layout);
                Glide.with(getViewContext()).load(item.getLevel_image()).into(view);
                viewHolder.setOnClickListener(R.id.cv_group_upgrade_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageDialog messageDialog = new MessageDialog(getViewContext(),
                                "升级至"+item.getLevel_num()+"人群",
                                "支付"+item.getPrice()+"金币");
                        messageDialog.setOnChooseListener(new MessageDialog.OnChooseListener() {
                            @Override
                            public void onEnter() {
                                PayPasswordDialog payPasswordDialog = new PayPasswordDialog(getViewContext(), item.getPrice()+"金币");
                                payPasswordDialog.setOnEnterListener(new PayPasswordDialog.OnEnterListener() {
                                    @Override
                                    public void onEnter(String password) {
                                        presenter.onEnterPay(item, password);
                                    }
                                });
                                payPasswordDialog.showPopupWindow();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                        SoftKeyboardUtil.hideSoftKeyboard((Activity) getViewContext());
                        messageDialog.showPopupWindow();
                    }
                });
            }
        };
        listView.setAdapter(adapter);
    }
    
    @Override
    public void updateList(GroupLevelListEntity data) {
        list.clear();
        list.addAll(data.getData());
        adapter.notifyDataSetChanged();
    }
}
