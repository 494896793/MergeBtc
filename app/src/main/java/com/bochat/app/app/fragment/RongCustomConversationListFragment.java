package com.bochat.app.app.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;

public class RongCustomConversationListFragment extends ConversationListFragment {//Contract

    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {
        return new CustomConversationListAdapter(context);
    }

    class CustomConversationListAdapter extends ConversationListAdapter {
        CustomConversationListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void bindView(View v, int position, UIConversation data) {
            ConversationListAdapter.ViewHolder holder = (ConversationListAdapter.ViewHolder) v.getTag();
            data.setUnreadType(UIConversation.UnreadRemindType.NO_REMIND);
            holder.unReadMsgCountIcon.setImageResource(R.drawable.rc_unread_bg_shape);
            super.bindView(v, position, data);
        }

        @Override
        protected void setUnReadViewLayoutParams(View view, UIConversation.UnreadRemindType type) {
            super.setUnReadViewLayoutParams(view, type);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            Context context = view.getContext();
            if(type == UIConversation.UnreadRemindType.NO_REMIND) {
                params.leftMargin = (int) context.getResources().getDimension(R.dimen.dp_45);
                params.topMargin = (int) context.getResources().getDimension(R.dimen.dp_14);
            }
            view.setLayoutParams(params);
        }
    }
}
