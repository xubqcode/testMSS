package com.wuyuzegang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.dao.ThirtyAverageValueBeforeProjMapper;
import com.wuyuzegang.dao.ThirtyAverageValueProjMapper;
import com.wuyuzegang.proj.ThirtyAverageValueBeforeProj;
import com.wuyuzegang.proj.ThirtyAverageValueProj;
import com.wuyuzegang.service.IThirtyAverageValue;
import com.wuyuzegang.service.IThirtyAverageValueBefore;

@Service("thirtyAverageValueBeforeServices")
public class ThirtyAverageValueBeforeImpl implements IThirtyAverageValueBefore {
	@Resource
	ThirtyAverageValueBeforeProjMapper dao ;
	@Override
	public int insertBatch(List<ThirtyAverageValueBeforeProj> reddemCodeList) {
		return dao.insertBatch(reddemCodeList);
	}
	@Override
	public int insert(ThirtyAverageValueBeforeProj prjo) {
		return  dao.insert(prjo);
	}


}
