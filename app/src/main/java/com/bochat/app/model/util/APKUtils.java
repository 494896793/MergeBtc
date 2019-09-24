package com.bochat.app.model.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.model.IUpgradeModel;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.util.download.DownloadApk;
import com.bochat.app.mvp.view.BaseActivity;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2019/5/9
 * Author LDL
 **/
public class APKUtils {

//    private String dirPath= Environment.getExternalStorageDirectory()+"/ceshi";
//    private String soonFileDir="/test";
//    private String fileName="/aa.apk";

    private static final boolean DEBUG = true;

    //安装
    public static void instant(String dirPath, String soonFileDir, String fileName, Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.fromFile(new File(dirPath + soonFileDir + fileName)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //删除
    public static void unInstant(String dirPath, String soonFileDir, String fileName, Context context) {
        Uri packageURI = Uri.parse("package:com.demo.CanavaCancel");

        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }

    public interface OnCheckUpdateListener {
        void onUpdate(UpgradeEntity entity);
        void onThrow(Throwable throwable);
    }

    public interface OnDownloadErrorListener {
        public void onError(DownloadApk.Builder builder, int error);
    }

    public static void checkUpdate(final Context context, final IUpgradeModel model, final OnCheckUpdateListener listener) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<UpgradeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<UpgradeEntity> emitter) throws Exception {
                try {

                    BaseActivity.isChecked = true;
                    String version = DownloadApk.getVersionName(context);
                    UpgradeEntity upgradeEntity = model.upgrade(version);;

//                    String version = DownloadApk.getVersionName(context);
//                    ULog.d("[ Target Version : %s ]", version);
//                    UpgradeEntity reuseEntity = DownloadApk.getUpgradeEntity(context);
//                    if (reuseEntity != null && compareVersion(reuseEntity.version, version) == 1) {
//                        upgradeEntity = reuseEntity;
//                        ULog.d("[ Entity load on SharedPreferences ] Entity:[ %@ ]", upgradeEntity);
//                    } else {
//                        upgradeEntity = model.upgrade(version);
//                        ULog.d("[ Entity load on Network ] Entity:[ %@ ]", upgradeEntity);
//                    }
                    if (upgradeEntity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(upgradeEntity));
                    } else {
                        emitter.onNext(upgradeEntity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UpgradeEntity>() {
            @Override
            public void accept(final UpgradeEntity entity) throws Exception {

                if (entity != null) {
                    if (listener != null)
                        listener.onUpdate(entity);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (listener != null)
                    listener.onThrow(throwable);
            }
        });
    }

    public static void upgrade(final Context context, final UpgradeEntity entity, final OnDownloadErrorListener listener) {

        if (entity != null && !BaseActivity.isUpdated) {
            BaseActivity.isUpdated = true;
            DownloadApk downloadApk = DownloadApk.builder()
                    .context(context)
                    .forceUpdate(entity.getIs_update() != 0)
                    .setName("BoChat_v" + entity.getVersion() + "_release")
                    .setTitle("下载新版本")
                    .setDescriptions(entity.getContent())
                    .setDownloadUrl(entity.getAddress())
                    .dynamicReceiver()
                    .setOnDownloadApkListener(new DownloadApk.Builder.OnDownloadApkListener() {
                        @Override
                        public void onDownloadPending(DownloadApk.Builder builder) {
                            builder.getDialog().showProgress(true);
                        }

                        @Override
                        public void onDownloadRunning(DownloadApk.Builder builder, float progress, int curSize, int totalSize) {
                        }

                        @Override
                        public void onDownloadFailed(DownloadApk.Builder builder, int reason) {
                            ULog.d("[ Download Apk Failed = %d ]", reason);
                            if (listener != null)
                                listener.onError(builder, reason);

                        }

                        @Override
                        public void onDownloadCompleted() {
                            BaseActivity.isUpdated = false;
                            DownloadApk.removeUpgradeEntity(context);
                            DownloadApk.install(context);
                        }
                    }).build();

            downloadApk.startDownload();
        }
    }

    /**
     * 比较字符串资源版本号算法。
     * <pre>当同一个位置的时候出现数字和字母进行比较的时候我们默认字母号高于数字号。</pre>
     * <p>
     * 时间复杂度o(n)^2
     *
     * @param opt1 string 1
     * @param opt2 string 2
     * @return 当两个字符串相等的时候返回为0 ，当字符串1大于字符串2的时候返回1。
     * 当字符串1小于字符串2的时候返回为-1
     */
    private static int compareVersion(@NotNull String opt1, @NotNull String opt2) {
        if (opt1.hashCode() == opt2.hashCode()) {
            return 0;
        }
        String[] array1 = opt1.split("\\.");
        String[] array2 = opt2.split("\\.");
        int length = array1.length > array2.length ? array2.length : array1.length;
        for (int len = 0; len < length; len++) {
            if (checkNum(array1[len]) && checkNum(array2[len])) {
                int result = Integer.parseInt(array1[len]) - Integer.parseInt(array2[len]);
                if (result == 0) continue;
                return result > 0 ? 1 : -1;
            } else {
                String[] temp1 = subSplit(array1[len]);
                String[] temp2 = subSplit(array2[len]);
                int subLen = temp1.length > temp2.length ? temp2.length : temp1.length;
                for (int i = 0; i < subLen; i++) {
                    boolean check1 = checkNum(temp1[i]);
                    boolean check2 = checkNum(temp2[i]);
                    if (check1 && check2) {
                        int result = Integer.parseInt(temp1[i]) - Integer.parseInt(temp2[i]);
                        if (result == 0) continue;
                        return result > 0 ? 1 : -1;

                    } else if (!check1 && !check2) {//纯单个字母比较
                        if (temp1[i].equals(temp2[i])) continue;
                        return temp1[i].compareTo(temp2[i]);
                    }
                    //数字和字符混合比较
                    if (check1) return -1;
                    if (check2) return 1;
                }
                if (temp1.length != temp2.length)
                    return temp1.length > temp2.length ? 1 : -1;
            }
        }
        if (array1.length != array2.length)
            return array1.length > array2.length ? 1 : -1;
        return 0;
    }

    private static boolean checkNum(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    private static String[] subSplit(String str) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                number.append("|");
                number.append(str.charAt(i));
                number.append("|");
            } else {
                number.append(str.charAt(i));
            }
        }
        return number.toString().split("\\|+");
    }

}
