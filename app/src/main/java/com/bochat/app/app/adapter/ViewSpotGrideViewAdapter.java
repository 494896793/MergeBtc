package com.bochat.app.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bochat.app.R;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

/**
 * 2019/5/24
 * Author LDL
 **/
public class ViewSpotGrideViewAdapter extends BaseAdapter {

    private List<String> imgs;
    private Context mContext;

    public ViewSpotGrideViewAdapter(List<String> imgs,Context mContext){
        this.imgs=imgs;
        this.mContext=mContext;
    }

    public void refresh(List<String> imgs){
        this.imgs=imgs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.viewspot_gridview_item,null);
        ImageView img=view.findViewById(R.id.img);
        Glide.with(mContext).load(imgs.get(position)).transform(new CenterCrop(),new GlideRoundTransform(mContext)).into(img);
        return view;
    }
}
