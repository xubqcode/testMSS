package com.wuyuzegang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.dao.ThirtyAverageValueProjMapper;
import com.wuyuzegang.proj.ThirtyAverageValueProj;
import com.wuyuzegang.service.IThirtyAverageValue;

@Service("thirtyAverageValueServices")
public class ThirtyAverageValueImpl implements IThirtyAverageValue {
	@Resource
	ThirtyAverageValueProjMapper dao ;
	
	@Override
	public int insertBatch(List<ThirtyAverageValueProj> reddemCodeList) {
		return dao.insertBatch(reddemCodeList);
	}

}
