package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupForbiddenItemEntity extends CodeEntity implements Serializable {
    /**
     * head_img : http://bochatoss.oss-cn-beijing.aliyuncs.com/headImg/1561102271788185.jpeg/058a3e7f-d9b2-427e-ab90-6c433fdc35c2.jpg
     * nickname : ggyy
     * id : 228802
     */

    private String head_img;
    private String nickname;
    private int id ;

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GroupForbiddenItemEntity{" +
                "head_img='" + head_img + '\'' +
                ", nickname='" + nickname + '\'' +
                ", id=" + id +
                '}';
    }
}
