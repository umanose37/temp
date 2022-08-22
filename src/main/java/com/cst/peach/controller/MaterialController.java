package com.cst.peach.controller;

import com.cst.peach.constants.CommonConstants;
import com.cst.peach.constants.MaterialConstatnts;
import com.cst.peach.model.*;
import com.cst.peach.service.MaterialService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 주문 컨트롤러
 *
 * @author jinwoolee
 */
@RestController
@RequestMapping("/material")
public class MaterialController {

    private static final Logger log = LoggerFactory.getLogger(MaterialController.class);
    private static String msg = "";

    @Autowired
    MaterialService materialService;

    /**
     * 효능 원료 재고 현황 조회
     * @param request
     * @return
     */
    @GetMapping("/getStockSts")
    @ResponseBody
    public String getMatStockSts(HttpServletRequest request) {
        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<MaterialModel> list = materialService.getMatStockSts();

        if (CollectionUtils.isEmpty(list)) {
            jo.put("message",MaterialConstatnts.MESSAGE_MAT_STOCK_NOT_EXISTS_ERR);
            return jo.toString();
        }

        for (MaterialModel model : list) {
            JSONObject jobj = new JSONObject();
            jobj.put("matNm", model.getMatNm());
            jobj.put("qty", model.getMatQty());
            jsonArray.put(jobj);
        }
        return jsonArray.toString();
    }

    /**
     * 원료 입력
     * @param request
     * @param materialModel
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public String addMaterial(HttpServletRequest request, @RequestBody MaterialModel materialModel) {

        JSONObject jo = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        boolean isValid = validateParam(materialModel);

        if (isValid) {

            // 재고 추가 ( 재고 추가는 40분 시간 가산 없음 )
            boolean result = false;
            try {
                materialModel.setDelFlg(CommonConstants.BOOLEAN_STR_FALSE);
                materialModel.setUseFlg(CommonConstants.BOOLEAN_STR_TRUE);
                materialModel.setRegId(CommonConstants.OP_USER_ID);
                materialModel.setRegUrl(request.getRequestURI());
                materialModel.setModId(CommonConstants.OP_USER_ID);
                materialModel.setModUrl(request.getRequestURI());
//                materialModel.setMatQty(materialModel.getMatQty());
                materialModel.setRegDts(DateModel.getRunDts());
                materialModel.setModDts(DateModel.getRunDts());
                result = materialService.saveSupplyMaterial(request, materialModel);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            if (result) {
                // 생산설비 원료 충전시간 가산
                Calendar cal = Calendar.getInstance();
                cal.setTime(DateModel.getRunDts()); // 시간 설정
                cal.add(Calendar.MINUTE, 40); // 분 연산
                Date modDate = new Date(cal.getTimeInMillis());
                DateModel.setRunDts(modDate);
                jo.put("result", CommonConstants.MESSAGE_SUCCEEDD);
            } else {
                jo.put("result", CommonConstants.MESSAGE_FAILED);
                jo.put("message", MaterialConstatnts.MASSAGE_NOT_WORKING_TIME_ERR);
            }
        } else {
            jo.put("result", CommonConstants.MESSAGE_FAILED);
            jo.put("message", msg);

        }
        return jo.toString();
    }

    private boolean validateParam(MaterialModel materialModel) {
        boolean res = true;

        // 필수 파라미터 누락 체크
        if (StringUtils.isBlank(materialModel.getMatNm()) || materialModel.getMatQty() == null) {
            res = false;
            msg = CommonConstants.MESSAGE_MISSING_REQ_PARAM;
        }

        // 효능 포맷 체크 (1자리 & 알파벳 대문자)
        if (res && (materialModel.getMatNm().length() != 1 || !materialModel.getMatNm().matches("^[A-Z]*$"))) {
            res = false;
            msg = MaterialConstatnts.MESSAGE_MAT_NM_INPUT_ERR;
        }

        // 단종된 효능인지 체크
        if (res) {
            boolean checkMatDiscon = materialService.checkMatDiscon(materialModel);
            if (checkMatDiscon) {
                res = false;
                msg = MaterialConstatnts.MESSAGE_MAT_DISCON_ERR;
            }
        }
        // 공급 용량 최대치 체크
        if (res && (materialModel.getMatQty() > MaterialConstatnts.MAX_MATERIAL_SUPPLY_QTY)) {
            res = false;
            msg = MaterialConstatnts.MESSAGE_MAT_SUPPLY_QTY_MAX_ERR;
        }
        // 공급 용량 최저치 체크
        if (res && (materialModel.getMatQty() < MaterialConstatnts.MIN_MATERIAL_SUPPLY_QTY)) {
            res = false;
            msg = MaterialConstatnts.MESSAGE_MAT_SUPPLY_QTY_MIN_ERR;
        }

        return res;


    }


}
