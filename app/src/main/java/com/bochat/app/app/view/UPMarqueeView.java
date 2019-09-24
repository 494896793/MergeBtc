package com.bochat.app.app.view;

/**
 * 2019/6/28
 * Author ZZW
 **/

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.bochat.app.R;

import java.util.List;

/**
 * 仿淘宝首页的 淘宝头条滚动的自定义View
 *
 * Created by dreamlive on 2016/7/20.
 */
public class UPMarqueeView extends ViewFlipper {

    private Context mContext;
    private boolean isSetAnimDuration = false;
    private int interval = 2000;

    /**
     * 动画时间
     */
    private int animDuration = 1000;

    public UPMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    public void addViews(View view){
        Log.i("======","======addViews:"+getChildViewCount());
        if(getChildViewCount()>=50){
            removeViewAt(0);
        }
        if(view!=null){
            addView(view);
            if(!isFlipping()){
                startFlipping();
            }
        }
    }


    public int getChildViewCount(){
        return getChildCount();
    }

    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    public void setViews(List<View> views) {
        if (views == null || views.size() == 0) return;
        removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            addView(views.get(i));
        }
        startFlipping();
    }


}
