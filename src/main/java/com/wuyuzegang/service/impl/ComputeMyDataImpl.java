package com.wuyuzegang.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.controller.GetSecurityNowPrice;
import com.wuyuzegang.dao.DayEndPriceProjMapper;
import com.wuyuzegang.dao.HalfPastFourteenPriceProjMapper;
import com.wuyuzegang.dao.ThirtyAverageValueBeforeProjMapper;
import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.HalfPastFourteenPriceProj;
import com.wuyuzegang.proj.ThirtyAverageValueBeforeProj;
import com.wuyuzegang.service.IComputeMyData;
@Service("computeMyDataServices")
public class ComputeMyDataImpl implements IComputeMyData{
	private static Logger logger = Logger.getLogger(ComputeMyDataImpl.class);
	@Resource
	private HalfPastFourteenPriceProjMapper hdao;
	@Resource
	private DayEndPriceProjMapper ddao;
	@Resource
	ThirtyAverageValueBeforeProjMapper tdao ;
	
	@Override
	public List<String> computeBuy(String yesterday) {
		/**
		 *1.获取前一日所有收盘数据
		 *2.获取前一日30平均值
		 *3.比较前一日所有收盘数据与获取前一日30平均值，且取前一日所有收盘数据小于  前一日30平均值 的数据   --股票代码List
		 *4.比较今日两点半数据与上述数据 取 今日两点半较大数据，返回股票代码List
		 * 
		 */
		List<String> lagyCode = new ArrayList<String>();
		List<String> returnCode = new ArrayList<String>();
		//获取前一日所有收盘数据对象
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");    
		String dayStart = yesterday+" 00:00:00";
		String dayEnd = yesterday+" 59:59:59";
		StringBuffer showBuffer = new StringBuffer();
		logger.info("=================="+dayStart+"================"+dayEnd);
		List<DayEndPriceProj> dlist =  ddao.selectByDaataTime(dayStart,dayEnd);
		logger.info("获取前一天所有收盘价,获取个数为"+dlist.size());
		//获取前一日30平均值对象
		List<ThirtyAverageValueBeforeProj> tlist =  tdao.selectBydata(dayStart,dayEnd);
		logger.info("获得前一天所有30日线，获取个数为"+tlist.size());
		//tlist转map key为股票代码 value为30均值
		Map <String,String> dmap = new HashMap<String,String>();
		Map <String,BigDecimal> tmap = new HashMap<String,BigDecimal>();
		for(int i=0;i<tlist.size();i++) {
			tmap.put(tlist.get(i).getSecurityCode(), tlist.get(i).getThirtyAverageValue());
		}
		//比较前一日所有收盘数据与获取前一日30平均值，且取前一日所有收盘数据小于  前一日30平均值 的数据   --股票代码List
		for(int i=0;i<dlist.size();i++) {
			String code = dlist.get(i).getSecurityCode();
			String endPrice = dlist.get(i).getNowPrice();
			dmap.put(code, endPrice);
			BigDecimal tPrice = tmap.get(code);
			BigDecimal a = new BigDecimal(endPrice);
			if(null==tmap.get(code)||null==tPrice) {
				logger.info("==============="+tmap.get(code)+":"+tmap.get(code));
				logger.info(code+":左右数据不对称");
			}else {
				if(a.compareTo(tPrice)==1) {
					lagyCode.add(code);
					logger.info(yesterday+"========30均值大于："+tPrice+"大于收盘:"+a+">>>>>>>>>>>>>>>>>>>>>");
				}else {
					logger.info(yesterday+"========30均值小于于："+tPrice+"大于收盘:"+a+"<<<<<<<<<<<<<<<<<<<<<");
				}
			}
		}
		
		//比较今日两点半数据与上述数据 取 今日两点半较大数据，返回股票代码List
		List<HalfPastFourteenPriceProj> hlist = hdao.selectAll();
		Map <String,String> hmap = new HashMap<String,String>();
		for(int i=0;i<hlist.size();i++) {
			hmap.put(hlist.get(i).getSecurityCode(), hlist.get(i).getNowPrice());
		}
		for(int i=0;i<lagyCode.size();i++) {
			String todayPrice = hmap.get(lagyCode.get(i));
			String yestdayPrice =dmap.get(lagyCode.get(i));
			BigDecimal t = new BigDecimal(todayPrice);
			BigDecimal y = new BigDecimal(yestdayPrice);
			
			if(t.compareTo(y)==1) {
				logger.info("符合规则:code"+lagyCode.get(i)+"今价:"+t+"昨价："+y);
				returnCode.add(lagyCode.get(i));
				showBuffer.append(lagyCode.get(i)).append(",");
			}
		}
		logger.info("股票代码为"+showBuffer.toString());
		return returnCode;
	}
	
}
