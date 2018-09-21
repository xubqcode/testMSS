package com.wuyuzegang.service;

import java.util.List;

import com.wuyuzegang.proj.ThirtyAverageValueBeforeProj;

public interface IThirtyAverageValueBefore {
	 public int insertBatch(List<ThirtyAverageValueBeforeProj> reddemCodeList);
	 
	 public int insert(ThirtyAverageValueBeforeProj prjo);
}
