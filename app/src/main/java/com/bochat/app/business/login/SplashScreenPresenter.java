package com.bochat.app.business.login;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.SplashScreenContract;
import com.bochat.app.common.model.IUpgradeModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.model.bean.AdvertEntity;
import com.bochat.app.model.bean.AdvertListEntity;
import com.bochat.app.model.util.TimeUtils;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/10 13:42
 * Description :
 */
@SuppressWarnings({"CheckResult", "ResultOfMethodCallIgnored"})
public class SplashScreenPresenter extends BasePresenter<SplashScreenContract.View> implements SplashScreenContract.Presenter {

    @Inject
    IUpgradeModel upgradeModel;

    Disposable mSetupDispoable;

    @Override
    public void onScreenClick() {
        getView().showTips("恭喜发财，红包拿来！");
    }

    @Override
    public void getAdvertPageList() {
        Observable.create(new ObservableOnSubscribe<AdvertListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<AdvertListEntity> emitter) throws Exception {
                
                Thread.sleep(800);
                
                AdvertListEntity entity = upgradeModel.getAdvertPageList();
                if (entity.getRetcode() != 0) {
                    emitter.onError(new RxErrorThrowable(entity));
                } else {
                    emitter.onNext(entity);
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AdvertListEntity>() {
            @Override
            public void accept(AdvertListEntity entity) {
                if (isActive())
                    getView().obtainAdvertList(entity);
            }
        }, new Consumer<Throwable>() {

            @Override
            public void accept(Throwable throwable) throws Exception {
                if (isActive())
                    getView().obtainAdvertList(null);
            }
        });
    }

    @Override
    public void onSkipButtonClick() {
        Router.navigation(new RouterLogin());
    }

    @Override
    public void setupAdvert(final List<AdvertEntity> entities, final int delay) {
        mSetupDispoable = Observable.interval(0, delay, TimeUnit.SECONDS)
                .map(new Function<Long, AdvertEntity>() {
                    @Override
                    public AdvertEntity apply(Long aLong) {
                        return entities.get(aLong.intValue() % entities.size());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AdvertEntity>() {
                    @Override
                    public void accept(AdvertEntity entity) throws Exception {
                        getView().loadAdvert(entity);
                    }
                });
    }

    @Override
    public void onViewInactivation() {
        getView().finish();
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        loadNetTime();
    }

    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    private void loadNetTime() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                for (int i = 0; i < 10; i++) {
                    try {
                        long netTime = getNetTime();
                        long localTime = System.currentTimeMillis();
                        TimeUtils.setNetworkTimeOffset(localTime - netTime);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static long getNetTime() throws IOException {
        String webUrl = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
        URL url = new URL(webUrl);
        URLConnection uc = url.openConnection();
        uc.setReadTimeout(5000);
        uc.setConnectTimeout(5000);
        uc.connect();
        return uc.getDate();
    }

    @Override
    public void onViewDetached() {
        super.onViewDetached();
        if (mSetupDispoable != null)
            mSetupDispoable.dispose();
    }
}