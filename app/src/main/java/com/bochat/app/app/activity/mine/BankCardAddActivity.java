package com.bochat.app.app.activity.mine;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.common.contract.mine.AddBankCardContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBankCardAdd;
import com.bochat.app.common.router.RouterBankSelect;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:54
 * Description :
 */

@Route(path = RouterBankCardAdd.PATH)
public class BankCardAddActivity extends BaseActivity<AddBankCardContract.Presenter> implements AddBankCardContract.View{

    @Inject
    AddBankCardContract.Presenter presenter;

    @BindView(R.id.mine_bank_card_add_select)
    LinearLayout bankSelect;

    @BindView(R.id.mine_bank_card_add_input)
    BoChatItemView bankInput;

    @BindView(R.id.mine_bank_card_add_name_input)
    BoChatItemView nameInput;

    @BindView(R.id.mine_bank_card_add_id_input)
    BoChatItemView idInput;
    @BindView(R.id.mine_bank_card_icon)
    ImageView bankIcon;
    @BindView(R.id.bochat_item_content)
    TextView bankCardText;

    private BankCard bankCard;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected AddBankCardContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_bank_card_add);
    }

    @Override
    public void updateUserName(String name) {
        nameInput.getEditText().setText(name);
    }

    @Override
    public void updateBank(BankCard bankCard) {
        this.bankCard = bankCard;
        bankCardText.setText(bankCard.getBankName());
        bankIcon.setVisibility(View.VISIBLE);
        Glide.with(this).load(bankCard.getBankIcon()).into(bankIcon);


    }

    @OnClick({R.id.mine_bank_card_add_select, R.id.mine_bank_card_add_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if(view.getId() == R.id.mine_bank_card_add_select){
            Router.navigation(new RouterBankSelect(), RouterBankCardAdd.class);
        } else {
            presenter.onAddBankCardEnter(bankCard,
                    bankInput.getEditText().getText().toString(),
                    idInput.getEditText().getText().toString());
        }
    }
}