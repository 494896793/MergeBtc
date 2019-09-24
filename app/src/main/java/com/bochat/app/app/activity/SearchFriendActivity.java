package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.SearchEditText;
import com.bochat.app.common.bean.SearchedEntity;
import com.bochat.app.common.contract.conversation.SearchFriendContract;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/18 11:00
 * Description :
 */

@Route(path = RouterSearchFriend.PATH)
public class SearchFriendActivity extends BaseActivity<SearchFriendContract.Presenter> implements SearchFriendContract.View{

    @Inject
    SearchFriendContract.Presenter presenter;
    
    @BindView(R.id.search_friend_input)
    SearchEditText searchEditText;
    
    @BindView(R.id.search_friend_list)
    ListView searchListView;
    
    private ArrayList<SearchedEntity> friendList;
    
    private MultiItemTypeAdapter<SearchedEntity> adapter;
    
    private String notFoundTips = "结果不存在";
    
    @Override
    public void updateSearchList(List<SearchedEntity> list, String notFoundTips) {
        this.notFoundTips = notFoundTips;
        friendList.clear();
        friendList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateSearchHint(String text) {
        searchEditText.setHint(text);
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SearchFriendContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_friend);
    }

    @Override
    protected void initWidget() {
        searchEditText.setTextInputEnterListener(new SearchEditText.TextInputEnterListener() {
            @Override
            public void onTextChange(String text) {
                presenter.onSearchTextUpdate(text);
            }

            @Override
            public void onEnter(String text) {
                presenter.onSearchTextEnter(text);
            }

            @Override
            public void onCancel() {
                presenter.onSearchCancel();
            }
        });
        friendList = new ArrayList<>();
        
        adapter = new MultiItemTypeAdapter<>(this, friendList);
        adapter.addItemViewDelegate(new ItemViewDelegate<SearchedEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.fragment_address_user_item;
            }

            @Override
            public boolean isForViewType(SearchedEntity item, int position) {
                return item.getType() == SearchedEntity.TYPE_FRIEND || item.getType() == SearchedEntity.TYPE_GROUP
                        || item.getType() == SearchedEntity.TYPE_GROUP_MEMBER;
            }

            @Override
            public void convert(ViewHolder viewHolder, SearchedEntity item, int position) {
                viewHolder.setText(R.id.userName, item.getName());
                viewHolder.setText(R.id.userSign, "ID: " + item.getId());
                ImageView view = viewHolder.getView(R.id.head_img);
                Glide.with(getViewContext()).load(item.getIcon())
                        .error(R.mipmap.ic_default_head)
                        .transform(new CenterCrop(),new GlideRoundTransform(getViewContext(),getResources().getDimensionPixelOffset(R.dimen.dp_10)))
                        .into(view);
            }
        });
        
        adapter.addItemViewDelegate(new ItemViewDelegate<SearchedEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_search_none;
            }

            @Override
            public boolean isForViewType(SearchedEntity item, int position) {
                return item.getType() == SearchedEntity.TYPE_NONE;
            }

            @Override
            public void convert(ViewHolder holder, SearchedEntity object, int position) {
                holder.setText(R.id.item_search_none_text, notFoundTips);
            }
        });
        
        adapter.addItemViewDelegate(new ItemViewDelegate<SearchedEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.pinyin_item;
            }

            @Override
            public boolean isForViewType(SearchedEntity item, int position) {
                return item.getType() == SearchedEntity.TYPE_PINYIN;
            }

            @Override
            public void convert(ViewHolder holder, SearchedEntity object, int position) {
                holder.setText(R.id.pinyin_tx, object.getFirstPinyin());
            }
        });
        
        searchListView.setAdapter(adapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSearchItemClick(friendList.get(position));
            }
        });
    }
}
