package com.cst.peach.service;

import com.cst.peach.Dao.OrdDao;
import com.cst.peach.constants.CommonConstants;
import com.cst.peach.constants.OrderConstants;
import com.cst.peach.model.DateModel;
import com.cst.peach.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

/**
 * Order Service Class
 * @author jinwoolee
 */
@Service
public class OrderService {

    private static int materialBalanceA = 200; // 원료A 잔량 (초기 200으로 설정)
    private static int materialBalanceB = 200; // 원료B 잔량 (초기 200으로 설정)
    private static int materialBalanceC = 200; // 원료C 잔량 (초기 200으로 설정)
    private static int materialBalanceD = 200; // 원료D 잔량 (초기 200으로 설정)
    private static int accRunTime = 0; // 누적 가동 시간
    private static boolean judgeAbleProd = true; // 생산 가능 여부
    @Autowired
    OrdDao ordDao;

    /**
     * 주문 정보 생성
     * @param order
     * @return
     * @throws Exception
     */
    @Transactional
    public String insertOrder(OrderModel order) throws Exception {
        SimpleDateFormat transFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_STR_BASIC_EXCLUDE_HYPHEN);
        String runDtsStr = transFormat.format(DateModel.getRunDts());
        int ordSeq = ordDao.getOrdSeq();
        String ordNo = String.format(OrderConstants.FORMAT_ORDER_NUMBER_BASE, runDtsStr, ordSeq);
        order.setOrdNumber(ordNo);
        order.setRegDts(DateModel.getRunDts());
        order.setModDts(DateModel.getRunDts());
        order.setOrdDts(DateModel.getRunDts());
        order.setAmount("1");
        int res = ordDao.insertOrder(order);
        String ordNoRtn = res == 1 ? ordNo : "";
        return ordNoRtn;
    }

    /**
     * 주문 정보 조회
     * @param order
     * @return
     */
    public OrderModel getOrderInfo(OrderModel order) {
        return ordDao.getOrderInfo(order);
    }
}
