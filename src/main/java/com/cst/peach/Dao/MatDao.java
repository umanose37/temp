package com.cst.peach.Dao;

import com.cst.peach.model.MaterialModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MatDao {
    void saveSupplyMaterial(MaterialModel model);
    public int getMaterialSeq();

    int getMatCountByMatNm(MaterialModel materialModel);

    int getMatDisconCountByMatNm(MaterialModel materialModel);

    List<MaterialModel> getMatStockSts(@Param("delFlg") String delFlg, @Param("useFlg") String useFlg);

    int getCountOpMaterialExists(MaterialModel materialModel);

    int insertMaterialOp(MaterialModel materialModel);

    void insertHisSupplyMatOp(MaterialModel materialModel);

    void updateHisSupplyMatOp(MaterialModel materialModel);

    int updateMatOpSupplyFlg(MaterialModel materialModel);

    int updateMatStockQty(MaterialModel materialModel);

    int getMatInSupplyCount(MaterialModel materialModel);

    int checkMatOpQtyOrder(@Param("matNm") String matNm, @Param("matQty") String matQty, @Param("supplyFlg") String supplyFlg, @Param("delFlg") String delFlg);
}
