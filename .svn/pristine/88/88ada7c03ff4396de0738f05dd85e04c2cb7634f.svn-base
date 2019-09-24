package com.bochat.app.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bochat.app.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupManageJoinFilterAdapter1 extends BaseAdapter {
    List<String> mDate;
    Context mContext;
    private Map<Integer,Boolean> isCheckMap = new HashMap<>();
    private ImageView isCheckImage;
    private int selectPosition = -1;//用于记录用户选择的变量
    private String selectBrand; //用户选择的品牌
    LayoutInflater mInflater;

    public GroupManageJoinFilterAdapter1(List<String> mDate, Context mContext) {
        this.mDate = mDate;
        this.mContext = mContext;
        for (int i = 0;i<mDate.size();i++){
            isCheckMap.put(i,false);
        }
    }


    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public Object getItem(int position) {
        return mDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_group_manage_join_type,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.strType = (TextView)convertView.findViewById(R.id.group_join_type_title);
            viewHolder.checkImag = (ImageView)convertView.findViewById(R.id.group_join_type_icon);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.strType.setText(mDate.get(position));
        if(selectPosition == position){
//            viewHolder.checkImag.setChecked(true);
            viewHolder.checkImag.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.checkImag.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setIsCheckMap(int positon){
        isCheckImage.setVisibility(View.VISIBLE);
        isCheckMap.put(positon,true);
        notifyDataSetChanged();

    }


    /*public void add(List<String> )*/

    public class ViewHolder{
        TextView strType;
        ImageView checkImag;
    }

}
