package com.wuyuzegang.dao;

import java.util.List;

import com.wuyuzegang.proj.ThirtyAverageValueProj;

public interface ThirtyAverageValueProjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThirtyAverageValueProj record);

    int insertSelective(ThirtyAverageValueProj record);

    ThirtyAverageValueProj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThirtyAverageValueProj record);

    int updateByPrimaryKey(ThirtyAverageValueProj record);
    
    int insertBatch(List<ThirtyAverageValueProj> reddemCodeList);
}