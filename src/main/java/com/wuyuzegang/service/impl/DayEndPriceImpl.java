package com.wuyuzegang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.dao.DayEndPriceProjMapper;
import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.service.IDayEndPrice;

@Service("dayEndPriceProjServices")
public class DayEndPriceImpl implements IDayEndPrice {
	@Resource
	private DayEndPriceProjMapper dayEndPriceProjMapper;
	@Override
	public int insertBatch(List<DayEndPriceProj> list) {
		System.out.println("进入批量插入");
		return this.dayEndPriceProjMapper.insertBatch(list);
	}
	@Override
	public DayEndPriceProj selectById(int id) {
		DayEndPriceProj a = dayEndPriceProjMapper.selectByPrimaryKey(id);
		return a;
	}
	@Override
	public List<DayEndPriceProj> selectFiveAvg(String bigDate, String smallDae) {
		return dayEndPriceProjMapper.selectFiveAvg(smallDae, bigDate);
	}
	@Override
	public List<DayEndPriceProj> selectTrightyAvgPrice(String bigDate, String smallDae) {
		return dayEndPriceProjMapper.selectTrightyAvgPrice(smallDae, bigDate);
	}
	
}
