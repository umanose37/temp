package com.cst.peach.model;

import java.util.Date;

/**
 * 주문 모델 클래스
 *
 * @author jinwoolee
 */
public class OrderModel {

    private String amount;
    private String ordNumber;
    private String orderDtl;
    private String ordStatus;
    private Date ordDts;
    private String orderDate;
    private String sendDate;
    private String delFlg;
    private String regId;
    private String regUrl;
    private Date regDts;
    private String modId;
    private String modUrl;
    private Date modDts;

    public String getOrdNumber() {
        return ordNumber;
    }

    public void setOrdNumber(String ordNumber) {
        this.ordNumber = ordNumber;
    }

    public String getOrderDtl() {
        return orderDtl;
    }

    public void setOrderDtl(String orderDtl) {
        this.orderDtl = orderDtl;
    }

    public String getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus = ordStatus;
    }

    public Date getOrdDts() {
        return ordDts;
    }

    public void setOrdDts(Date ordDts) {
        this.ordDts = ordDts;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegUrl() {
        return regUrl;
    }

    public void setRegUrl(String regUrl) {
        this.regUrl = regUrl;
    }

    public Date getRegDts() {
        return regDts;
    }

    public void setRegDts(Date regDts) {
        this.regDts = regDts;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getModUrl() {
        return modUrl;
    }

    public void setModUrl(String modUrl) {
        this.modUrl = modUrl;
    }

    public Date getModDts() {
        return modDts;
    }

    public void setModDts(Date modDts) {
        this.modDts = modDts;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
