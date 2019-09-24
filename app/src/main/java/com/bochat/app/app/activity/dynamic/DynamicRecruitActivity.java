package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.ShareDialog;
import com.bochat.app.common.contract.dynamic.DynamicRecruitContract;
import com.bochat.app.common.router.RouterDynamicRecruit;
import com.bochat.app.model.bean.DynamicIncomeOfTodayEntity;
import com.bochat.app.model.bean.DynamicQueryUserBXInfoEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterDynamicRecruit.PATH)
public class DynamicRecruitActivity extends BaseActivity<DynamicRecruitContract.Presenter> implements DynamicRecruitContract.View {


    @Inject
    DynamicRecruitContract.Presenter presenter;

    @BindView(R.id.liner_recruit)
    LinearLayout recruit;
    @BindView(R.id.liner_recruit_1)
    LinearLayout recruit_1;
    @BindView(R.id.liner_recruit_2)
    LinearLayout recruit_2;
    @BindView(R.id.dynamic_recruit_success)
    ImageView recruitSuccess;
    @BindView(R.id.dynamic_recruit_user_icon)
    ImageView userIcon;
    @BindView(R.id.dynamic_recruit_join_btn)
    Button joinBtn;
    @BindView(R.id.dynamic_recruit_resident_count)
    TextView residentCount;
    @BindView(R.id.dynamic_recruit_resident_total)
    TextView redidentTotal;
    @BindView(R.id.dynamic_recruit_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.dynamic_recruit_time)
    TextView timeText;

    @BindView(R.id.dynamic_recruit_balance)
    TextView mBalance;
    @BindView(R.id.dynamic_recruit_tomrrow_income_top)
    TextView mTomrrowIncomTop;
    @BindView(R.id.dynamic_recruit_tomrrow_income_below)
    TextView mTommrowIncomBelow;
    @BindView(R.id.dynamic_recruit_cum_income)
    TextView mCumulateIncome;
    @BindView(R.id.dynamic_recruit_daily_income)
    TextView mDailyIncome;

    @BindView(R.id.dynamic_recruit_bochat_bar)
    BoChatTopBar boChatTopBar;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected DynamicRecruitContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_recruit);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        RouterDynamicRecruit router = (RouterDynamicRecruit) getIntent().getSerializableExtra(RouterDynamicRecruit.TAG);
        boChatTopBar.setRightButtonInvisble(!router.isShare);
        if (router.isShare)
            boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
                @Override
                public void onRightExtButtonClick() {
                    presenter.onShareClick();
                }
            });
    }

    @OnClick({R.id.dynamic_recruit_join_btn})
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.dynamic_recruit_join_btn:
                presenter.onJoinButtonOnClick();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void updateUserImage(UserEntity userInfo) {
        Glide.with(this)
                .asBitmap()
                .load(userInfo.getHeadImg())
                .transform(new CircleCrop())
                .error(R.mipmap.ic_default_head).into(userIcon);
    }

    @Override
    public void updateRegisterLayout(DynamicQueryUserBXInfoEntity entity) {
        if (entity.getState() == 1) {
            recruit_1.setVisibility(View.GONE);
            recruit_2.setVisibility(View.VISIBLE);
            recruitSuccess.setVisibility(View.VISIBLE);
        }
        updateProgress(entity);
        timeText.setText(entity.getExtendInfo());
        recruit.setVisibility(View.VISIBLE);
    }

    private void updateProgress(DynamicQueryUserBXInfoEntity entity) {
        //设置进度条
        residentCount.setText(entity.getAmount() + "");
        redidentTotal.setText(" / " + (int) entity.getTatalAmount());
        mProgressBar.setMax((int) entity.getTatalAmount());
        mProgressBar.setProgress((int) entity.getAmount());
    }

    @Override
    public void updateTodayIncome(DynamicIncomeOfTodayEntity entity) {
        mBalance.setText(entity.getExtendInfo());
        if (entity.getAmount() == null ){
            mTomrrowIncomTop.setText("00.0000");
        }else {
            mTomrrowIncomTop.setText(String.valueOf(entity.getAmount()));
        }
        if (entity.getRatePerYear() == null){
            mTommrowIncomBelow.setText("0.000" + " BX");
        }else {
            mTommrowIncomBelow.setText(entity.getRatePerYear() + " BX");
        }

        if (entity.getTatalAmount() == null){
            mCumulateIncome.setText("0.000" + " BX");
        }else {
            mCumulateIncome.setText(entity.getTatalAmount() + " BX");
        }

        if (entity.getRatePerDay() == null){
            mDailyIncome.setText("0.00");
        }else{
            mDailyIncome.setText(entity.getRatePerDay());
        }




    }

    @Override
    public void showShareDialog(String shareUrl) {
        ShareDialog shareDialog = new ShareDialog(this, "邀请有礼", "邀请链接 [点击复制]", shareUrl);
        shareDialog.showPopupWindow();
    }
}
