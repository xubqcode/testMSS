package com.wuyuzegang.dao;

import java.util.List;

import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.HalfPastFourteenPriceProj;

public interface HalfPastFourteenPriceProjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HalfPastFourteenPriceProj record);

    int insertSelective(HalfPastFourteenPriceProj record);

    HalfPastFourteenPriceProj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HalfPastFourteenPriceProj record);

    int updateByPrimaryKey(HalfPastFourteenPriceProj record);
    
    int insertBatch(List<HalfPastFourteenPriceProj> reddemCodeList);
    
    List<HalfPastFourteenPriceProj> selectAll();
}