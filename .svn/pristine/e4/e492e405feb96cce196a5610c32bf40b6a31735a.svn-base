package com.bochat.app.model.bean;

import com.bochat.app.app.adapter.SelectCoinAdapter;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/06 16:32
 * Description :
 */

public class TokenEntity extends CodeEntity implements Serializable, SelectCoinAdapter.SelectCoin {
    private String image;
    private String bName;
    private int bId;
    private double residue_amount;
    
    
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public double getResidue_amount() {
        return residue_amount;
    }

    public void setResidue_amount(double residue_amount) {
        this.residue_amount = residue_amount;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "image='" + image + '\'' +
                ", bName='" + bName + '\'' +
                ", bId=" + bId +
                ", residue_amount=" + residue_amount +
                '}';
    }

    @Override
    public String getCoinName() {
        return getbName();
    }

    @Override
    public String getCoinImage() {
        return getImage();
    }
}
