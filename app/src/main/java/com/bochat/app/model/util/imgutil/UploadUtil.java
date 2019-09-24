package com.bochat.app.model.util.imgutil;

import android.util.Log;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.HttpResponseBean;
import com.bochat.app.model.net.JsonConvert;
import com.bochat.app.model.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.bochat.app.model.util.Api.BASE_URL;


/**
 * Created by LDL on 2017/10/12 0012.
 */

public class UploadUtil {

    //创建OkHttpClient对象
    static OkHttpClient client = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();

    //创建Retrofit
    static Retrofit retrofit = new Retrofit.Builder()
            .client(client) //设置OKHttpClient
            .baseUrl(BASE_URL)  //设置baseUrl, baseUrl必须后缀"/"
            .addConverterFactory(ScalarsConverterFactory.create())  //添加Gson转换器
            .build();
    static UploadImageService uploadImageService = HttpClient.getInstance().retrofit().create(UploadImageService.class);

    /**
     * 上传图片
     *
     * @param filePath            图片本地路径
     * @param type                图片类别(身份证正面、身份证反面、营业执照)
     * @param uploadImageListener
     */
    public static void uploadImage(String filePath, String type, final UploadImageListener uploadImageListener) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        LogUtil.LogDebug("uploadImage  fileName=" + fileName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<String> call = uploadImageService.uploadImage(CachePool.getInstance().user().getLatest().getToken(), type, requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.LogDebug("uploadImage response=" + response.body());
                HttpResponseBean httpResponseBean = JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
                if (httpResponseBean.getCode() == 200) {
//                    uploadImageListener.uploadImageSuc(null);
                } else {
                    uploadImageListener.uploadImageFail(httpResponseBean.getMsg());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.LogError("uploadImage onFailure=" + t.getMessage());
                uploadImageListener.uploadImageFail(t.getMessage());
            }
        });
    }

    /**
     * 上传图片
     *
     * @param utoken
     * @param filePaths
     * @param uploadImagesListener
     */
    public static void uploadImage(String utoken, List<String> filePaths, final UploadImagesListener uploadImagesListener) {
        HashMap<String, RequestBody> images = new HashMap<>();
        if (null != filePaths && filePaths.size() > 0) {
            for (int i = 0; i < filePaths.size(); i++) {
                File file = new File(filePaths.get(i));
                if (file.exists()) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    images.put("file" + (i + 1) + "\"; filename=\"" + file.getName(), requestBody);
                }
            }
        }
        Call call = uploadImageService.uploadImage(utoken, images);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.LogDebug("report response=" + response.body());
                HttpResponseBean httpResponseBean = JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
                if (httpResponseBean.getCode() == 200) {
                    uploadImagesListener.uploadImageSuc((Map<String, String>) httpResponseBean.getData());
                } else {
                    uploadImagesListener.uploadImageFail(httpResponseBean.getMsg());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.LogError("uploadImage onFailure=" + t.getMessage());
                uploadImagesListener.uploadImageFail(t.getMessage());
            }
        });
    }

    public static void uploadSingleFile(String utoken, String filePath, final UploadSingleFileListener uploadSingleFileListener) {
        File fileImage = new File(filePath);
        if (!fileImage.exists()) {
            return;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
// 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestBodyOcc = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName, requestBodyOcc);
        Call call = uploadImageService.uploadSingleFile(utoken, body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.LogDebug("uploadSingleFile response=" + response.body());
                HttpResponseBean httpResponseBean = JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
                if (httpResponseBean.getCode() == 200) {
                    String json = JsonConvert.GsonString(httpResponseBean.getData());
                    UploadFileBean uploadFileBean = JsonConvert.analysisJson(json, UploadFileBean.class);
                    LogUtil.LogDebug("uploadSingleFile uploadFileBean=" + uploadFileBean.toString());
                    uploadSingleFileListener.uploadSIngFileSuc(uploadFileBean);
                } else {
                    uploadSingleFileListener.onFail(httpResponseBean.getMsg());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.LogError("uploadImage onFailure=" + t.getMessage());
                uploadSingleFileListener.onFail(t.getMessage());
            }
        });
    }

    public static void uploadSingleFiles( String content,String phone, List<String> filePath, final UploadSingleFileListener uploadSingleFileListener) {
        Map<String, RequestBody> images = new HashMap<>();
        // 创建 RequestBody，用于封装构建RequestBody
        for (int i = 0; i < filePath.size(); i++) {
            File fileImage = new File(filePath.get(i));
            if (!fileImage.exists()) {
                return;
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
            images.put("file" + (i + 1) + "\"; filename=\"" + fileImage.getName(), requestBody);
        }

        for (int i = 0; i < filePath.size(); i++) {
            File fileImage = new File(filePath.get(i));
            if (!fileImage.exists()) {
                return;
            }
        }

        Call call = uploadImageService.addFeedback( content,phone, images);
        try {
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    HttpResponseBean httpResponseBean = JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
                    List<String> urls=new ArrayList<>();
                    if (httpResponseBean.getCode() == 200) {
//                        String json = JsonConvert.GsonString(httpResponseBean.getData());
//                        try{
//                            JSONArray jsonArray=new JSONArray(json);
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject=jsonArray.optJSONObject(i);
//                                urls.add(jsonObject.optString("url"));
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                        uploadSingleFileListener.uploadSuccessUrls(urls);
                    } else {
                        uploadSingleFileListener.onFail(httpResponseBean.getMsg());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    String a=t.getMessage();
                    uploadSingleFileListener.onFail(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void up(String content,String phone, List<String> filePath){
        List<MultipartBody.Part> parts = new ArrayList<>();
        List<File> files = new ArrayList<>();
        // 创建 RequestBody，用于封装构建RequestBody
        for (int i = 0; i < filePath.size(); i++) {
            File fileImage = new File(filePath.get(i));
            if (!fileImage.exists()) {
                return;
            }
            files.add(fileImage);
        }
        parts = filesToMultipartBodyParts(files);

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("content", content);
        builder.addFormDataPart("phone", phone);
        builder.addPart(parts.get(0));
        builder.addPart(parts.get(0));
//        builder.addPart(envPart);
        MultipartBody multipartBody = builder.build();
        Call call = uploadImageService.uploadSingleFiless(multipartBody);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("===","====");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("===","====");
            }
        });
    }

    public static void addProjectParty(String website,List<String> filePath){
        List<File> files = new ArrayList<>();
        // 创建 RequestBody，用于封装构建RequestBody
        for (int i = 0; i < filePath.size(); i++) {
            File fileImage = new File(filePath.get(i));
            if (!fileImage.exists()) {
                return;
            }
            files.add(fileImage);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(0));
        MultipartBody.Part license = MultipartBody.Part.createFormData("license", "aaaa.jpg", requestBody);

        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(1));
        MultipartBody.Part logo = MultipartBody.Part.createFormData("logo", "aaaa2.jpg", requestBody2);

        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), files.get(2));
        MultipartBody.Part agreement = MultipartBody.Part.createFormData("agreement", "aaaa3.doc", requestBody3);

//        Call call=uploadImageService.addProjectParty(license,website,logo,agreement);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.i("===","+==");
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Log.i("===","+==");
//            }
//        });
    }

    public static void uploadSingleFiless(String content,String phone, List<String> filePath, final UploadSingleFileListener uploadSingleFileListener) {
//        List<MultipartBody.Part> parts = new ArrayList<>();
//        List<File> files = new ArrayList<>();
//        // 创建 RequestBody，用于封装构建RequestBody
//        for (int i = 0; i < filePath.size(); i++) {
//            File fileImage = new File(filePath.get(i));
//            if (!fileImage.exists()) {
//                return;
//            }
//            files.add(fileImage);
//        }
//        parts = filesToMultipartBodyParts(files);
//        Call call = uploadImageService.uploadSingleFiless(content,phone, parts);
//        try {
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    HttpResponseBean httpResponseBean = JsonConvert.analysisJson(response.body(), HttpResponseBean.class);
//                    if (httpResponseBean.getCode() == 200) {
//                        String json = JsonConvert.GsonString(httpResponseBean.getData());
//                        UploadFileBean uploadFileBean = JsonConvert.analysisJson(json, UploadFileBean.class);
//                        uploadSingleFileListener.uploadSIngFileSuc(uploadFileBean);
//                    } else {
//                        uploadSingleFileListener.onFail(httpResponseBean.getMsg());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    String msg=t.getMessage();
//                    uploadSingleFileListener.onFail(t.getMessage());
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        int i = 0;
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
            i++;
        }
        return parts;
    }



    /**
     * 上传商家资料
     * @param utoken
     * @param type * type=0 上传营业执照     * type=1 上传身份证     * type=2 上传打款证明
     * @param filePath
     * @param uploadSingleFileListener
     */
    public static void uploadMerchantCertificate(String utoken,String type,String filePath,final UploadSingleFileListener uploadSingleFileListener){
        File fileImage = new File(filePath);
        if (!fileImage.exists()){
            return;
        }
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
// 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestBodyOcc =RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
        MultipartBody.Part body =MultipartBody.Part.createFormData("file", fileName, requestBodyOcc);
        Call call = uploadImageService.uploadMerchantCertificate(utoken,type,body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.LogDebug("uploadSingleFile response="+response.body());
                HttpResponseBean httpResponseBean= JsonConvert.analysisJson(response.body(),HttpResponseBean.class);
                if (httpResponseBean.getCode()==200){
                    String json=JsonConvert.GsonString(httpResponseBean.getData());
                    UploadFileBean uploadFileBean=JsonConvert.analysisJson(json,UploadFileBean.class);
                    LogUtil.LogDebug("uploadSingleFile uploadFileBean="+uploadFileBean.toString());
                    uploadSingleFileListener.uploadSIngFileSuc(uploadFileBean);
                }else {
                    uploadSingleFileListener.onFail(httpResponseBean.getMsg());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.LogError("uploadImage onFailure="+t.getMessage());
                uploadSingleFileListener.onFail(t.getMessage());
            }
        });
    }

}
