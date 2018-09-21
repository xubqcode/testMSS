package com.wuyuzegang.service.impl;

import java.util.List;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.dao.DayEndPriceProjMapper;
import com.wuyuzegang.dao.HalfPastFourteenPriceProjMapper;
import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.HalfPastFourteenPriceProj;
import com.wuyuzegang.service.IHalfPastFourteenPrice;

@Service("HalfPastFourteenPriceServices")
public class HalfPastFourteenPriceImpl implements IHalfPastFourteenPrice {
	@Resource
	private HalfPastFourteenPriceProjMapper halfPastFourteenPriceProjMapper;
	@Override
	public int insertBatch(List<HalfPastFourteenPriceProj> list) {
		System.out.println("进入批量插入");
		return this.halfPastFourteenPriceProjMapper.insertBatch(list);
	}
}
