package com.cst.peach.constants;

/**
 * 주문 상수 클래스
 *
 * @author jinwoolee
 */
public class OrderConstants {

    public static final String ORD_STS_CD_RECIVED = "10";
    public static final String ORD_STS_NM_RECIVED = "주문접수";
    public static final String ORD_STS_CD_IN_PRODUCTION = "20";
    public static final String ORD_STS_NM_IN_PRODUCTION = "생산중";
    public static final String ORD_STS_CD_PRODUCTION_COMPLETE = "30";
    public static final String ORD_STS_NM_PRODUCTION_COMPLETE = "생산완료";
    public static final String ORD_STS_CD_SEND_READY = "40";
    public static final String ORD_STS_NM_SEND_READY = "발송준비중";
    public static final String ORD_STS_CD_SEND_COMPLETE = "50";
    public static final String ORD_STS_NM_SEND_COMPLETE = "발송완료";
    public static final String MESSAGE_UNMATCHED_FORMAT_ORD_DATE = "주문일자 포맷 오류";
    public static final String MESSAGE_ORD_DTL_FORMAT_ERR = "주문내용 포맷 오류";
    public static final String REGULAR_EXPRESSION_ORDER_DTL_MAT_NM = "^[A-D]*$";
    public static final int ORDER_DTL_LENGTH_ONE_MAT = 3;
    public static final int ORDER_DTL_LENGTH_MULTI_MAT = 4;
    public static final int MAX_VAL_ORDER = 10;
    public static final String FORMAT_ORDER_NUMBER_BASE = "P%s%d";
    public static final String ORD_DTL_DIV_SINGLE = "S";
    public static final String ORD_DTL_DIV_MULTI = "M";
    public static final int MINUTE_PRODUCTION_ITEM = 16;

}
