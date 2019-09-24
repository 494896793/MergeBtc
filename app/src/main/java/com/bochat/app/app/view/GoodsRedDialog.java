package com.bochat.app.app.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bochat.app.R;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.modelImpl.UserModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2019/6/28
 * Author LDL
 **/
public class GoodsRedDialog extends Dialog {


    @BindView(R.id.detail_text)
    TextView detail_text;

    @BindView(R.id.sure_bt)
    TextView sure_bt;

    @BindView(R.id.coin_close)
    Button coin_close;

    @BindView(R.id.money_label)
    TextView money_label;

    @BindView(R.id.money_unit)
    TextView money_unit;

    @BindView(R.id.notice_text)
    TextView notice_text;
    
    @BindView(R.id.statu_label)
    TextView statu_label;
    
    @BindView(R.id.money_tips)
    TextView money_tips;

    private IUserModel userModel;
    
    private ShakyCandyEntity entity;
    private Context context;

    public GoodsRedDialog(Context context) {
        super(context,R.style.TransparentDialog);
        this.context=context;
    }

    public GoodsRedDialog(Context context, int themeResId) {
        super(context,R.style.TransparentDialog);
        this.context=context;
    }

    @Override
    public void show() {
        super.show();
    }

    
    public void setEntity(ShakyCandyEntity entity){
        this.entity=entity;
        if("0".equals(entity.getGetType())){
            statu_label.setVisibility(View.INVISIBLE);
            money_unit.setVisibility(View.GONE);
            sure_bt.setVisibility(View.VISIBLE);
            notice_text.setText("");
            money_tips.setText("— 糖果会自动发放至钱包 —");
            
            money_label.setText("领取糖果礼包");
            
        } else if("1".equals(entity.getGetType())) {
            statu_label.setVisibility(View.VISIBLE);
            money_unit.setVisibility(View.VISIBLE);
            sure_bt.setVisibility(View.INVISIBLE);
            notice_text.setText(entity.getHint());
            money_tips.setText("— 已自动发放至钱包 —");
            
            money_unit.setText(entity.getCurrencyName());
            money_label.setText(entity.getNum());
        }
    }

    protected GoodsRedDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context,R.style.TransparentDialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goodsred);
        ButterKnife.bind(this);
        setStyle();
    }

    @OnClick({R.id.coin_close,R.id.sure_bt,R.id.detail_text})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.detail_text:
                Router.navigation(new RouterDynamicWebView(entity.getUrl(),"活动详情"));
                dismiss();
                break;
            case R.id.coin_close:
                dismiss();
                break;
            case R.id.sure_bt:
                if(userModel == null){
                    userModel = new UserModule();
                }
                Disposable subscribe = Observable.create(new ObservableOnSubscribe<ShakyCandyEntity>() {
                    @Override
                    public void subscribe(ObservableEmitter<ShakyCandyEntity> emitter) throws Exception {
                        try {
                            ShakyCandyEntity entity = userModel.insertActivityRecord();
                            if(entity.getRetcode() != 0){
                                emitter.onError(new RxErrorThrowable(entity));
                            }
                            entity.setGetType("1");
                            emitter.onNext(entity);
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                            e.printStackTrace();
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ShakyCandyEntity>() {
                    @Override
                    public void accept(ShakyCandyEntity entity) throws Exception {
                        setEntity(entity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        int icon = R.mipmap.tips_icon;
                        Toast toast = ToastHelper.makeToast(context, "领取失败", icon);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
                break;
        }
    }

    /**
     * 设置样式
     */
    private void setStyle() {
        WindowManager.LayoutParams layoutParams;
        if (getWindow() != null) {
            layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width= context.getResources().getDimensionPixelSize(R.dimen.dp_270);
            getWindow().setAttributes(layoutParams);
            getWindow().setWindowAnimations(R.style.mystyle);
        }
    }
}
