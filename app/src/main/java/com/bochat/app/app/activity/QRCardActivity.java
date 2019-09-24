package com.bochat.app.app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.CodeCreator;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.QRCardContract;
import com.bochat.app.common.router.RouterQRCard;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;

import static com.bochat.app.app.view.SpImageView.TYPE_CIRCLE;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 19:23
 * Description :
 */

@Route(path = RouterQRCard.PATH)
public class QRCardActivity extends BaseActivity<QRCardContract.Presenter> implements QRCardContract.View{
    
    @Inject
    QRCardContract.Presenter presenter;
    
    @BindView(R.id.qr_card_icon)
    SpImageView icon;
    @BindView(R.id.qr_card_name)
    TextView name;
    @BindView(R.id.qr_card_area)
    TextView area;
    @BindView(R.id.qr_card_code)
    ImageView code;
    @BindView(R.id.qr_card_tips)
    TextView tips;
    @BindView(R.id.qr_card_invite_layout)
    RelativeLayout inviteLayout;
    @BindView(R.id.qr_card_invite_content)
    TextView inviteContent;
    
    @BindView(R.id.qr_card_top_bar)
    BoChatTopBar boChatTopBar;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected QRCardContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_qr_card);
    }
    
    @Override
    public void updateFriendQRCard(FriendEntity content) {
        icon.setType(TYPE_CIRCLE);
        Glide.with(this).load(content.getHead_img()).into(icon);
        boChatTopBar.setTitleText("好友二维码");
        name.setText(content.getNickname());
        area.setText(content.getArea());
        tips.setText("扫一扫上面的二维码图案，加我好友");
        inviteContent.setText("");
        inviteLayout.setVisibility(View.INVISIBLE);
        code.setImageBitmap(createQRCode("1", String.valueOf(content.getId())));
    }

    @Override
    public void updateGroupQRCard(GroupEntity content) {
        boChatTopBar.setTitleText("群聊二维码");
        icon.setType(TYPE_CIRCLE);
        Glide.with(this).load(content.getGroup_head()).into(icon);
        name.setText(content.getGroup_name());
        area.setText("");
        tips.setText("扫一扫上面的二维码图案，添加群聊");
        inviteLayout.setVisibility(View.INVISIBLE);
        code.setImageBitmap(createQRCode("2", content.getGroup_id()));
    }

    @Override
    public void updateUserQRCard(UserEntity content) {
        boChatTopBar.setTitleText("我的二维码");
        icon.setType(TYPE_CIRCLE);
        Glide.with(this).load(content.getHeadImg()).into(icon);
        name.setText(content.getNickname().trim());
        area.setText(content.getArea());
        tips.setText("扫一扫上面的二维码图案，加我好友");
        inviteContent.setText("");
        inviteLayout.setVisibility(View.INVISIBLE);
        code.setImageBitmap(createQRCode("1", String.valueOf(content.getId())));
    }

    private Bitmap createQRCode(String type, String id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("id", id);
            String content = jsonObject.toString();
            return CodeCreator.createQRCode(content, ResourceUtils.dip2px(this,R.dimen.dp_160),
                    ResourceUtils.dip2px(this, R.dimen.dp_160), null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Bitmap createQRCode(String type, long id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("id", id);
            String content = jsonObject.toString();
            return CodeCreator.createQRCode(content, ResourceUtils.dip2px(this,R.dimen.dp_160),
                    ResourceUtils.dip2px(this, R.dimen.dp_160), null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
