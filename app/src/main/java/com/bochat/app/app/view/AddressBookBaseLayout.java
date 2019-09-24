package com.bochat.app.app.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.fragment.AddressBookFragment;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddressPublicNum;
import com.bochat.app.common.router.RouterApplyList;
import com.bochat.app.common.router.RouterInvitation;
import com.bochat.app.model.event.FriendApplyEvent;
import com.bochat.app.model.event.GroupApplyEvent;
import com.bochat.app.model.util.PinYinUtil;
import com.bochat.app.mvp.view.BaseFragment;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/31 09:57
 * Description :
 */

public class AddressBookBaseLayout implements SpringView.OnFreshListener, AddressBookFragment.NotificationShower {
    public static final int ADDRESS_TEAM = 1;
    public static final int ADDRESS_PUBLIC = 2;
    public static final int ADDRESS_NOTIC = 3;
    private RecyclerView recycler;
    private SpringView springView;
    private AddressBookAdapter adapter;
    private List<AddressBookItem> items = new ArrayList<>();

    private String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private BaseFragment fragment;
    private AddressBookListener listener;
    private String totalCountFormat;
    private String notificationCount = "0";

    public AddressBookBaseLayout(BaseFragment fragment, String totalCountFormat, AddressBookListener listener) {
        this.fragment = fragment;
        this.listener = listener;
        this.totalCountFormat = totalCountFormat;
        recycler = fragment.getContentView().findViewById(R.id.recycler);
        springView = fragment.getContentView().findViewById(R.id.springView);
        initRecyclerView();
        initSpringView();
    }

    public void updateList(int count, List<? extends AddressBookItem> list, boolean isTotalShow, int type) {
        notificationCount = String.valueOf(count);
        items.clear();
        items.addAll(formatList(list, isTotalShow, type));
        adapter.notifyDataSetChanged();
    }

    public void onFinishFreshAndLoad() {
        springView.onFinishFreshAndLoad();
    }

    private List<AddressBookItem> formatList(List<? extends AddressBookItem> list, boolean isTotalShow, int type) {
        List<AddressBookItem> temperaList = new ArrayList<>();
        if (list != null) {
            if (type == ADDRESS_NOTIC) {
                AddressBookItem notificationItem = new AddressBookNotificationItem(notificationCount);
                temperaList.add(notificationItem);
            } else if (type == ADDRESS_TEAM) {
                AddressBookItem teamTopItem = new AddressBookInvitationItem(notificationCount);
                temperaList.add(teamTopItem);
                AddressBookItem bookItem = new AddressBookTeamCoutItem(notificationCount);
                temperaList.add(bookItem);
            } else if (type == ADDRESS_PUBLIC) {
                AddressBookItem publicTopItem = new AddressBookPublicItem(notificationCount);
                temperaList.add(publicTopItem);
            }


            ArrayList<String> letters = new ArrayList<>();
            for (int i = 0; i < letter.length; i++) {
                boolean hasLetterDiv = false;
                for (int j = 0; j < list.size(); j++) {
                    if (!TextUtils.isEmpty(list.get(j).getFirstPinYin())) {

                        if (list.get(j).getFirstPinYin().substring(0, 1).toUpperCase().equals(letter[i])) {
                            if (!hasLetterDiv && type == ADDRESS_NOTIC) {
                                letters.add(letter[i]);
                                temperaList.add(new AddressBookPinyinItem(letter[i]));
                                hasLetterDiv = true;
                            }


                            list.get(j).setType(1);
                            temperaList.add(list.get(j));
                        }
                    }
                }
            }
            if (type == ADDRESS_NOTIC) {
                List<AddressBookItem> otherList = new ArrayList<>();
                for (AddressBookItem entity : list) {
                    if (!temperaList.contains(entity)) {
                        entity.setType(1);
                        otherList.add(entity);
                    }
                }
                if (!otherList.isEmpty()) {
                    AddressBookPinyinItem otherLetter = new AddressBookPinyinItem("#");
                    temperaList.add(otherLetter);
                    letters.add("#");
                }

                EventBus.getDefault().post(letters);

                Collections.sort(otherList, new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        String nick1 = ((AddressBookItem) o1).getTitle();
                        String nick2 = ((AddressBookItem) o2).getTitle();
                        int f1 = TextUtils.isEmpty(nick1) ? 0 : nick1.charAt(0);
                        int f2 = TextUtils.isEmpty(nick2) ? 0 : nick2.charAt(0);
                        return f1 == f2 ? 0 : f1 < f2 ? 1 : -1;
                    }
                });
                temperaList.addAll(otherList);

                int count = 0;
                for (AddressBookItem item : temperaList) {
                    if (item.getType() == 1) {
                        count++;
                    }
                }
                if (isTotalShow) {
                    AddressBookTotalCountItem totalCountItem = new AddressBookTotalCountItem(String.valueOf(count));
                    temperaList.add(totalCountItem);
                }
            }

        }
        return temperaList;
    }

    private void initRecyclerView() {
        adapter = new AddressBookAdapter(fragment.getActivity(), items);

        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position, AddressBookItem friendEntity) {
                listener.onItemClick(friendEntity);
            }

            @Override
            public void onNotificationClick() {
                FriendApplyEvent friendApplyEvent = new FriendApplyEvent();
                friendApplyEvent.setClearAll(true);
                EventBus.getDefault().post(friendApplyEvent);
                GroupApplyEvent groupApplyEvent = new GroupApplyEvent();
                groupApplyEvent.setClearAll(true);
                EventBus.getDefault().post(groupApplyEvent);
                Router.navigation(new RouterApplyList());
            }

            @Override
            public void onInvitationClick() {
                Router.navigation(new RouterInvitation());
            }

            @Override
            public void onAddToPublicOnClick() {
                Router.navigation(new RouterAddressPublicNum());

            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(fragment.getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(fragment.getActivity()));
        springView.setFooter(new DefaultFooter(fragment.getActivity()));
    }

    @Override
    public void onRefresh() {
        listener.onRefresh();
    }

    @Override
    public void onLoadmore() {
        listener.onLoadMore();
    }

    @Override
    public void setNotificationCount(int num) {
        notificationCount = String.valueOf(num);
        if (items != null && !items.isEmpty() && items.get(0).getType() == 3) {
            items.get(0).setExtra(String.valueOf(num));
            adapter.notifyItemChanged(0);
        }
    }

    public interface AddressBookListener {
        void onRefresh();

        void onLoadMore();

        void onItemClick(AddressBookItem addressBookItem);
    }

    public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressUserViewHolder> {

        private List<AddressBookItem> items;
        private Context context;
        private OnItemClickListener onItemClickListenner;

        public void setOnItemClickListener(OnItemClickListener onItemClickListenner) {
            this.onItemClickListenner = onItemClickListenner;
        }

        public AddressBookAdapter(Context context, List<AddressBookItem> items) {
            this.items = items;
            this.context = context;
        }

        public void refreshData(List<AddressBookItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        public void loadData(List<AddressBookItem> items) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AddressBookAdapter.AddressUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.pinyin_item, null, false);
            } else if (viewType == 1) {//用户条目
                view = LayoutInflater.from(context).inflate(R.layout.fragment_address_user_item, null, false);
            } else if (viewType == 2) {//设置好友数
                view = LayoutInflater.from(context).inflate(R.layout.item_address_book_user_dark_total_num, null, false);
            } else if (viewType == 4) {
                view = LayoutInflater.from(context).inflate(R.layout.item_address_book_user_invitation, null, false);
            } else if (viewType == 3) {
                view = LayoutInflater.from(context).inflate(R.layout.item_address_book_user_dark_notification, null, false);
            } else if (viewType == 5) {
//                view = LayoutInflater.from(context).inflate(R.layout.item_address_book_user_invitation,null,false);
                view = LayoutInflater.from(context).inflate(R.layout.item_address_book_user_dark_notification, null, false);
            }else if(viewType == 6){
                view = LayoutInflater.from(context).inflate(R.layout.item_address_book_team_count,null,false);
            }

            return new AddressBookAdapter.AddressUserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AddressBookAdapter.AddressUserViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == 0) {
                holder.pinyin_tx.setText(items.get(position).getExtra());
            } else if (type == 1) {
                Glide.with(context).load(items.get(position).getIcon()).into(holder.head_img);
                holder.userName.setText(items.get(position).getTitle());
                holder.userSign.setText(items.get(position).getContent());
            } else if (type == 2) {
                holder.totalCount.setText(items.get(position).getExtra());

            } else if (type == 3) {
                holder.notificationText.setText("新朋友通知");
                int num = 0;
                try {
                    num = Integer.valueOf(notificationCount);
                } catch (Exception e) {
                }
                if (num == 0) {
                    holder.notificationCount.setVisibility(View.INVISIBLE);
                } else {
                    holder.notificationCount.setText("");
                    holder.notificationCount.setVisibility(View.VISIBLE);
                }
            } else if (type == 4) {

            } else if (type == 5) {
                holder.notificationCount.setVisibility(View.VISIBLE);
                holder.notificationText.setText("添加公众号");
            } else if(type == 6){
                holder.team_count.setText("共邀请"+notificationCount+"位好友");
            } 
        }

        @Override
        public int getItemCount() {
            if (items == null) {
                return 0;
            } else {
                return items.size();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position).getType();
        }

        class AddressUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            SpImageView head_img;
            TextView userName;
            TextView userSign;
            TextView pinyin_tx;
            TextView totalCount;
            TextView notificationCount;
            TextView addText;
            TextView notificationText;
            TextView team_count;

            public AddressUserViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                userSign = itemView.findViewById(R.id.userSign);
                userName = itemView.findViewById(R.id.userName);
                head_img = itemView.findViewById(R.id.head_img);
                pinyin_tx = itemView.findViewById(R.id.pinyin_tx);
                totalCount = itemView.findViewById(R.id.user_dark_total_num);
                notificationCount = itemView.findViewById(R.id.user_dark_notification_num);
                notificationText = itemView.findViewById(R.id.user_dark_notification_text);
                addText = itemView.findViewById(R.id.address_book_add_text);
                team_count = itemView.findViewById(R.id.team_count);
            }

            @Override
            public void onClick(View v) {
                if (onItemClickListenner != null) {
                    if (items.get(getPosition()).getType() == 1) {
                        onItemClickListenner.onItemClick(getPosition(), (AddressBookItem) items.get(getPosition()));
                    } else if (items.get(getPosition()).getType() == 3) {
                        onItemClickListenner.onNotificationClick();
                    } else if (items.get(getPosition()).getType() == 4) {
                        onItemClickListenner.onInvitationClick();
                    } else if (items.get(getPosition()).getType() == 5) {
                        onItemClickListenner.onAddToPublicOnClick();
                    }
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, AddressBookItem friendEntity);

        void onNotificationClick();

        void onInvitationClick();

        void onAddToPublicOnClick();
    }

    public static abstract class AddressBookItem<T> {
        private String firstPinYin;
        private T object;
        private int type;
        private String title;
        private String content;
        private String icon;
        private String extra;

        public AddressBookItem(T object) {
            this.object = object;
            setTitle(provideTitle(object));
            setContent(provideContent(object));
            setIcon(provideIcon(object));
            setType(provideType(object));
            setFirstPinYin(PinYinUtil.getFirstSpell(getTitle()));
        }

        public abstract String provideTitle(T object);

        public abstract String provideContent(T object);

        public abstract String provideIcon(T object);

        public abstract int provideType(T object);

        public String getFirstPinYin() {
            return firstPinYin;
        }

        public void setFirstPinYin(String firstPinYin) {
            this.firstPinYin = firstPinYin;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public T getObject() {
            return object;
        }

        public void setObject(T object) {
            this.object = object;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }

    private class AddressBookPinyinItem extends AddressBookItem<String> {

        public AddressBookPinyinItem(String object) {
            super(object);
        }

        @Override
        public String provideTitle(String object) {
            return "";
        }

        @Override
        public String provideContent(String object) {
            return "";
        }

        @Override
        public String provideIcon(String object) {
            return "";
        }

        @Override
        public int provideType(String object) {
            return 0;
        }

        @Override
        public String getExtra() {
            return getObject();
        }
    }

    private class AddressBookTotalCountItem extends AddressBookItem<String> {

        public AddressBookTotalCountItem(String object) {
            super(object);
        }

        @Override
        public String provideTitle(String object) {
            return "";
        }

        @Override
        public String provideContent(String object) {
            return "";
        }

        @Override
        public String provideIcon(String object) {
            return "";
        }

        @Override
        public int provideType(String object) {
            return 2;
        }

        @Override
        public String getExtra() {
            return String.format(totalCountFormat, getObject());
        }
    }

    private class AddressBookNotificationItem extends AddressBookItem<String> {

        public AddressBookNotificationItem(String object) {
            super(object);
        }

        @Override
        public String provideTitle(String object) {
            return "";
        }

        @Override
        public String provideContent(String object) {
            return "";
        }

        @Override
        public String provideIcon(String object) {
            return "";
        }

        @Override
        public int provideType(String object) {
            return 3;
        }

        @Override
        public String getExtra() {
            return getObject();
        }
    }


    private class AddressBookInvitationItem extends AddressBookItem<String> {


        public AddressBookInvitationItem(String object) {
            super(object);
        }

        @Override
        public String provideTitle(String object) {
            return "";
        }

        @Override
        public String provideContent(String object) {
            return "";
        }

        @Override
        public String provideIcon(String object) {
            return "";
        }

        @Override
        public int provideType(String object) {
            return 4;
        }

        @Override
        public String getExtra() {
            return getObject();
        }
    }

    private class AddressBookPublicItem extends AddressBookItem<String> {


        public AddressBookPublicItem(String object) {
            super(object);
        }

        @Override
        public String provideTitle(String object) {
            return "";
        }

        @Override
        public String provideContent(String object) {
            return "";
        }

        @Override
        public String provideIcon(String object) {
            return "";
        }

        @Override
        public int provideType(String object) {
            return 5;
        }

        @Override
        public String getExtra() {
            return getObject();
        }
    }

    private class AddressBookTeamCoutItem extends AddressBookItem<String> {


        public AddressBookTeamCoutItem(String object) {
            super(object);
        }

        @Override
        public String provideTitle(String object) {
            return "";
        }

        @Override
        public String provideContent(String object) {
            return "";
        }

        @Override
        public String provideIcon(String object) {
            return "";
        }

        @Override
        public int provideType(String object) {
            return 6;
        }
    }
}