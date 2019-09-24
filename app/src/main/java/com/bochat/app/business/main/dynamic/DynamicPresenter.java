package com.bochat.app.business.main.dynamic;

import com.bochat.app.R;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.fetcher.DynamicPlushProvider;
import com.bochat.app.common.contract.dynamic.DynamicContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IThirdServicesModel;
import com.bochat.app.model.bean.BitMallEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.DynamicAdapterEntity;
import com.bochat.app.model.bean.DynamicBannerListEntity;
import com.bochat.app.model.bean.DynamicPlushEntity;
import com.bochat.app.model.bean.DynamicTopShopEntity;
import com.bochat.app.model.bean.DynamicTopShopListEntity;
import com.bochat.app.model.bean.GameGoEntity;
import com.bochat.app.model.bean.ProjectIdentificationEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 2019/5/7
 * Author LDL
 **/
public class DynamicPresenter extends BasePresenter<DynamicContract.View> implements DynamicContract.Presenter {

    private DynamicPlushProvider dynamicPlushProvider;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Inject
    IDynamicModel model;

    @Inject
    IThirdServicesModel thirdServicesModel;

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        if(dynamicPlushProvider==null){
            dynamicPlushProvider=new DynamicPlushProvider(model);
        }
        modulePrepare();
    }

    @Override
    public void loadData() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<DynamicAdapterEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<DynamicAdapterEntity>> emitter) throws Exception {
                try {
                    DynamicBannerListEntity dynamicBannerListEntit = model.listBanner(4);
                    DynamicPlushEntity dynamicNoticeListEntity = null;
                    dynamicNoticeListEntity = dynamicPlushProvider.getDynamicPlush();
                    
                    DynamicTopShopListEntity dynamicTopShopListEntity = new DynamicTopShopListEntity();
                    DynamicTopShopListEntity dynamicTopShopListEntity2 = model.listAppService();
                    List<DynamicTopShopEntity> bochatServer = new ArrayList<>();
                    bochatServer.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_fast_exchange, "超速闪兑", "官方兑换"));
                    bochatServer.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_exchange, "闪兑大厅", "兑换资产"));
                    bochatServer.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_quotation, "AUSEX", "交易平台"));
                    bochatServer.addAll(dynamicTopShopListEntity2.getData());
                    dynamicTopShopListEntity2.setData(bochatServer);
                    dynamicTopShopListEntity2.setTypes(1);

                    List<DynamicTopShopEntity> list = new ArrayList<>();
                    list.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_activity, "活动中心", "新人有礼"));
                    list.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_spot, "今日看点", "热门资讯"));
                    list.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_market, "应用市场", "精选APP"));
                    list.add(new DynamicTopShopEntity(R.mipmap.dynamic_icon_apply, "项目申请", "项目方认证"));
                    dynamicTopShopListEntity.setTypes(1);
                    dynamicTopShopListEntity.setData(list);

                    DynamicAdapterEntity entity = new DynamicAdapterEntity();
                    DynamicAdapterEntity entity2 = new DynamicAdapterEntity();  //红包大厅入口
                    DynamicAdapterEntity entity3 = new DynamicAdapterEntity();
                    DynamicAdapterEntity entity4 = new DynamicAdapterEntity();

                    entity2.setType(3);
                    entity2.setViewLineVisible(false);

                    entity.setDynamicBannerListEntity(dynamicBannerListEntit);
                    entity.setDynamicPlushEntity(dynamicNoticeListEntity);
                    entity.setType(0);

                    entity3.setDynamicTopShopListEntity(dynamicTopShopListEntity);
                    entity3.setType(2);
                    entity3.setTitle("BoChat服务");
                    entity3.setViewLineVisible(false);

                    entity4.setDynamicTopShopListEntity(dynamicTopShopListEntity2);
                    entity4.setType(2);
                    entity4.setTitle("第三方服务");

                    List<DynamicAdapterEntity> dataList = new ArrayList<>();
                    dataList.add(entity);
                    dataList.add(entity2);
                    dataList.add(entity3);
                    dataList.add(entity4);

                    if (dynamicBannerListEntit == null) {
                        emitter.onError(new Throwable("获取Banner失败"));
                    }

                    if (dynamicNoticeListEntity == null) {
                        emitter.onError(new Throwable("获取公告失败"));
                    }
                    emitter.onNext(dataList);
                } catch (Exception e) {
                    emitter.onError(new Throwable("请求失败"));
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<DynamicAdapterEntity>>() {
            @Override
            public void accept(List<DynamicAdapterEntity> dynamicAdapterEntity) throws Exception {
                if (isActive()) {
                    getView().refreshData(dynamicAdapterEntity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable throwable) {
            }
        });
    }

    @Override
    public void modulePrepare() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<PrepareTask>() {
            @Override
            public void subscribe(ObservableEmitter<PrepareTask> emitter) throws Exception {
                try {
                    List<PrepareTask> prepareTasks = new ArrayList<>();
                    prepareTasks.add(new GameGoPrepareTask());
                    prepareTasks.add(new ProjectPartyPrepareTask());
                    prepareTasks.add(new BitMallPrepareTask());
                    for (PrepareTask prepareTask : prepareTasks) {
                        CodeEntity result = prepareTask.run();
                        prepareTask.setResult(result);
                        emitter.onNext(prepareTask);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PrepareTask>() {
            @Override
            public void accept(PrepareTask task) throws Exception {
                if (isActive()) {
                    if (task.getResult() != null && task.getResult().getRetcode() == 0) {
                        task.onComplete(task.getResult());
                    } else {
                        task.onError();
                    }
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }

    /**
     * Author      : FJ
     * CreateDate  : 2019/6/25 0025 下午 3:02
     * Description : 第三方服务初始化接口, 在modulePrepare中注册
     */
    abstract class PrepareTask<T extends CodeEntity> {
        private T result;

        abstract T run();

        abstract void onComplete(T t);

        abstract void onError();

        void setResult(T result) {
            this.result = result;
        }

        T getResult() {
            return result;
        }
    }

    private class GameGoPrepareTask extends PrepareTask<GameGoEntity> {

        @Override
        public GameGoEntity run() {
            return thirdServicesModel.gameLogin();
        }

        @Override
        public void onComplete(GameGoEntity gameGoEntity) {
            getView().gameGoLogin(gameGoEntity);
        }

        @Override
        public void onError() {
            getView().gameGoLogin(null);
        }
    }

    private class ProjectPartyPrepareTask extends PrepareTask<ProjectIdentificationEntity> {

        @Override
        public ProjectIdentificationEntity run() {
            return model.getProjectParty();
        }

        @Override
        public void onComplete(ProjectIdentificationEntity projectIdentificationEntity) {
            getView().getProjectStatuSuccess(projectIdentificationEntity);
        }

        @Override
        public void onError() {
            getView().getProjectStatuSuccess(null);
        }
    }

    private class BitMallPrepareTask extends PrepareTask<BitMallEntity> {

        @Override
        BitMallEntity run() {
            return thirdServicesModel.bitMallLogin();
        }

        @Override
        void onComplete(BitMallEntity bitMallEntity) {
            getView().bitMallLogin(bitMallEntity);
        }

        @Override
        void onError() {
            getView().bitMallLogin(null);
        }
    }
}
