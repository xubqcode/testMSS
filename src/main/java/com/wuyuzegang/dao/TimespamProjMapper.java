package com.wuyuzegang.dao;

import org.apache.ibatis.annotations.Param;

import com.wuyuzegang.proj.TimespamProj;

public interface TimespamProjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimespamProj record);

    int insertSelective(TimespamProj record);

    TimespamProj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimespamProj record);

    int updateByPrimaryKey(TimespamProj record);
    
    TimespamProj selectYestoday(@Param("d1")String time);
    
    TimespamProj selectBeforeFive(@Param("d1")String time);
    
    TimespamProj selectBeforeThrity(@Param("d1")String time);
}