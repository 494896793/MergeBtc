package com.bochat.app.model.bean;


import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 *
 * 2019-4-10
 * Author LDL
 * acount也为username   即账号
 *
 **/

@Entity(
        nameInDb = "table_friends"
)
public class FriendEntity extends CodeEntity implements Serializable {

    @Id
    @JSONField(name = "id")
    private long id;

    private String account;

    private String nickname;
    private int isInit;
    private String head_img;
    private String signature;
    private int sex;
    private int age;
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
    private String firstPinYin;
    private int type;
    private String note;
    private String address;
    private int friend_state;

    @Generated(hash = 1414777127)
    public FriendEntity(long id, String account, String nickname, int isInit,
            String head_img, String signature, int sex, int age, String province,
            String city, String token, String other_id, String inviteCode,
            String countries, String birthday, String area, String relation_state,
            String black_state, String firstPinYin, int type, String note,
            String address, int friend_state) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.isInit = isInit;
        this.head_img = head_img;
        this.signature = signature;
        this.sex = sex;
        this.age = age;
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
        this.firstPinYin = firstPinYin;
        this.type = type;
        this.note = note;
        this.address = address;
        this.friend_state = friend_state;
    }

    @Generated(hash = 834006476)
    public FriendEntity() {
    }

    public int getFriend_state() {
        return friend_state;
    }

    public void setFriend_state(int friend_state) {
        this.friend_state = friend_state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getFirstPinYin() {
        if(firstPinYin==null){
            firstPinYin="";
        }
        return firstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        this.firstPinYin = firstPinYin;
    }

    
    public String getArea() {
        if(area==null){
            area="";
        }
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
    
    public String getSexString() {
        return sex == 1 ? "男" : "女";
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
    
    public String getProvince() {
        if(province==null){
            province="";
        }
        return province;
    }

    public String getHead_img() {
        if(head_img==null){
            head_img="";
        }
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
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
        return "FriendEntity{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isInit=" + isInit +
                ", head_img='" + head_img + '\'' +
                ", signature='" + signature + '\'' +
                ", sex=" + sex +
                ", age=" + age +
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
                ", firstPinYin='" + firstPinYin + '\'' +
                ", type=" + type +
                ", note='" + note + '\'' +
                ", address='" + address + '\'' +
                ", friend_state=" + friend_state +
                '}';
    }
}
