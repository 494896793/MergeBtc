package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.CommandDailog;
import com.bochat.app.model.bean.NewGroupManagerEntivity;
import com.bochat.app.model.util.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupMangerSettingAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<NewGroupManagerEntivity> mData;
    public static final int text_layout = 0;
    public static final int owner_layout = 1;
    public static final int manger_layout = 2;
    private LayoutInflater inflater;
    private boolean isShowDeleteImg;
    private onManagerSettingListner listner;



    public GroupMangerSettingAdapter(Context mContext, List<NewGroupManagerEntivity> mData) {
        this.mContext = mContext;
        this.mData = mData;

        inflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        if (viewType == owner_layout){
            View view = inflater.inflate(R.layout.item_cv_group_add_manager,parent,false);
            return new OwnerViewHolder(view);
        }


        if (viewType == manger_layout){
            View view = inflater.inflate(R.layout.item_cv_group_add_manager,parent,false);
            return new ManagerViewHolder(view);
        }
        if (viewType == text_layout){
            LogUtil.LogDebug("ggyy","gg++"+inflater);
            View view = inflater.inflate(R.layout.item_query_manager_owner,parent,false);
            return new TextViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        View view = null;

        final NewGroupManagerEntivity entivity = mData.get(position);
        if (entivity.getRole() == 4 || entivity.getRole() == 5){
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.textView.setText(entivity.getNickname());
        }
        if (entivity.getRole() == 3){
            OwnerViewHolder ownerViewHolder = (OwnerViewHolder) holder;
            Glide.with(mContext)
                    .asBitmap()
                    .load(entivity.getHead_img())
                    .transform(new CircleCrop())
                    .error(R.mipmap.ic_default_head).into(ownerViewHolder.userImage);
            ownerViewHolder.userNickName.setText(entivity.getNickname());
            ownerViewHolder.userId.setText("ID"+String.valueOf(entivity.getId()));
//            ownerViewHolder.userId.setText(entivity.getId());
        }

        if (entivity.getRole() == 2){
            ManagerViewHolder managerViewHolder = (ManagerViewHolder) holder;
            Glide.with(mContext)
                    .asBitmap()
                    .load(entivity.getHead_img())
                    .transform(new CircleCrop())
                    .error(R.mipmap.ic_default_head).into(managerViewHolder.userImage);
            managerViewHolder.userNickName.setText(entivity.getNickname());
            managerViewHolder.userId.setText("ID"+String.valueOf(entivity.getId()));

            if (isShowDeleteImg){
                managerViewHolder.managerDelete.setVisibility(View.VISIBLE);
                managerViewHolder.managerDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CommandDailog dailog = new CommandDailog(mContext,R.style.CommandDialog);
                        dailog.setMessage("您是否确认删除管理员 " + entivity.getNickname());
                        dailog.setYesOnclickListener("确定", new CommandDailog.onYesOnclickListener() {
                            @Override
                            public void onYesOnclick() {
                                listner.removManger(position);
                                dailog.dismiss();


                            }
                        });
                        dailog.setNoOnclickListener("取消", new CommandDailog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                dailog.dismiss();
                            }
                        });
                        dailog.show();

                    }
                });
            }else {
                managerViewHolder.managerDelete.setVisibility(View.GONE);
            }

        }


    }

    @Override
    public int getItemCount() {
        return mData.size();

    }

    @Override
    public int getItemViewType(int position) {
//        LogUtil.LogDebug("ggyy","position = "+position);
        NewGroupManagerEntivity bean = mData.get(position);
        if(bean.getRole() == 2){
            return  manger_layout;
        }else if(bean.getRole() == 3){
            return owner_layout;
        }else{
            return text_layout;
        }
    }

    private class TextViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        View mView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_mananger_list);
            mView = itemView.findViewById(R.id.div_);
        }
    }

    private class OwnerViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userNickName;
        TextView userId;
        ImageView addManagerImageCheck;
        RelativeLayout addManagerItem;
        public OwnerViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.add_manager_user_img);
            userNickName = itemView.findViewById(R.id.add_manager_user_nick_name);
            userId = itemView.findViewById(R.id.add_manager_user_member);
            addManagerImageCheck = itemView.findViewById(R.id.add_manager_check);
            addManagerItem = itemView.findViewById(R.id.add_manager_item);

        }
    }

    private class ManagerViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userNickName;
        TextView userId;
        ImageView addManagerImageCheck;
        RelativeLayout addManagerItem;
        TextView managerDelete;
        public ManagerViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.add_manager_user_img);
            userNickName = itemView.findViewById(R.id.add_manager_user_nick_name);
            userId = itemView.findViewById(R.id.add_manager_user_member);
            addManagerImageCheck = itemView.findViewById(R.id.add_manager_check);
            addManagerItem = itemView.findViewById(R.id.add_manager_item);
            managerDelete =itemView.findViewById(R.id.delete_manager);

        }
    }

    public void add(List<NewGroupManagerEntivity> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMoreData(List<NewGroupManagerEntivity> addMessageList) {
        //加载更多数据
        int position = mData.size();
        mData.addAll(position, addMessageList);
        notifyItemInserted(position);
    }

    public void removeItem(int positon){
        mData.remove(positon);
        notifyDataSetChanged();
    }

    public void  showDeleteButton(boolean Show){
        isShowDeleteImg = Show;
        notifyDataSetChanged();
    }

    public void setOnManagerSettingListner(onManagerSettingListner listner){
        this.listner = listner;
    }

    public interface onManagerSettingListner {
        void removManger(int position);

    }

}
