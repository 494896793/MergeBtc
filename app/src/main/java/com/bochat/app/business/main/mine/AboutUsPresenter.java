package com.bochat.app.business.main.mine;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.View;

import com.bochat.app.R;
import com.bochat.app.app.view.DownloadDialog;
import com.bochat.app.common.contract.mine.AboutUsContract;
import com.bochat.app.common.model.IUpgradeModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.modelImpl.UpgradeModel;
import com.bochat.app.model.util.APKUtils;
import com.bochat.app.model.util.download.DownloadApk;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 10:46
 * Description :
 */

public class AboutUsPresenter extends BasePresenter<AboutUsContract.View> implements AboutUsContract.Presenter {

    @Inject
    IUpgradeModel upgradeModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void upgradeApplication() {
        if (!BaseActivity.isUpdated) {
            getView().showLoading(null);
            APKUtils.checkUpdate(getView().getViewContext(), new UpgradeModel(), new APKUtils.OnCheckUpdateListener() {
                @Override
                public void onUpdate(final UpgradeEntity entity) {
                    getView().hideLoading(null);
                    DownloadApk.saveUpgradeEntity(getView().getViewContext(), entity);
                    DownloadDialog downloadDialog = new DownloadDialog.Builder(getView().getViewContext())
                            .setTitle(getView().getViewContext().getResources().getString(R.string.update_descriptions))
                            .setVersion("V" + entity.getVersion())
                            .setContent(Html.fromHtml(entity.getContent()))
                            .forceUpdate(entity.is_update == BaseActivity.IS_FORCE_UPDATE)
                            .setOnClickItemListener(new DownloadDialog.OnClickItemListener() {
                                @Override
                                public void onEnter(DownloadDialog dialog, View v) {
                                    BaseActivity.isChecked = false;
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(entity.getAddress()));
                                    if(intent.resolveActivity(getView().getViewContext().getPackageManager()) != null) {
                                        getView().getViewContext().startActivity(intent);
                                    } else {
                                        Router.navigation(new RouterDynamicWebView(entity.getAddress(),null, null,null));
                                    }
                                }

                                @Override
                                public void onCancel(DownloadDialog dialog, View v) {
                                    BaseActivity.isChecked = false;
                                    dialog.dismiss();
                                }
                            }).build();
                    downloadDialog.show();
                }

                @Override
                public void onThrow(Throwable throwable) {
                    ULog.d("onThrow:%@", throwable.getMessage());
                    getView().hideLoading(null);
                    getView().showTips("已是最新版本");
                }
            });
        }
    }
}
