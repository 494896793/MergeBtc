package com.bochat.app.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bochat.app.R;
import com.bochat.app.app.DaggerFragmentComponent;

import com.bochat.app.app.FragmentComponent;
import com.bochat.app.app.view.LoadingDialog;
import com.bochat.app.app.view.ToastHelper;
import com.bochat.app.common.router.AbstractRouter;
import com.bochat.app.common.util.ALog;
import com.bochat.app.mvp.injector.ApplicationComponentProvider;
import com.bochat.app.mvp.injector.module.FragmentModule;
import com.bochat.app.mvp.presenter.IBasePresenter;

import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import razerdp.basepopup.BasePopupWindow;


/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 15:10
 * Description :
 */

public abstract class BaseFragment <P extends IBasePresenter> extends Fragment implements IBaseView<P>{

    protected FragmentComponent fragmentComponent;

    private LoadingDialog loadingDialog;

    private View contentView;

    protected abstract void initInjector();

    protected P presenter;

    protected abstract P initPresenter();

    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .applicationComponent(ApplicationComponentProvider.getApplicationComponent())
                .build();


        initInjector();
        presenter = initPresenter();
        contentView =null;
        try
        {
            contentView = getRootView(inflater, container, savedInstanceState);
            ButterKnife.bind(this, contentView);
            initWidget();

        }catch (Exception e){
            e.printStackTrace();
        }
        return contentView;
    }

    public View getContentView(){
        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onViewAttached(this);
        }
        ALog.d(getClass().getSimpleName() + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewRefresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null){
            presenter.onViewInactivation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onViewDetached();
        }
        if(loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    public <T> T getExtra(Class<T> type){
        try {
            return (T)(getViewIntent().getSerializableExtra(AbstractRouter.TAG));
        } catch (Exception e){
        }
        return null;
    }
    
    @Override
    public void showTips(final TipsType tip) {
        int icon = R.mipmap.success_icon;
        if(tip instanceof ResultTipsType && !((ResultTipsType) tip).isSuccess()){
            icon = R.mipmap.tips_icon;
        }
        Toast toast = ToastHelper.makeToast(getViewContext(), tip.getMessage(), icon);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void showTips(final String tip) {
        showTips(new ResultTipsType(tip, true));
    }

    @Override
    public void showLoading(final Object extras) {
        try{
            if(loadingDialog == null){
                loadingDialog = new LoadingDialog(getActivity());
            }
            loadingDialog.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if(extras instanceof Disposable){
                        if(!((Disposable) extras).isDisposed()){
                            ((Disposable) extras).dispose();
                        }
                    }
                }
            });
            loadingDialog.showPopupWindow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoading(Object extras) {
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
        if(extras instanceof String){
            if(!TextUtils.isEmpty((String)extras)){
                showTips((String)extras);
            }
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public Intent getViewIntent() {
        return getActivity().getIntent();
    }

    @Override
    public void setViewIntent(Intent intent) {
        getActivity().setIntent(intent);
    }

    protected FragmentComponent getFragmentComponent(){
        return fragmentComponent;
    }


    protected  void initWidget(){

    }

    protected void onViewClicked(View view){

    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void finishAll(boolean containSelf){

    }
}
