package com.wuyuzegang.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wuyuzegang.proj.ThirtyAverageValueBeforeProj;
import com.wuyuzegang.proj.ThirtyAverageValueProj;

public interface ThirtyAverageValueBeforeProjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThirtyAverageValueBeforeProj record);

    int insertSelective(ThirtyAverageValueBeforeProj record);

    ThirtyAverageValueBeforeProj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirtyAverageValueBeforeProj record);

    int updateByPrimaryKey(ThirtyAverageValueBeforeProj record);
    
    int insertBatch(List<ThirtyAverageValueBeforeProj> reddemCodeList);
    
    List<ThirtyAverageValueBeforeProj> selectBydata(@Param("d1")String date1,@Param("d2")String date2);
}