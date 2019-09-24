package com.bochat.app.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.SelectFriendContract;
import com.bochat.app.common.router.RouterSelectFriend;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/22 16:25
 * Description :
 */
@Route(path = RouterSelectFriend.PATH)
public class SelectFriendActivity extends BaseActivity<SelectFriendContract.Presenter> implements SelectFriendContract.View{

    @Inject
    SelectFriendContract.Presenter presenter;
    
    @BindView(R.id.select_friend_top_bar)
    BoChatTopBar topBar;
    
    @BindView(R.id.select_friend_list)
    ListView selectListView;
    
    private ArrayList<SelectFriendEntity> friendList;

    private CommonAdapter adapter;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SelectFriendContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_friend);
    }

    @OnClick({R.id.select_friend_search_layout})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onSearchBtnClick();
    }
    
    @Override
    protected void initWidget() {
        friendList = new ArrayList<>();
        
        adapter = new CommonAdapter<SelectFriendEntity>(this, R.layout.item_select_friend, friendList) {

            @Override
            protected void convert(final ViewHolder viewHolder, final SelectFriendEntity item, int position) {
                viewHolder.setText(R.id.select_friend_name, item.getNickname());

                SpImageView icon = viewHolder.getView(R.id.select_friend_icon);
                Glide.with(getViewContext()).load(item.getHead_img()).into(icon);
                final ImageView view = viewHolder.getView(R.id.select_friend_btn);
                if (item.isSelected()) {
                    Glide.with(getViewContext()).load(R.mipmap.ic_select_friend_btn_ok).into(view);
                } else {
                    Glide.with(getViewContext()).load(R.mipmap.ic_select_friend_btn).into(view);
                }
                viewHolder.setOnClickListener(R.id.select_friend_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.isSelected()) {
                            item.setSelected(false);
                            Glide.with(getViewContext()).load(R.mipmap.ic_select_friend_btn).into(view);
                        } else {
                            item.setSelected(true);
                            Glide.with(getViewContext()).load(R.mipmap.ic_select_friend_btn_ok).into(view);
                        }
                        changeButtonStatus();
                    }
                });
            }
        };
        selectListView.setAdapter(adapter);
        
        topBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onLeftExtButtonClick() {

            }

            @Override
            public void onRightExtButtonClick() {
                
            }

            @Override
            public void onTextExtButtonClick() {
                ArrayList<FriendEntity> selectFriends = new ArrayList<>();
                for(int i = 0; i < friendList.size(); i++){
                    if(friendList.get(i).isSelected){
                        selectFriends.add(friendList.get(i));
                    } 
                }
                presenter.onEnterBtnClick(selectFriends);
            }
        });

        topBar.getTextButton().setBackground(getResources().getDrawable(R.drawable.shape_button_5dp));
        changeButtonStatus();
    }
    
    private void changeButtonStatus(){
        if(friendList != null && !friendList.isEmpty()){
            for(SelectFriendEntity item : friendList){
                if(item.isSelected()){
                    topBar.getTextButton().setClickable(true);
                    topBar.getTextButton().setTextColor(Color.parseColor("#FFFFFF"));
                    return;
                }
            }
        }
        topBar.getTextButton().setClickable(false);
        topBar.getTextButton().setTextColor(Color.parseColor("#AAFFFFFF"));
    }
    
    @Override
    public void onRefresh(List<FriendEntity> list) {
        friendList.clear();
        for(FriendEntity friendEntity : list){
            friendList.add(new SelectFriendEntity(friendEntity));
        }
        adapter.notifyDataSetChanged();
        changeButtonStatus();
    }
    
    @Override
    public void select(final FriendEntity selectBySearch) {
        for(SelectFriendEntity friendEntity : friendList){
            if(selectBySearch.getId() == friendEntity.getId()){
                friendEntity.setSelected(true);
            }
        }
        adapter.notifyDataSetChanged();
        changeButtonStatus();
    }

    private class SelectFriendEntity extends FriendEntity{
        
        private boolean isSelected;

        public SelectFriendEntity(FriendEntity entity){
            setId(entity.getId());
            setAccount(entity.getAccount());
            setNickname(entity.getNickname());
            setIsInit(entity.getIsInit());
            setHead_img(entity.getHead_img());
            setSignature(entity.getSignature());
            setSex(entity.getSex());
            setAge(entity.getAge());
            setProvince(entity.getProvince());
            setCity(entity.getCity());
            setToken(entity.getToken());
            setOther_id(entity.getOther_id());
            setInviteCode(entity.getInviteCode());
            setCountries(entity.getCountries());
            setBirthday(entity.getBirthday());
            setArea(entity.getArea());
            setRelation_state(entity.getRelation_state());
            setBlack_state(entity.getBlack_state());
        }
        
        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
