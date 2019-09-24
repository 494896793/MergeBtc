package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.GroupMemberContract;
import com.bochat.app.common.router.RouterGroupMember;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bochat.app.app.view.SpImageView.TYPE_CIRCLE;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupMember.PATH)
public class GroupMemberActivity extends BaseActivity<GroupMemberContract.Presenter> implements GroupMemberContract.View, SpringView.OnFreshListener {

    @Inject
    GroupMemberContract.Presenter presenter;

    @BindView(R.id.springView)
    SpringView springView;
    
    @BindView(R.id.cv_group_member_list)
    ListView listView;

    List<GroupMemberEntity> members;

    MultiItemTypeAdapter<GroupMemberEntity> adapter;
    
    int page = 1;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupMemberContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_member);
    }

    @Override
    protected void initWidget() {
        members = new ArrayList<>();
     
        adapter = new MultiItemTypeAdapter<>(this, members);

        adapter.addItemViewDelegate(new ItemViewDelegate<GroupMemberEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_cv_group_member;
            }

            @Override
            public boolean isForViewType(GroupMemberEntity item, int position) {
                return item.getRole() == 1;
            }

            @Override
            public void convert(ViewHolder holder, GroupMemberEntity groupMemberEntity, int position) {
                SpImageView icon = holder.getView(R.id.cv_group_member_icon);
                icon.setType(TYPE_CIRCLE);
                Glide.with(getViewContext()).load(groupMemberEntity.getHead_img()).into(icon);
                holder.setText(R.id.cv_group_member_name, groupMemberEntity.getNickname());
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<GroupMemberEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_cv_group_manager;
            }

            @Override
            public boolean isForViewType(GroupMemberEntity item, int position) {
                return item.getRole() == 2;
            }

            @Override
            public void convert(ViewHolder holder, GroupMemberEntity groupMemberEntity, int position) {
                SpImageView icon = holder.getView(R.id.cv_group_member_icon);
                icon.setType(TYPE_CIRCLE);
                Glide.with(getViewContext()).load(groupMemberEntity.getHead_img()).into(icon);
                holder.setText(R.id.cv_group_member_name, groupMemberEntity.getNickname());
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<GroupMemberEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_cv_group_owner;
            }

            @Override
            public boolean isForViewType(GroupMemberEntity item, int position) {
                return item.getRole() == 3;
            }

            @Override
            public void convert(ViewHolder holder, GroupMemberEntity groupMemberEntity, int position) {
                SpImageView icon = holder.getView(R.id.cv_group_member_icon);
                icon.setType(TYPE_CIRCLE);
                Glide.with(getViewContext()).load(groupMemberEntity.getHead_img()).into(icon);
                holder.setText(R.id.cv_group_member_name, groupMemberEntity.getNickname());
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<GroupMemberEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_cv_group_div;
            }

            @Override
            public boolean isForViewType(GroupMemberEntity item, int position) {
                return item.getRole() == 4;
            }

            @Override
            public void convert(ViewHolder holder, GroupMemberEntity groupMemberEntity, int position) {
                holder.setText(R.id.cv_group_member_div_text, groupMemberEntity.getNickname());
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (members.get(position).getRole() != 4) {
                    presenter.onItemClick(members.get(position));
                }
            }
        });
        initSpringView();
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }
    
    @OnClick({R.id.cv_group_member_search_layout})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onSearchClick();
    }

    @Override
    public void updateMemberList(int page, List<GroupMemberEntity> memberList) {
        this.page = page;
        springView.onFinishFreshAndLoad();
        members.clear();
        members.addAll(memberList);
        adapter.notifyDataSetChanged();
    }
    
    private List<GroupMemberEntity> createTestList(){
        List<GroupMemberEntity> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            GroupMemberEntity friendEntity = new GroupMemberEntity();
            friendEntity.setRole(1);
            friendEntity.setNickname("好友" + i);
            friendEntity.setHead_img("http://bochatoss.oss-cn-beijing.aliyuncs.com/headImg/IMG_20190512_214544.jpg/45515e50-41c0-4042-9375-dc5fea431abe.jpg");
            friendEntity.setSignature("哈哈哈哈哈哈哈哈哈哈哈");
            list.add(friendEntity);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        presenter.reload();
    }

    @Override
    public void onLoadmore() {
        presenter.loadMore(page+1);
    }
}
