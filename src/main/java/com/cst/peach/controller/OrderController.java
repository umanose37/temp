package com.cst.peach.controller;

import com.cst.peach.constants.CommonConstants;
import com.cst.peach.constants.MaterialConstatnts;
import com.cst.peach.constants.OrderConstants;
import com.cst.peach.model.*;
import com.cst.peach.service.MaterialService;
import com.cst.peach.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 주문 컨트롤러
 *
 * @author jinwoolee
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private static String msg = "";
    private static String ordDtlDiv = "";

    @Autowired
    OrderService orderService;

    @Autowired
    MaterialService materialService;


    /**
     * 주문정보 취득
     * @param request
     * @param order
     * @return
     * @throws ParseException
     */
    @GetMapping("/getMyOrder")
    @ResponseBody
    public String getOrderInfo(HttpServletRequest request, OrderModel order) throws ParseException {
        JSONObject jo = new JSONObject();

        boolean isValid = validateOrderInfoParam(order);
        if (!isValid) {
            jo.put("orderNumber", order.getOrdNumber());
            jo.put("orderDtl", order.getOrderDtl());
            jo.put("ordDts", order.getOrdDts());
            jo.put("error", msg);
        } else {
            order.setDelFlg(CommonConstants.BOOLEAN_STR_FALSE);
            OrderModel userOrderInfo = orderService.getOrderInfo(order);
            if (userOrderInfo == null) {
                jo.put("orderNumber", order.getOrdNumber());
                jo.put("orderDtl", order.getOrderDtl());
                jo.put("ordDts", order.getOrdDts());
                jo.put("error", CommonConstants.MESSAGE_INFO_NOT_EXISTS);
            } else {
                jo.put("orderNumber", order.getOrdNumber());
                jo.put("orderDtl", order.getOrderDtl());
                String sendDateStr = StringUtils.isEmpty(userOrderInfo.getSendDate()) ? CommonConstants.MESSAGE_IN_CONFIRM : userOrderInfo.getSendDate();
                jo.put("sendDate", sendDateStr);
                jo.put("ordDts", order.getOrdDts());
            }
        }

        return jo.toString();
    }

    /**
     * 주문정보 조회 파라미터 유효체크
     * @param order
     * @return
     * @throws ParseException
     */
    private boolean validateOrderInfoParam(OrderModel order) throws ParseException {
        boolean res = true;

        // 필수 파라미터 체크
        if (StringUtils.isEmpty(order.getOrderDtl()) || StringUtils.isEmpty(order.getOrdNumber()) || StringUtils.isEmpty(order.getOrderDate())) {
            res = false;
            msg = CommonConstants.MESSAGE_MISSING_REQ_PARAM;
        }
        // 주문내용 포맷 체크
        res = checkOrderDtlFormat(order);
        // 주문일
        if (res) {
            SimpleDateFormat transFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_YYYYMMDD);
            Date orderDate = transFormat.parse(order.getOrderDate());
            String orderDateStr = transFormat.format(orderDate);
            if (!checkDateValid(orderDateStr)) res = false;
        }

        return res;
    }

    /**
     * 주문일자 유효체크
     * @param orderDate
     * @return
     */
    private boolean checkDateValid(String orderDate) {
        boolean res = true;
        if (orderDate.length() == 8) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_YYYYMMDD);
                sdf.setLenient(false);
                sdf.parse(orderDate);
                System.out.println(sdf.parse(orderDate));

            } catch (ParseException e) {
                res = false;
                msg = CommonConstants.MESSAGE_NOT_VALID_DATE;
            }
        } else {
            res = false;
            msg = OrderConstants.MESSAGE_UNMATCHED_FORMAT_ORD_DATE;
        }

        return res;
    }


    /**
     * 주문 접수, 등록
     * @param request
     * @param order
     * @return
     * @throws Exception
     */
    @PostMapping("/order")
    @ResponseBody
    public String acceptOrder(HttpServletRequest request, @RequestBody OrderModel order) throws Exception {

        boolean isValid = validateOrderParam(order);
        String ordNoRtn = "";
        JSONObject jo = new JSONObject();

        if (isValid) {
            // insert order
            order.setOrdStatus(OrderConstants.ORD_STS_CD_RECIVED);
            order.setRegId(CommonConstants.OP_USER_ID);
            order.setModId(CommonConstants.OP_USER_ID);
            order.setRegUrl(request.getRequestURI());
            order.setModUrl(request.getRequestURI());

            try {
                String sendDtsStr = getSendDateOrder(order);
                order.setSendDate(sendDtsStr);
                ordNoRtn = orderService.insertOrder(order);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            if (StringUtils.isEmpty(ordNoRtn)) throw new Exception(CommonConstants.MASSAGE_EXCEPTION_UNEXPECTED_ERR);
            jo.put("result", CommonConstants.MESSAGE_SUCCEEDD);
            jo.put("order_number", ordNoRtn);
        } else {
            jo.put("result", CommonConstants.MESSAGE_FAILED);
            jo.put("message", msg);
        }

        return jo.toString();

    }

    /**
     * 배송일자 취득
     * @param order
     * @return
     * @throws ParseException
     */
    private String getSendDateOrder(OrderModel order) throws ParseException {
        String res = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateModel.getRunDts());
        String material1 = order.getOrderDtl().substring(0, 1);
        String materialRat1 = order.getOrderDtl().substring(1, 2);

        boolean result1 = materialService.checkMatOpQtyOrder(material1, materialRat1);

        // 원료 부족시 충전 시간 가산
        if (!result1) cal.add(Calendar.MINUTE, MaterialConstatnts.MINUTE_SUPPLY_MAT);
        if (OrderConstants.ORD_DTL_DIV_MULTI.equals(ordDtlDiv)) {
            String material2 = order.getOrderDtl().substring(2, 3);
            String materialRat2 = order.getOrderDtl().substring(3, 4);
            boolean result2 = materialService.checkMatOpQtyOrder(material2, materialRat2);
            // 원료 부족시 충전 시간 가산
            if (!result2) cal.add(Calendar.MINUTE, MaterialConstatnts.MINUTE_SUPPLY_MAT);
        }
        // 제품 1개 생산 시간 가산
        cal.add(Calendar.MINUTE, OrderConstants.MINUTE_PRODUCTION_ITEM);
        Date afterProdDts = new Date(cal.getTimeInMillis());

        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_WITHOUT_TIME);
        String runDay = sdf.format(DateModel.getRunDts());
        String runEdDtsStr = String.format("%s %s", runDay, CommonConstants.RUN_END_TIME_STR);
        sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_BASIC);
        Date runEdDts = sdf.parse(runEdDtsStr);
        sdf = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_YYYYMMDD);
        // 원료 충전, 제품 생산 시간을 가산한 시간이 금일 영업일을 넘기는 경우 배송일자는 다음날을 지정
        if (afterProdDts.after(runEdDts)) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(runEdDts);
            cal1.add(Calendar.DATE, 1);
            res = sdf.format(new Date(cal1.getTimeInMillis()));
        } else {
            // 제품 생산 완료 예정 시각이 금일 영업일을 안넘기면 금일을 배송일로 지정
            res = sdf.format(afterProdDts);
        }

        return res ;
    }

    /**
     * 주문 등록 파라미터 유효 체크
     * @param order
     * @return
     */
    private boolean validateOrderParam(OrderModel order) {

        boolean res = true;

        if (StringUtils.isEmpty(order.getOrderDtl())) {
            res = false;
            msg = CommonConstants.MESSAGE_MISSING_REQ_PARAM;
        }

        res = checkOrderDtlFormat(order);
        if (!res) msg = OrderConstants.MESSAGE_ORD_DTL_FORMAT_ERR;

        return res;
    }

    /**
     * 주문 내용 유효 체크
     * @param order
     * @return
     */
    private boolean checkOrderDtlFormat(OrderModel order) {
        boolean res = true;

        order.getOrderDtl().replace("\b","");
        // 포맷 확인
        // 자리수
        if (res && !(order.getOrderDtl().length() == OrderConstants.ORDER_DTL_LENGTH_ONE_MAT
                || order.getOrderDtl().length() == OrderConstants.ORDER_DTL_LENGTH_MULTI_MAT)) {
            res = false;

        }
        // 단일 효능일 경우
        if (res && (order.getOrderDtl().length() == OrderConstants.ORDER_DTL_LENGTH_ONE_MAT)) {
            ordDtlDiv = OrderConstants.ORD_DTL_DIV_SINGLE;
            String material = order.getOrderDtl().substring(0, 1);
            String materialRat = order.getOrderDtl().substring(1);
            if (!(material.matches(OrderConstants.REGULAR_EXPRESSION_ORDER_DTL_MAT_NM)
                    && materialRat.equals(MaterialConstatnts.MAX_VAL_MAT_RAT))) {
                res = false;
            }
        }
        if (!res) msg = OrderConstants.MESSAGE_ORD_DTL_FORMAT_ERR;
        // 복합 효능일 경우
        if (res && (order.getOrderDtl().length() == OrderConstants.ORDER_DTL_LENGTH_MULTI_MAT)) {
            ordDtlDiv = OrderConstants.ORD_DTL_DIV_MULTI;
            String material1 = order.getOrderDtl().substring(0, 1);
            String materialRat1 = order.getOrderDtl().substring(1, 2);
            String material2 = order.getOrderDtl().substring(2, 3);
            String materialRat2 = order.getOrderDtl().substring(3, 4);
            if (!(material1.matches(OrderConstants.REGULAR_EXPRESSION_ORDER_DTL_MAT_NM)
                    && material2.matches(OrderConstants.REGULAR_EXPRESSION_ORDER_DTL_MAT_NM))) {
                res = false;
            }
            if (res && (Integer.parseInt(materialRat1) + Integer.parseInt(materialRat2) != 10)) {
                res = false;
            }
        }
        return res;
    }


}
