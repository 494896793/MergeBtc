package com.bochat.app.mvp.presenter;

import com.bochat.app.business.BusinessComponent;
import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.common.router.AbstractRouter;
import com.bochat.app.mvp.view.IBaseView;

import java.lang.ref.WeakReference;

/**
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/08 09:49
 * Description:
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected WeakReference<V> viewReference;

    private boolean isActive;

    protected BusinessComponent businessComponent;

    public abstract void initInjector();
    
    public BasePresenter(){
        businessComponent = DaggerBusinessComponent.builder().build();
        initInjector();
    }
    
    @Override
    public void onViewAttached(V view) {
        viewReference = new WeakReference<V>(view);
        this.isActive = true;
    }

    @Override
    public void onViewDetached() {
        if(viewReference != null) {
            viewReference.clear();
            viewReference = null;
        }
        this.isActive = false;
    }

    @Override
    public void onViewInactivation() {
    }

    @Override
    public void onViewRefresh() {
        
    }

    @Override
    public boolean isActive() {
        V view = getView();
        return view != null && isActive;
    }

    @Override
    public V getView() {
        if(viewReference != null) {
            return viewReference.get();
        }
        return null;
    }
    
    public <T> T getExtra(Class<T> type){
        try {
            return (T)(getView().getViewIntent().getSerializableExtra(AbstractRouter.TAG));
        } catch (Exception e){
        }
        return null;
    }
    
    public BusinessComponent getBusinessComponent(){
        return businessComponent;
    }
}