package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.SearchEditText;
import com.bochat.app.common.bean.ConversationCopy;
import com.bochat.app.common.bean.MessageCopy;
import com.bochat.app.common.bean.SearchedConversation;
import com.bochat.app.common.contract.conversation.SearchConversationContract;
import com.bochat.app.common.router.RouterSearchConversation;
import com.bochat.app.common.util.DateUtil;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.rong.imlib.model.Conversation;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/23 18:04
 * Description :
 */

@Route(path = RouterSearchConversation.PATH)
public class SearchConversationActivity extends BaseActivity<SearchConversationContract.Presenter> implements SearchConversationContract.View{
    
    @Inject
    SearchConversationContract.Presenter presenter;
    
    @BindView(R.id.search_message_input)
    SearchEditText searchEditText;
    
    @BindView(R.id.search_message_list)
    ListView listView;

    private ArrayList<ExpandableSearchedConversation> conversationList = new ArrayList<>();
    private MultiItemTypeAdapter<ExpandableSearchedConversation> adapter;
    
    @Override
    public void updateConversationList(List<SearchedConversation> list) {
        conversationList.clear();
        for(SearchedConversation item : list){
            conversationList.add(new ExpandableSearchedConversation(item));
        }
        if(conversationList.isEmpty()){
            conversationList.add(new ExpandableSearchedConversation(null));
        }
        adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SearchConversationContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_search_local_message);
    }
    
    @Override
    protected void initWidget() {
        adapter = new MultiItemTypeAdapter<>(this, conversationList);

        adapter.addItemViewDelegate(new ItemViewDelegate<ExpandableSearchedConversation>() {
                @Override
                public int getItemViewLayoutId() {
                    return R.layout.item_search_history_message;
                }
    
                @Override
                public boolean isForViewType(ExpandableSearchedConversation item, int position) {
                    return item.getSearchedMessage() != null;
                }
    
                @Override
                public void convert(final ViewHolder viewHolder, final ExpandableSearchedConversation item, int position) {
                    viewHolder.setText(R.id.search_history_message_name, item.getTitle());
                    viewHolder.setText(R.id.search_history_message_content, item.getCloseContent());
                    viewHolder.setVisible(R.id.search_history_message_arrow, item.isExpandable());
                    viewHolder.setImageResource(R.id.search_history_message_arrow, R.mipmap.ic_arrow_right_gray24);
                    viewHolder.setText(R.id.search_history_message_date, item.getTime());

                    ImageView icon = viewHolder.getView(R.id.search_history_message_icon);
                    Glide.with(getViewContext()).load(item.getIcon()).into(icon);

                    viewHolder.setOnClickListener(R.id.search_history_message_arrow, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.setExpand(!item.isExpand());
                            if(item.isExpand()){
                                viewHolder.setText(R.id.search_history_message_content, item.getContent());
                                viewHolder.setImageResource(R.id.search_history_message_arrow, R.mipmap.ic_arrow_down_gray24);
                            } else {
                                viewHolder.setText(R.id.search_history_message_content, item.getCloseContent());
                                viewHolder.setImageResource(R.id.search_history_message_arrow, R.mipmap.ic_arrow_right_gray24);
                            }
                        }
                    });
                    viewHolder.setOnClickListener(R.id.search_history_message_layout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            presenter.onSearchItemClick(item.getSearchedMessage());
                        }
                    });
                }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<ExpandableSearchedConversation>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_search_none;
            }

            @Override
            public boolean isForViewType(ExpandableSearchedConversation item, int position) {
                return item.getSearchedMessage() == null;
            }

            @Override
            public void convert(ViewHolder holder, ExpandableSearchedConversation object, int position) {
                holder.setText(R.id.item_search_none_text, "未找到相关信息");
            }
        });
        listView.setAdapter(adapter);
        searchEditText.setTextInputEnterListener(new SearchEditText.TextInputEnterListener() {
            @Override
            public void onTextChange(String text) {
                presenter.onSearchTextChange(text);
            }

            @Override
            public void onEnter(String text) {
            }

            @Override
            public void onCancel() {
                presenter.onSearchCancel();
            }
        });
    }


    private class ExpandableSearchedConversation {
        
        private static final int EXPAND_LIMIT_LENGTH = 10;
        private SearchedConversation searchedMessage;
        private boolean isExpandable;
        private String title;
        private String content;
        private String closeContent;
        private boolean isExpand;
        private String time;
        private String icon;
        
        public ExpandableSearchedConversation(SearchedConversation searchedMessage) {
            this.searchedMessage = searchedMessage;
            if(searchedMessage == null){
                return;
            }
            if(searchedMessage.getMessages().size() >= 1){
                ConversationCopy conversation = searchedMessage.getConversation();
                MessageCopy message = searchedMessage.getMessages().get(0);
                
                
                    
                    setTime(DateUtil.formatChatTime(message.getReceivedTime()));
                    
                    if(conversation.getConversationType() == Conversation.ConversationType.GROUP){
                        setTitle(conversation.getConversationTitle());
                        setIcon(conversation.getPortraitUrl());
                    } else {
                        setTitle(conversation.getConversationTitle());
                        setIcon(conversation.getPortraitUrl());
                    }
                    if(searchedMessage.getMessages().size() == 1){
                        String content = message.getContent().getContent();
                        setContent(content);
                        if(content != null && content.length() >= EXPAND_LIMIT_LENGTH){
                            setExpandable(true);
                            setCloseContent(content.substring(0, EXPAND_LIMIT_LENGTH - 1) + "...");
                        } else {
                            setExpandable(false);
                            setCloseContent(content);
                        }
                    } else if (searchedMessage.getMessages().size() > 1){
                        
                        String content = searchedMessage.getMessages().size() + "条相关记录";
                        setContent(content);
                        setExpandable(false);
                        setCloseContent(content);
                    }
                
            } else {
                setExpandable(false);
                setContent("");
                setCloseContent("");
            }
        }

        public SearchedConversation getSearchedMessage() {
            return searchedMessage;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSearchedMessage(SearchedConversation searchedMessage) {
            this.searchedMessage = searchedMessage;
        }

        public boolean isExpandable() {
            return isExpandable;
        }

        public void setExpandable(boolean expandable) {
            isExpandable = expandable;
        }

        public String getCloseContent() {
            return closeContent == null ? "" : closeContent;
        }

        public void setCloseContent(String closeContent) {
            this.closeContent = closeContent;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "ExpandableSearchedConversation{" +
                    "searchedMessage=" + searchedMessage +
                    ", isExpandable=" + isExpandable +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", closeContent='" + closeContent + '\'' +
                    ", isExpand=" + isExpand +
                    ", time='" + time + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }
}