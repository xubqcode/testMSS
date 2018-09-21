package com.wuyuzegang.service;

import java.util.List;
import com.wuyuzegang.proj.DayEndPriceProj;

public interface IDayEndPrice {
	public int insertBatch(List<DayEndPriceProj> list);
	
	public DayEndPriceProj selectById(int id); 
	
	public List<DayEndPriceProj> selectFiveAvg(String bigDate,String smallDae);
	
	public List<DayEndPriceProj> selectTrightyAvgPrice(String bigDate, String smallDae);
}
