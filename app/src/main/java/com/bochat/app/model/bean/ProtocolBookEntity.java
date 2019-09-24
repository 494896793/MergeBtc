package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/29
 * Author LDL
 **/
public class ProtocolBookEntity extends CodeEntity implements Serializable {

    private long id;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "ProtocolBookEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
