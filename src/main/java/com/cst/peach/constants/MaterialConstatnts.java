package com.cst.peach.constants;

/**
 * 원료 상수 클래스
 *
 * @author jinwoolee
 */
public class MaterialConstatnts {

    public static final String MESSAGE_MAT_SUPPLY_QTY_MAX_ERR = "원료량 최대치 초과";
    public static final String MESSAGE_MAT_SUPPLY_QTY_MIN_ERR = "원료량 최저치 미달";
    public static final String MESSAGE_MAT_EXISTS_ERR = "이미 존재하는 원료";
    public static final String MESSAGE_MAT_DISCON_ERR = "단종된 효능";
    public static final String MESSAGE_MAT_NM_INPUT_ERR = "효능명 오류";
    public static final String MESSAGE_MAT_STOCK_NOT_EXISTS_ERR = "재고 정보가 존재하지 않음";
    public static final int MAX_MATERIAL_SUPPLY_QTY = 400;
    public static final int MIN_MATERIAL_SUPPLY_QTY = 200;
    public static final int MINUTE_SUPPLY_MAT = 40;
    public static final String MASSAGE_EXCEPTION_IMPSB_SUPPLY_ERR = "공급 불가(타 원료 공급 중)";
    public static final String MASSAGE_NOT_WORKING_TIME_ERR = "공급 불가 시간";
    public static final String MAX_VAL_MAT_RAT = "10";
}
