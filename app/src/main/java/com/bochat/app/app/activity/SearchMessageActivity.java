package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SearchEditText;
import com.bochat.app.common.bean.MessageContentCopy;
import com.bochat.app.common.bean.MessageCopy;
import com.bochat.app.common.bean.SearchedMessage;
import com.bochat.app.common.contract.conversation.SearchMessageContract;
import com.bochat.app.common.router.RouterSearchMessage;
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

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 16:06
 * Description :
 */

@Route(path = RouterSearchMessage.PATH)
public class SearchMessageActivity extends BaseActivity<SearchMessageContract.Presenter> implements SearchMessageContract.View{
    
    @Inject
    SearchMessageContract.Presenter presenter;
    
    @BindView(R.id.search_message_input)
    SearchEditText searchEditText;
    
    @BindView(R.id.search_message_top_bar)
    BoChatTopBar searchTopBar;

    @BindView(R.id.search_message_list)
    ListView listView;

    private ArrayList<ExpandableSearchedMessage> messageList = new ArrayList<>();
    private MultiItemTypeAdapter<ExpandableSearchedMessage> adapter;
    
    @Override
    public void showSearchBar(boolean isShow) {
        searchEditText.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTopBar(boolean isShow) {
        searchTopBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void updateMessageList(List<SearchedMessage> list) {
        messageList.clear();
        for(SearchedMessage item : list){
            messageList.add(new ExpandableSearchedMessage(item));
        }
        if(messageList.isEmpty()){
            messageList.add(new ExpandableSearchedMessage(null));
        }
        adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        adapter = new MultiItemTypeAdapter<>(this, messageList);
        adapter.addItemViewDelegate(new ItemViewDelegate<ExpandableSearchedMessage>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_search_history_message;
            }

            @Override
            public boolean isForViewType(ExpandableSearchedMessage item, int position) {
                return item.getSearchedMessage() != null;
            }

            @Override
            public void convert(final ViewHolder viewHolder, final ExpandableSearchedMessage item, int position) {
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
        adapter.addItemViewDelegate(new ItemViewDelegate<ExpandableSearchedMessage>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_search_none;
            }

            @Override
            public boolean isForViewType(ExpandableSearchedMessage item, int position) {
                return item.getSearchedMessage() == null;
            }

            @Override
            public void convert(ViewHolder holder, ExpandableSearchedMessage object, int position) {
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
    
    @Override
    protected SearchMessageContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_search_local_message);
    }
    
    private class ExpandableSearchedMessage {

        private static final int EXPAND_LIMIT_LENGTH = 10;
        private SearchedMessage searchedMessage;
        private boolean isExpandable;
        private String title;
        private String content;
        private String closeContent;
        private boolean isExpand;
        private String time;
        private String icon;

        public ExpandableSearchedMessage(SearchedMessage searchedMessage) {
            this.searchedMessage = searchedMessage;
            if(searchedMessage == null){
                return;
            }
            MessageCopy message = searchedMessage.getMessage();
            MessageContentCopy messageContent = message.getContent();
            setTime(DateUtil.formatChatTime(message.getReceivedTime()));
            setIcon(messageContent.getUserInfo().getPortraitUri());
            setTitle(messageContent.getUserInfo().getName());
            String content = messageContent.getContent();
            setContent(content);
            if (content != null && content.length() >= EXPAND_LIMIT_LENGTH) {
                setExpandable(true);
                setCloseContent(content.substring(0, EXPAND_LIMIT_LENGTH - 1) + "...");
            } else {
                setExpandable(false);
                setCloseContent(content);
            }
        }

        public SearchedMessage getSearchedMessage() {
            return searchedMessage;
        }

        public void setSearchedMessage(SearchedMessage searchedMessage) {
            this.searchedMessage = searchedMessage;
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
            return "ExpandableSearchedMessage{" +
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