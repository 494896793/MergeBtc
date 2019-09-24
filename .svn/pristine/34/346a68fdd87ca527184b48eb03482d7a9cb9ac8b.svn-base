package com.bochat.app.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 2019/4/26
 * Author LDL
 **/
@Entity(
        nameInDb = "table_group_member"
)
public class GroupMemberEntity extends CodeEntity implements Serializable {

    @Id(autoincrement = true)
    private Long none_id;
    private long group_id;
    private int member_id;
    private String join_time;
    private String nickname;
    private String head_img;
    private String signature;
    private int role;
    private int prohibit_state;





    @Generated(hash = 1731466580)
    public GroupMemberEntity(Long none_id, long group_id, int member_id,
            String join_time, String nickname, String head_img, String signature,
            int role, int prohibit_state) {
        this.none_id = none_id;
        this.group_id = group_id;
        this.member_id = member_id;
        this.join_time = join_time;
        this.nickname = nickname;
        this.head_img = head_img;
        this.signature = signature;
        this.role = role;
        this.prohibit_state = prohibit_state;
    }

    @Generated(hash = 1538201027)
    public GroupMemberEntity() {
    }





    public int getProhibit_state() {
        return prohibit_state;
    }

    public void setProhibit_state(int prohibit_state) {
        this.prohibit_state = prohibit_state;
    }
    
    public Long getNone_id() {
        return none_id;
    }

    public void setNone_id(Long none_id) {
        this.none_id = none_id;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }  
    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    
    
    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getJoin_time() {
        if(join_time==null){
            join_time="";
        }
        return join_time;
    }

    public void setJoin_time(String join_time) {
        this.join_time = join_time;
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

    public String getHead_img() {
        if(head_img==null){
            head_img="";
        }
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getSignature() {
        if(signature==null){
            signature="";
        }
        return signature;
    }


    @Override
    public String toString() {
        return "GroupMemberEntity{" +
                "none_id=" + none_id +
                ", group_id=" + group_id +
                ", member_id=" + member_id +
                ", join_time='" + join_time + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_img='" + head_img + '\'' +
                ", signature='" + signature + '\'' +
                ", role=" + role +
                ", prohibit_state=" + prohibit_state +
                '}';
    }
}
