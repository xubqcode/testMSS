package com.wuyuzegang.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wuyuzegang.proj.DayEndPriceProj;

public interface DayEndPriceProjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayEndPriceProj record);

    int insertSelective(DayEndPriceProj record);

    DayEndPriceProj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayEndPriceProj record);

    int updateByPrimaryKey(DayEndPriceProj record);
    
    int insertBatch(List<DayEndPriceProj> reddemCodeList);
    
    List <DayEndPriceProj>  selectByDaataTime(@Param("d1")String date1,@Param("d2")String date2);
    
    List <DayEndPriceProj>  selectFiveAvg(@Param("d1")String date1,@Param("d2")String date2);
    
    List <DayEndPriceProj>  selectTrightyAvgPrice(@Param("d1")String date1,@Param("d2")String date2);
    
}