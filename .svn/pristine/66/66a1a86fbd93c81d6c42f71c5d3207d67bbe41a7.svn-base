package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.GroupAddMangerAdapter;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.conversation.GroupAddManagerContract;
import com.bochat.app.common.router.RouterGroupAddManager;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterGroupAddManager.PATH)
public class GroupAddManagerActivity extends BaseActivity<GroupAddManagerContract.Presenter>  implements GroupAddManagerContract.View{
    @Inject
    GroupAddManagerContract.Presenter presenter;
    @BindView(R.id.group_add_manager_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.group_add_manager_topbar)
    BoChatTopBar boChatTopBar;
    Map<Integer,String> managerMap;

    private GroupAddMangerAdapter addMangerAdapter;

    private List<GroupMemberEntity> memberEntityList;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupAddManagerContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_add_manager);

    }

    @Override
    protected void initWidget() {
        super.initWidget();
        memberEntityList = new ArrayList<>();
        managerMap = new HashMap<>();
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                super.onTextExtButtonClick();
                String member ="";
//                Toast.makeText(GroupAddManagerActivity.this,"wancgc",Toast.LENGTH_SHORT).show();
                for (int i = 0;i<managerMap.size();i++){
                    if (!managerMap.get(i).equals("")){
                        member = member+managerMap.get(i)+",";
                    }
                }
                if (!member.equals("")){
                    member = member.substring(0,member.length()-1);
                }
//                LogUtil.LogDebug("ggyy","str ="+member);
                presenter.addManagerCommit(member);
            }
        });


         //测试数据
        /*for (int i = 0;i<30;i++){
            GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
            groupMemberEntity.setHead_img("http://bochatoss.oss-cn-beijing.aliyuncs.com/headImg/1561102370493229.jpeg/08e97cf9-0274-4aa0-8cf8-b7435a009316.jpg");
            groupMemberEntity.setMember_id(0000);
            groupMemberEntity.setNickname("gggggggg");
            memberEntityList.add(groupMemberEntity);
        }*/


        addMangerAdapter = new GroupAddMangerAdapter(this,memberEntityList);
        addMangerAdapter.setOnAddManagerItemListener(new GroupAddMangerAdapter.onAddManagerItemListener() {
            @Override
            public void onAddManagerItemOnclik(int positon, String memberId) {
                managerMap.put(positon,memberId);
            }

            @Override
            public void onReMoveManageItemOnclik(int position) {
                managerMap.put(position,"");
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(addMangerAdapter);
    }

    @OnClick({R.id.cv_group_member_search_layout})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onSearchClick();
    }

    @Override
    public void onUpdateMemberList(List<GroupMemberEntity> list) {

       for (int i= 0;i<list.size();i++){
           managerMap.put(i,"");
       }

        addMangerAdapter.add(list);

    }

    @Override
    public void selectMember(String memberId) {
        int itemPosition = addMangerAdapter.getItemPosition(memberId);
        if(itemPosition != -1){
            managerMap.put(itemPosition, memberId);
            addMangerAdapter.setItemClick(itemPosition, true);
            addMangerAdapter.notifyDataSetChanged();
        }
    }

}
