package com.bochat.app.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.search.FuzzySearchBaseAdapter;
import com.bochat.app.app.view.search.IFuzzySearchItem;
import com.bochat.app.app.view.search.PinyinUtil;
import com.bochat.app.model.bean.FriendEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/23 11:54
 * Description :
 */

public class SearchConversationAdapter extends FuzzySearchBaseAdapter<SearchConversationAdapter.LocalFriendEntity> {

    private Context context;

    public SearchConversationAdapter(Context context, List<LocalFriendEntity> dataList) {
        super(null, dataList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_friend, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.friend_name);
            viewHolder.icon = convertView.findViewById(R.id.friend_icon);
            viewHolder.content = convertView.findViewById(R.id.friend_signature);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FriendEntity friendEntity = mDataList.get(position).getFriendEntity();
        viewHolder.name.setText(friendEntity.getNickname());
        Glide.with(context).load(friendEntity.getHead_img()).into(viewHolder.icon);
        viewHolder.content.setText(friendEntity.getSignature());
        return convertView;
    }

    public static class LocalFriendEntity implements IFuzzySearchItem {

        private FriendEntity friendEntity;
        private String sourceKey;
        private List<String> fuzzyKey;

        public LocalFriendEntity(FriendEntity entity){
            friendEntity = entity;
            sourceKey = friendEntity.getNickname();
            fuzzyKey = PinyinUtil.getPinYinList(sourceKey);
            if(fuzzyKey == null){
                fuzzyKey = new ArrayList<>();
            }
        }

        public FriendEntity getFriendEntity() {
            return friendEntity;
        }

        public void setFriendEntity(FriendEntity friendEntity) {
            this.friendEntity = friendEntity;
        }

        @Override
        public String getSourceKey() {
            return sourceKey;
        }

        @Override
        public List<String> getFuzzyKey() {
            return fuzzyKey;
        }
    }

    static class ViewHolder {
        TextView name;
        ImageView icon;
        TextView content;
    }
}
