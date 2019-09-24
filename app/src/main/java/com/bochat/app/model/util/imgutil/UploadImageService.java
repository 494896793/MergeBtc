package com.bochat.app.model.util.imgutil;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by LDL on 2017/10/12 0012.
 */

public interface UploadImageService {
    /**@Part("fileName") String description,
     * 单文件上传
     * @return 状态信息String
     */
    @Multipart
    @POST("app/jinjian/upload")
    Call<String> uploadImage(@Query("utoken") String utoken, @Query("type") String type,
                             @Part("file\"; filename=\"avator.png\"") RequestBody img);

    @Multipart
    @POST("app/wsjinjian/upload")
    Call<String> uploadCertificate(@Query("utoken") String utoken, @Query("type") String type, @Part MultipartBody.Part file);

    @Multipart
    @POST("app/Article/opinion")
    Call<String> submitSuggestion(@Query("utoken") String utoken, @Query("des") String des, @Query("tel") String tel,
                                  @PartMap() Map<String, RequestBody> images);

    @POST("app/Article/opinion")
    Call<String> submitSuggestion(@Query("utoken") String utoken, @Query("des") String des, @Query("tel") String tel);


    @Multipart
    @POST("app/User/change_headimg")
    Call<String> modifyAvatorImage(@Query("utoken") String utoken, @Part("file\"; filename=\"clientAvator.png\"") RequestBody img);

    @GET
    Call<ResponseBody> downloadPic(@Url String fileUrl);


    @Multipart
    @POST("app/userrole/set_role_base")
    Call<String> submitExpertData(@Query("utoken") String utoken, @Query("real_name") String real_name, @Query("company") String company, @Query("position") String position, @Query("introduction") String introduction,
                                  @Part MultipartBody.Part file);


    @Multipart
    @POST("app/api/getImgUrl")
    Call<String> uploadFiles(@Query("utoken") String utoken, @PartMap() Map<String, RequestBody> images);

    @Multipart
    @POST("app/api/getImgUrl")
    Call<String> uploadImage(@Query("utoken") String utoken, @PartMap() Map<String, RequestBody> images);


    //单张
    @Multipart
    @POST("/inseeapp/PicController/getPic")
    Call<String> uploadSingleFile(@Query("utoken") String utoken, @Part MultipartBody.Part file);


    //多张
    @Multipart
    @POST("inseeapp/PicController/uploadPicture")
    Call<String> uploadSingleFiles(@Query("utoken") String utoken, @Query("topic_id") int topic_id, @Query("topic_name") String topic_name, @Query("channel_id") int channel_id, @Query("hall_id") int hall_id, @Query("user_desc") String user_desc, @PartMap Map<String, RequestBody> images);


    @POST("app/my/addFeedback")
    Call<String> uploadSingleFiless(@Body MultipartBody multipartBody);


    @Multipart
    @POST("/inseeapp/MerchantController/approveUpload")
    Call<String> uploadMerchantCertificate(@Query("utoken") String utoken, @Query("type") String type, @Part MultipartBody.Part file);

    //单张
    @Multipart
    @POST("app/my/addFeedback")
    Call<String> addFeedback(@Query("content") String content,@Query("phone") String phone, @PartMap Map<String, RequestBody> images);

    @Multipart
    @POST("app/my/addProjectParty")
    Call<String> addProjectParty(@Part MultipartBody.Part license,@Query("website") String website,@Part  MultipartBody.Part logo,@Part  MultipartBody.Part agreement);
}
