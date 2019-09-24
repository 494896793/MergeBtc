package com.bochat.app.model.util;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.bochat.app.common.util.ULog;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.SwipeLayout;
import io.rong.imkit.widget.SwipeLayoutManager;


public class IMAppCompat {

    public static void bindLongClick(Context context, View view, final UIConversation uiConversation) {
        if (view instanceof SwipeLayout) {
            ((SwipeLayout) view).open();
            view.findViewById(io.rong.imkit.R.id.top_conversation).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().setConversationToTop(uiConversation.getConversationType(), uiConversation.getConversationTargetId(), !uiConversation.isTop(), null);
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                }
            });
            view.findViewById(io.rong.imkit.R.id.delete_conversation).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RongIM.getInstance().removeConversation(uiConversation.getConversationType(), uiConversation.getConversationTargetId(), null);
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                }
            });
        }
    }

    public static boolean clearSwipe(View v) {
        if (v instanceof SwipeLayout) {
            SwipeLayout swipeLayout = (SwipeLayout) v;
            if (swipeLayout.isOpen()) {
                swipeLayout.close();
                return true;
            }
        }
        return false;
    }
}
