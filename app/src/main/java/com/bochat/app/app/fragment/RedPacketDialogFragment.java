package com.bochat.app.app.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.MainApplication;
import com.bochat.app.R;
import com.bochat.app.model.animation.FrameAnimation;
import com.bochat.app.model.bean.RedPacketDialogDataEntity;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;

import butterknife.BindView;

/**
 * 2019/5/13
 * Author LDL
 **/
public class RedPacketDialogFragment extends DialogFragment implements View.OnClickListener {//Contract

    private ImageView open_redpacket_img;
    private OnGetRedPacket getRedPacket;
    private ImageView close_img;
    private FrameAnimation animation;
    private ImageView head_img;
    private TextView name_text;
    private TextView title_text;
    private RedPacketDialogDataEntity redPacketDialogDataEntity;

    private int[] coints={R.mipmap.redpacket_coin_frame_a,R.mipmap.redpacket_coin_frame_b,R.mipmap.redpacket_coin_frame_c,R.mipmap.redpacket_coin_frame_d,
            R.mipmap.redpacket_coin_frame_e,R.mipmap.redpacket_coin_frame_f,R.mipmap.redpacket_coin_frame_g,R.mipmap.redpacket_coin_frame_h
            ,R.mipmap.redpacket_coin_frame_i,R.mipmap.redpacket_coin_frame_j,R.mipmap.redpacket_coin_frame_k,R.mipmap.redpacket_coin_frame_l
            ,R.mipmap.redpacket_coin_frame_m,R.mipmap.redpacket_coin_frame_n,R.mipmap.redpacket_coin_frame_o,R.mipmap.redpacket_coin_frame_p
            ,R.mipmap.redpacket_coin_frame_q,R.mipmap.redpacket_coin_frame_r,R.mipmap.redpacket_coin_frame_s,R.mipmap.redpacket_coin_frame_t
            ,R.mipmap.redpacket_coin_frame_aa,R.mipmap.redpacket_coin_frame_bb,R.mipmap.redpacket_coin_frame_cc,R.mipmap.redpacket_coin_frame_dd
            ,R.mipmap.redpacket_coin_frame_ee,R.mipmap.redpacket_coin_frame_ff,R.mipmap.redpacket_coin_frame_gg,R.mipmap.redpacket_coin_frame_hh
            ,R.mipmap.redpacket_coin_frame_ii,R.mipmap.redpacket_coin_frame_jj,R.mipmap.redpacket_coin_frame_kk
    };

    private RedPacketMessage message;

    public void setGetRedPacket(OnGetRedPacket getRedPacket){
        this.getRedPacket=getRedPacket;
    }

    public void setMessage(RedPacketMessage message){
        this.message=message;
    }

    public void setRedPacketDialogDataEntity(RedPacketDialogDataEntity redPacketDialogDataEntity){
        this.redPacketDialogDataEntity=redPacketDialogDataEntity;
    }

    public void initData(){
        if(message!=null){
            title_text.setText(message.getText());
            name_text.setText(message.getUserInfo().getName());
            Glide.with(MainApplication.getInstance()).load(message.getUserInfo().getPortraitUri()).transform(new CenterInside(), new GlideRoundTransform(getContext(),30)).into(head_img);
        }else if(redPacketDialogDataEntity!=null){
            title_text.setText(redPacketDialogDataEntity.getText());
            name_text.setText(redPacketDialogDataEntity.getName());
            Glide.with(MainApplication.getInstance()).load(redPacketDialogDataEntity.getImg()).transform(new CenterInside(), new GlideRoundTransform(getContext(),30)).into(head_img);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_redpacketdialog,null);
        open_redpacket_img=view.findViewById(R.id.open_redpacket_img);
        name_text=view.findViewById(R.id.name_text);
        title_text=view.findViewById(R.id.title_text);
        head_img=view.findViewById(R.id.head_img);
        close_img=view.findViewById(R.id.close_img);
        close_img.setOnClickListener(this);
        open_redpacket_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getRedPacket!=null){
                    getRedPacket.onOpenRedPacket(message);
                    getRedPacket.onOpenRedPacketOther(redPacketDialogDataEntity);
                }
            }
        });
        initData();
        return view;
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    public void startAnimation(){
        animation=new FrameAnimation(open_redpacket_img,coints,8,true);
        animation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                Log.i("", "start");
            }

            @Override
            public void onAnimationEnd() {
                Log.i("", "end");
            }

            @Override
            public void onAnimationRepeat() {
                Log.i("", "repeat");
            }

            @Override
            public void onAnimationPause() {
                open_redpacket_img.setBackgroundResource(R.mipmap.redpacket_bid_icon);
            }
        });
    }

    public void stopAnimation(){
        if(animation!=null){
            animation.release();
            animation=null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.dp_305), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    public interface OnGetRedPacket{
        void onOpenRedPacket(RedPacketMessage message);
        void onOpenRedPacketOther(RedPacketDialogDataEntity entity);
    }
}
