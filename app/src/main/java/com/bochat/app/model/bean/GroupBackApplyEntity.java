package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/15
 * Author ZZW
 **/
public class GroupBackApplyEntity extends CodeEntity implements Serializable {

    private int gapply_id;
    private int gproposer_id;
    private int group_id;
    private String group_token;
    private String proposer_token;
    private String gapply_time;
    private String gapply_text;
    private String grefuse_time;
    private String grefuse_text;
    private String gagree_time;
    private int gapply_state;

    public int getGapply_id() {
        return gapply_id;
    }

    public void setGapply_id(int gapply_id) {
        this.gapply_id = gapply_id;
    }

    public int getGproposer_id() {
        return gproposer_id;
    }

    public void setGproposer_id(int gproposer_id) {
        this.gproposer_id = gproposer_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_token() {
        return group_token;
    }

    public void setGroup_token(String group_token) {
        this.group_token = group_token;
    }

    public String getProposer_token() {
        return proposer_token;
    }

    public void setProposer_token(String proposer_token) {
        this.proposer_token = proposer_token;
    }

    public String getGapply_time() {
        return gapply_time;
    }

    public void setGapply_time(String gapply_time) {
        this.gapply_time = gapply_time;
    }

    public String getGapply_text() {
        return gapply_text;
    }

    public void setGapply_text(String gapply_text) {
        this.gapply_text = gapply_text;
    }

    public String getGrefuse_time() {
        return grefuse_time;
    }

    public void setGrefuse_time(String grefuse_time) {
        this.grefuse_time = grefuse_time;
    }

    public String getGrefuse_text() {
        return grefuse_text;
    }

    public void setGrefuse_text(String grefuse_text) {
        this.grefuse_text = grefuse_text;
    }

    public String getGagree_time() {
        return gagree_time;
    }

    public void setGagree_time(String gagree_time) {
        this.gagree_time = gagree_time;
    }

    public int getGapply_state() {
        return gapply_state;
    }

    public void setGapply_state(int gapply_state) {
        this.gapply_state = gapply_state;
    }
}
