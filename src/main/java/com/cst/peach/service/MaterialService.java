package com.cst.peach.service;

import com.cst.peach.Dao.MatDao;
import com.cst.peach.constants.CommonConstants;
import com.cst.peach.constants.MaterialConstatnts;
import com.cst.peach.model.DateModel;
import com.cst.peach.model.MaterialModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class MaterialService {

    private static final Logger log = LoggerFactory.getLogger(MaterialService.class);

    @Autowired
    MatDao matDao;

    /**
     * 원료 재고 저장,입력
     * @param request
     * @param materialModel
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean saveSupplyMaterial(HttpServletRequest request, MaterialModel materialModel) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_WITHOUT_TIME);
        String runDay = sdf.format(DateModel.getRunDts());
        String runStDtsStr = String.format("%s %s", runDay, CommonConstants.RUN_START_TIME_STR);
        String runEdDtsStr = String.format("%s %s", runDay, CommonConstants.RUN_END_TIME_STR);
        sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_BASIC);
        Date runStsDts = sdf.parse(runStDtsStr);
        Date runEdDts = sdf.parse(runEdDtsStr);
        // 영업시간인지 체크
        if (!DateModel.getRunDts().after(runStsDts) && !DateModel.getRunDts().before(runEdDts)) {
            return false;
        }
        // 원료 재고 공급
        matDao.saveSupplyMaterial(materialModel);
        if (StringUtils.isNotBlank(String.valueOf(materialModel.getMatId()))) {
            // 재고 공급 원료가 생산 설비 원료에 포함되어 있지 않으면 추가
            if (matDao.getCountOpMaterialExists(materialModel) == 0) {
                // 공급중인 원료가 있으면 에러
                materialModel.setSupplyFlg(CommonConstants.BOOLEAN_STR_TRUE);
                int cntMatInSupply = matDao.getMatInSupplyCount(materialModel);
                if (cntMatInSupply == 0) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateModel.getRunDts()); // 시간 설정
                    cal.add(Calendar.MINUTE, 40); // 분 연산
                    Date modDate = new Date(cal.getTimeInMillis());
                    // 원료 충전 예상 시간이 영업시간 이후면 exception
                    if (modDate.after(runEdDts)) {
                        new Exception(MaterialConstatnts.MASSAGE_EXCEPTION_IMPSB_SUPPLY_ERR);
                    }
                    materialModel.setSupplyFlg(CommonConstants.BOOLEAN_STR_TRUE);
                    int stockQtyAfterSupply = materialModel.getMatQty() - MaterialConstatnts.MIN_MATERIAL_SUPPLY_QTY;
                    materialModel.setMatQty(MaterialConstatnts.MIN_MATERIAL_SUPPLY_QTY);
                    // 생산 설비 원료 추가
                    matDao.insertMaterialOp(materialModel);
                    // 생산설비 원료 공급 이력 갱신
                    materialModel.setSupplyStDts(DateModel.getRunDts());
                    materialModel.setSupplyQty(MaterialConstatnts.MIN_MATERIAL_SUPPLY_QTY);
                    matDao.insertHisSupplyMatOp(materialModel);
                    // 40분 경과
                    TimeUnit.SECONDS.sleep(CommonConstants.SLEEP_SEC_40);
                    // 생산 설비 원료 공급 플래그 갱신
                    materialModel.setSupplyEdDts(DateModel.getRunDts());
                    materialModel.setSupplyFlg(CommonConstants.BOOLEAN_STR_FALSE);
                    materialModel.setModDts(DateModel.getRunDts());
                    matDao.updateMatOpSupplyFlg(materialModel);
                    // 원료 공급 이력 갱신 (공급 종료 시간)
                    matDao.updateHisSupplyMatOp(materialModel);
                    // 공급 후 재고량 갱신
                    materialModel.setMatQty(stockQtyAfterSupply);
                    matDao.updateMatStockQty(materialModel);
                } else {
                    new Exception(MaterialConstatnts.MASSAGE_EXCEPTION_IMPSB_SUPPLY_ERR);
                }
            }
        } else {
            throw new Exception(CommonConstants.MASSAGE_EXCEPTION_UNEXPECTED_ERR);
        }

        return true;
    }

    public boolean checkMatExists(MaterialModel materialModel) {
        materialModel.setDelFlg(CommonConstants.BOOLEAN_STR_FALSE);
        materialModel.setUseFlg(CommonConstants.BOOLEAN_STR_TRUE);
        int cnt = matDao.getMatCountByMatNm(materialModel);
        boolean res = cnt > 0 ? true : false;
        return res;
    }

    /**
     * 단종 원료 체크
     * @param materialModel
     * @return
     */
    public boolean checkMatDiscon(MaterialModel materialModel) {
        materialModel.setUseFlg(CommonConstants.BOOLEAN_STR_FALSE);
        int cnt = matDao.getMatDisconCountByMatNm(materialModel);
        boolean res = cnt > 0 ? true : false;
        return res;

    }

    /**
     * 재고 현황 취득
     * @return
     */
    public List<MaterialModel> getMatStockSts() {
        List<MaterialModel> list = matDao.getMatStockSts(CommonConstants.BOOLEAN_STR_FALSE, CommonConstants.BOOLEAN_STR_TRUE);
        return list;
    }


    /**
     * 주문 내용 원료에 대해 생산 설비 잔량 체크
     * @param matNm
     * @param matQty
     * @return
     */
    public boolean checkMatOpQtyOrder(String matNm, String matQty) {
        int res = matDao.checkMatOpQtyOrder(matNm, matQty, CommonConstants.BOOLEAN_STR_FALSE, CommonConstants.BOOLEAN_STR_FALSE);
        return res > 0 ? true : false;
    }
}
