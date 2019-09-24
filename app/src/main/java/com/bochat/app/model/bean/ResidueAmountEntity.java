package com.bochat.app.model.bean;

public class ResidueAmountEntity extends CodeEntity {
    private String residue_amount;
    private String currency_id;

    public String getResidue_amount() {
        return residue_amount;
    }

    public void setResidue_amount(String residue_amount) {
        this.residue_amount = residue_amount;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    @Override
    public String toString() {
        return "ResidueAmountEntity{" +
                "residue_amount='" + residue_amount + '\'' +
                ", currency_id='" + currency_id + '\'' +
                '}';
    }
}
