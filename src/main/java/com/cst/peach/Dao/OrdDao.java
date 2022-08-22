package com.cst.peach.Dao;

import com.cst.peach.model.OrderModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdDao {
    public int getOrdSeq();

    int insertOrder(OrderModel order);

    OrderModel getOrderInfo(OrderModel order);
}
