package com.bochat.app.model.bean;


import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 *
 * 2019-4-10
 * Author LDL
 * acount也为username   即账号
 *
 **/

@Entity(
        nameInDb = "table_user"
)
public class UserEntity  extends CodeEntity implements Serializable {

    @Id
    @JSONField(name = "id")
    private long id;

    @Unique
    private String account;

    private String nickname;
    private int isInit;
    private String headImg;
    private String signature;
    private int sex;
    private int age;
    private int bochatId;
    private String province;
    private String city;
    private String token;
    private String other_id;
    private String inviteCode;
    private String countries;
    private String birthday;
    private String area;
    private String relation_state;
    private String black_state;
    
    private String authStatus;
    private String isSetTrade;
    private String isPwd;


    @Generated(hash = 263932545)
    public UserEntity(long id, String account, String nickname, int isInit,
            String headImg, String signature, int sex, int age, int bochatId,
            String province, String city, String token, String other_id,
            String inviteCode, String countries, String birthday, String area,
            String relation_state, String black_state, String authStatus,
            String isSetTrade, String isPwd) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.isInit = isInit;
        this.headImg = headImg;
        this.signature = signature;
        this.sex = sex;
        this.age = age;
        this.bochatId = bochatId;
        this.province = province;
        this.city = city;
        this.token = token;
        this.other_id = other_id;
        this.inviteCode = inviteCode;
        this.countries = countries;
        this.birthday = birthday;
        this.area = area;
        this.relation_state = relation_state;
        this.black_state = black_state;
        this.authStatus = authStatus;
        this.isSetTrade = isSetTrade;
        this.isPwd = isPwd;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    
    
    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getIsSetTrade() {
        return isSetTrade;
    }

    public void setIsSetTrade(String isSetTrade) {
        this.isSetTrade = isSetTrade;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBirthday() {
        if(birthday==null){
            birthday="";
        }
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountries() {
        if(countries==null){
            countries="";
        }
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        if(token==null){
            token="";
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOther_id() {
        if(other_id==null){
            other_id="";
        }
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }

    public String getInviteCode() {
        if(inviteCode==null){
            inviteCode="";
        }
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }


    public String getAccount() {
        if(account==null){
            account="";
        }
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        if(nickname==null){
            nickname="";
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIsInit() {
        return isInit;
    }

    public void setIsInit(int isInit) {
        this.isInit = isInit;
    }

    public String getHeadImg() {
        if(headImg==null){
            headImg="";
        }
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSignature() {
        if(signature==null){
            signature="";
        }
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBochatId() {
        return bochatId;
    }

    public void setBochatId(int bochatId) {
        this.bochatId = bochatId;
    }

    public String getProvince() {
        if(province==null){
            province="";
        }
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        if(city==null){
            city="";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRelation_state() {
        if(relation_state==null){
            relation_state="";
        }
        return relation_state;
    }

    public void setRelation_state(String relation_state) {
        this.relation_state = relation_state;
    }

    public String getBlack_state() {
        if(black_state==null){
            black_state="";
        }
        return black_state;
    }

    public void setBlack_state(String black_state) {
        this.black_state = black_state;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isInit=" + isInit +
                ", headImg='" + headImg + '\'' +
                ", signature='" + signature + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", bochatId=" + bochatId +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", token='" + token + '\'' +
                ", other_id='" + other_id + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", countries='" + countries + '\'' +
                ", birthday='" + birthday + '\'' +
                ", area='" + area + '\'' +
                ", relation_state='" + relation_state + '\'' +
                ", black_state='" + black_state + '\'' +
                '}';
    }

    public String getIsPwd() {
        return this.isPwd;
    }

    public void setIsPwd(String isPwd) {
        this.isPwd = isPwd;
    }
}
