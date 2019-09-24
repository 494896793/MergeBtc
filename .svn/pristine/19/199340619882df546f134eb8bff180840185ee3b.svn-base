package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 13:59
 * Description :
 */

public class RouterBillDetail extends AbstractRouter {
    public static final String PATH ="/path/RouterBillDetail";
    
    public static final int BILL_TYPE_MONEY = 1;
    public static final int BILL_TYPE_CANDY = 2;
    
    private int billType;
    private int billId;
    private String depictZh;
    
    public RouterBillDetail(int billType, int billId, String depictZh) {
        this.billType = billType;
        this.billId = billId;
        this.depictZh = depictZh;
    }
    
    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getDepictZh() {
        return depictZh;
    }

    public void setDepictZh(String depictZh) {
        this.depictZh = depictZh;
    }
    
    @Override
    public String getPath() {
        return PATH;
    }
}
