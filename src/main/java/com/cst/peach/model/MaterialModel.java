package com.cst.peach.model;

import java.util.Date;

/**
 * 원료 모델 클래스
 *
 * @author jinwoolee
 */
public class MaterialModel {

    private String matNm;
    private Integer matQty;
    private long hisNo;
    private Integer supplyQty;
    private Date supplyStDts;
    private Date supplyEdDts;
    private String supplyFlg;
    private long matId;
    private String delFlg;
    private String useFlg;
    private String regId;
    private String regUrl;
    private Date regDts;
    private String modId;
    private String modUrl;
    private Date modDts;

    public String getMatNm() {
        return matNm;
    }

    public void setMatNm(String matNm) {
        this.matNm = matNm;
    }


    public long getHisNo() {
        return hisNo;
    }

    public void setHisNo(long hisNo) {
        this.hisNo = hisNo;
    }


    public Date getSupplyStDts() {
        return supplyStDts;
    }

    public void setSupplyStDts(Date supplyStDts) {
        this.supplyStDts = supplyStDts;
    }

    public Date getSupplyEdDts() {
        return supplyEdDts;
    }

    public void setSupplyEdDts(Date supplyEdDts) {
        this.supplyEdDts = supplyEdDts;
    }

    public String getSupplyFlg() {
        return supplyFlg;
    }

    public void setSupplyFlg(String supplyFlg) {
        this.supplyFlg = supplyFlg;
    }

    public long getMatId() {
        return matId;
    }

    public void setMatId(long matId) {
        this.matId = matId;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getUseFlg() {
        return useFlg;
    }

    public void setUseFlg(String useFlg) {
        this.useFlg = useFlg;
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

    public Integer getMatQty() {
        return matQty;
    }

    public void setMatQty(Integer matQty) {
        this.matQty = matQty;
    }

    public Integer getSupplyQty() {
        return supplyQty;
    }

    public void setSupplyQty(Integer supplyQty) {
        this.supplyQty = supplyQty;
    }
}
