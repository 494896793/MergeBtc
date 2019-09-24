package com.bochat.app.model.modelImpl;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bochat.app.MainApplication;
import com.bochat.app.common.config.OSSConfig;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.ConditionObject;
import com.bochat.app.common.util.StringUtil;

import java.io.File;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 13:44
 * Description :
 */

public class OSSModel implements IOSSModel {

    private static final String TAG = "OSS";
    
    private OSSClient ossClient;
    
    @Override
    public String uploadHeadImage(File file) {
        
        final ConditionObject block = new ConditionObject();
        uploadHeadImage("", file.getPath(), new OnUploadResponseListener() {

            @Override
            public void onSuccess(String filePath, String url) {
                super.onSuccess(filePath, url);
                block.setObject(url);
            }
            
            @Override
            public void onFinish() {
                super.onFinish();
                block.open();
            }
        });
        block.block();
        return (String)block.getObject();
    }
    
    public void uploadHeadImage(String tag, String filePath, OnUploadResponseListener listener) {
        String objectKey = String.format(OSSConfig.FOLDER_NAME_HEAD_IMAGE, tag) + UUID.randomUUID().toString() + ".jpg";
        OSSUploadFile ossFile = new OSSUploadFile(filePath, objectKey);
        ALog.d(TAG, "uploadChatImage[OSSUploadFile=" + ossFile + "]");
        asyncPutObject(ossFile, listener);
    }
    
    /**
     * oss上传文件
     *
     * @param ossFile
     * @param listener
     */
    private void asyncPutObject(final OSSUploadFile ossFile, final OnUploadResponseListener listener) {
        
        if (ossClient == null) {
            ossClient = COSSClient.getInstance().getOSSClient();
        }

        if (ossFile == null) {
            ALog.e(TAG, "asyncPutObject() ossFile is null.");
            return;
        }

        String filePath = ossFile.getFilePath();
        if (StringUtil.isEmpty(filePath)) {
            ALog.e(TAG, "asyncPutObject() filePath is empty.");
            return;
        } else {
            // 如果文件路径带有file:// 则替换成空字符串
            if (filePath.contains("file://")) {
                filePath = filePath.replace("file://", "");
            }
        }

        final String bucketName = ossFile.getBucketName();
        if (StringUtil.isEmpty(bucketName)) {
            ALog.e(TAG, "asyncPutObject() bucketName is empty.");
            return;
        }

        final String objectKey = ossFile.getObjectKey();
        if (StringUtil.isEmpty(objectKey)) {
            ALog.e(TAG, "asyncPutObject() objectKey is empty.");
            return;
        }

        // 文件元信息的设置是可选的
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setContentType("application/octet-stream"); // 设置content-type
        // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5
        // put.setMetadata(metadata);
        final File uploadFile = new File(filePath);
        if (!uploadFile.exists()) {
            ALog.d(TAG, "asyncPutObject() 文件不存在.");
            return;
        }

        final String serverUrl = ossFile.getUploadUrl();
        if (listener != null) {
            listener.onStart(filePath, serverUrl);
        }

        final PutObjectRequest request = new PutObjectRequest(bucketName, objectKey,
                filePath/*, metadata*/);

        final String finalFilePath = filePath;
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                
                request.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {

                    int lastProgress = 0;
                    @Override
                    public void onProgress(PutObjectRequest request, final long currentSize, final long totalSize) {
                        ALog.d(TAG, "asyncPutObject() onProgress[currentSize=" + currentSize + "\ttotalSize=" + totalSize + "]");
                        int progress = (int) ((currentSize * 1.0f / totalSize) * 100);
                        if(progress >= lastProgress + 7) {
                            emitter.onNext(String.valueOf(progress));
                            lastProgress = progress;
                        }
                    }
                });

                ossClient.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        ALog.d(TAG, "asyncPutObject() onSuccess[tag=" + result.getETag() + "\trequestId=" + result.getRequestId() + "\tfileSize=" + uploadFile.length() + "\turl=" + serverUrl + "]");
                        emitter.onNext(serverUrl);
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                        String errorMsg = null;
                        // 请求异常
                        if (clientException != null) {
                            // 本地异常如网络异常等
                            errorMsg = clientException.getMessage();
                            clientException.printStackTrace();
                        } else if (serviceException != null) {
                            // 服务异常
                            errorMsg = serviceException.getMessage();
                            serviceException.printStackTrace();
                            ALog.e(TAG, "asyncPutObject() onFailure[errorCode=" + serviceException.getErrorCode() + "]");
                            ALog.e(TAG, "asyncPutObject() onFailure[requestId=" + serviceException.getRequestId() + "]");
                            ALog.e(TAG, "asyncPutObject() onFailure[hostId=" + serviceException.getHostId() + "]");
                            ALog.e(TAG, "asyncPutObject() onFailure[rawMessage=" + serviceException.getRawMessage() + "]");
                        }

                        Throwable t = null;
                        if (StringUtil.isNotEmpty(errorMsg)) {
                            t = new Throwable(errorMsg);
                        } else {
                            t = new Throwable("unknown exception.");
                        }

                        emitter.onError(t);
                        emitter.onComplete();
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        this.disposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        if (listener != null) {
                            try{
                                listener.onProgress(finalFilePath, Integer.valueOf(s));
                            } catch (Exception e){
                            }
                            listener.onSuccess(finalFilePath, s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        disposable = null;

                        if (listener != null) {
                            listener.onFailure(finalFilePath, e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onFinish();
                        }
                    }
                });
    }
    
    public static class COSSClient {

        private static COSSClient instance;
        private OSSClient ossClient;

        private COSSClient() {

        }

        public static COSSClient getInstance() {
            if(instance == null) {
                synchronized (COSSClient.class) {
                    if(instance == null) {
                        instance = new COSSClient();
                    }
                }
            }

            return instance;
        }

        public void init() {
            OSSCredentialProvider credentialProvider = new StsGetter();
            
            ClientConfiguration configuration = new ClientConfiguration();
            configuration.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            configuration.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            configuration.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
            configuration.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            ossClient = new OSSClient(MainApplication.getInstance(), 
                    "http://oss-cn-beijing.aliyuncs.com",
                    credentialProvider, 
                    configuration);
        }

        public OSSClient getOSSClient() {
            if(ossClient == null) {
                init();
            }
            return ossClient;
        }
    }

    public static class StsGetter extends OSSFederationCredentialProvider {

        @Override
        public OSSFederationToken getFederationToken() {
            String accessKeyId = "LTAI4XzGwYnX6yc7";
            String accessKeySecret = "yYU2SFx8gZ8dtXmBcCBC0TsrjZRCX7";
            String securityToken = "";
            return new OSSFederationToken(accessKeyId, accessKeySecret, securityToken, 24 * 60 * 60);
        }
    }
    
    public static class OSSUploadFile {

        private String bucketName;
        private String objectKey;
        private String filePath;
        private String endpoint;
        private String uploadUrl;

        public OSSUploadFile() {

        }

        public OSSUploadFile(String filePath, String objectKey) {
            this(filePath, objectKey, OSSConfig.BUCKET, OSSConfig.ENDPOINT);
        }

        public OSSUploadFile(String filePath, String objectKey, String bucketName, String endpoint) {
            this.filePath = filePath;
            this.objectKey = objectKey;
            this.bucketName = bucketName;
            this.endpoint = endpoint;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getObjectKey() {
            return objectKey;
        }

        public void setObjectKey(String objectKey) {
            this.objectKey = objectKey;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getUploadUrl() {
            this.uploadUrl = OSSConfig.PREFIX_HTTP + OSSConfig.BUCKET + "." + endpoint + "/" + objectKey;
            return uploadUrl;
        }

        @Override
        public String toString() {
            return "OSSUploadFile:{"
                    + "\"bucketName\":" + this.bucketName + ","
                    + "\"objectKey\":" + this.objectKey + ","
                    + "\"filePath\":" + this.filePath + ","
                    + "\"endpoint\":" + this.endpoint + "}";
        }
    }

    public abstract class OnUploadResponseListener {

        public void onStart(String filePath, String url) {

        }

        public void onProgress(String filePath, int progress) {

        }

        public void onSuccess(String filePath, String url) {

        }

        public void onFailure(String filePath, String errorMsg) {

        }

        public void onFinish() {

        }
    }
}
