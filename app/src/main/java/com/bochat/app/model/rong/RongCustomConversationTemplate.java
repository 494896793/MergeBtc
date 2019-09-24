package com.bochat.app.model.rong;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.util.ResourceUtils;

import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.GroupConversationProvider;
import io.rong.imlib.model.Conversation;

@ConversationProviderTag(conversationType = "group", portraitPosition = 1)
public class RongCustomConversationTemplate extends GroupConversationProvider {

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        View result = LayoutInflater.from(context).inflate(io.rong.imkit.R.layout.item_private_conversation_provider_custom, (ViewGroup) null);
        ViewHolder holder = new ViewHolder();
        holder.title = result.findViewById(io.rong.imkit.R.id.rc_conversation_title);
        holder.time = result.findViewById(io.rong.imkit.R.id.rc_conversation_time);
        holder.content = result.findViewById(io.rong.imkit.R.id.rc_conversation_content);
        holder.notificationBlockImage = result.findViewById(io.rong.imkit.R.id.rc_conversation_msg_block);
        holder.readStatus = result.findViewById(io.rong.imkit.R.id.rc_conversation_status);
        result.setTag(holder);
        return result;
    }

    @Override
    public void bindView(View view, int position, UIConversation data) {
        super.bindView(view, position, data);
        ViewHolder holder = (ViewHolder) view.getTag();
        Drawable drawable = null;
        if(data != null) {
            if(data.getConversationType() == Conversation.ConversationType.GROUP) {
                holder.title.setCompoundDrawablePadding(ResourceUtils.dip2px(view.getContext(), R.dimen.dp_4));
                drawable = view.getResources().getDrawable(R.mipmap.home_icon_group);
                holder.title.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,  drawable, null);
            }
        }
    }

    @Override
    public Spannable getSummary(UIConversation data) {
        return super.getSummary(data);
    }

    @Override
    public String getTitle(String userId) {
        return super.getTitle(userId);
    }

    @Override
    public Uri getPortraitUri(String userId) {
        return super.getPortraitUri(userId);
    }

    // 自定义 ViewHolder，可添加自定义布局控件
    class ViewHolder extends GroupConversationProvider.ViewHolder {
    }
}