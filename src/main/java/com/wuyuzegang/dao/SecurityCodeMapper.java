package com.wuyuzegang.dao;

import java.util.List;

import com.wuyuzegang.proj.SecurityCodeProj;

public interface SecurityCodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SecurityCodeProj record);

    int insertSelective(SecurityCodeProj record);

    SecurityCodeProj selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SecurityCodeProj record);

    int updateByPrimaryKey(SecurityCodeProj record);
    
    List<SecurityCodeProj> selectAll();
}