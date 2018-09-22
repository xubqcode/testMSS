package com.wuyuzegang.service.impl;

import static org.hamcrest.CoreMatchers.containsString;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.wuyuzegang.dao.TimespamProjMapper;
import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.HalfPastFourteenPriceProj;
import com.wuyuzegang.proj.ThirtyAverageValueBeforeProj;
import com.wuyuzegang.proj.TimespamProj;
import com.wuyuzegang.service.IComputeMyData;
@Service("computeTodayServices")
public class ComputeTodayImpl implements IComputeMyData{
	private static Logger logger = Logger.getLogger(ComputeTodayImpl.class);
	@Resource
	private HalfPastFourteenPriceProjMapper hdao;
	@Resource
	private DayEndPriceProjMapper ddao;
	@Resource
	private ThirtyAverageValueBeforeProjMapper tdao ;
	@Resource
	private TimespamProjMapper timeSpam;
	/**
	 * 
	 * 入参为上一个开盘日期
	 */
	@Override
	public List<String> computeBuy(String yesterday) {
		/**
		 *1.获取前一日所有收盘数据
		 *2.获取前一日30平均值
		 *3.比较前一日所有收盘数据与获取前一日30平均值，且取前一日所有收盘数据小于  前一日30平均值 的数据   --股票代码List
		 *4.比较今日两点半数据与上述数据 取 今日两点半较大数据，返回股票代码List
		 * 
		 */
		BigDecimal multiplicand = new BigDecimal("2");
		List<String> returnCode = new ArrayList<String>();
		Map<String,BigDecimal>fiveAvg = new HashMap<String,BigDecimal>();
		Map <String,BigDecimal>beforeDayMap = new HashMap<String,BigDecimal>();
		Map <String,BigDecimal>yestayMap = new HashMap<String,BigDecimal>();
		Map <String,HalfPastFourteenPriceProj>okMap = new HashMap<String,HalfPastFourteenPriceProj>();
		
		beforeDayMap = xyBeforeDay(yesterday);
		yestayMap = dyYestoday(beforeDayMap,yesterday);
		okMap = dyToday(yestayMap);
		fiveAvg = getFiveAray();
		for (Map.Entry<String, HalfPastFourteenPriceProj> entry : okMap.entrySet()) {
			//bug selectAll的时候并没有查询num字段
			BigDecimal doubleFiveNum = fiveAvg.get(entry.getKey()).multiply(multiplicand);
			BigDecimal todayNum =  new BigDecimal(entry.getValue().getNum());
			if(doubleFiveNum.compareTo(todayNum)==1 && !"3".equals(entry.getKey().substring(0,1))) {
				logger.info("入围代码为:"+entry.getKey());
				returnCode.add(entry.getKey());
			}else {
//				logger.info("被pass的:"+entry.getKey()+"------今天的量："+todayNum+"-----------昨日的量:"+doubleFiveNum.divide(multiplicand));
			}
		}		
		return returnCode;
	}
	
//----------------------以上为services--------------------------------------------------------
	/**
	 * 前天小于前天30日线
	 * 返回值为
	 * 	key：	股票跑代码
	 * 	value：	前天收盘价格
	 */
	private Map<String,BigDecimal> xyBeforeDay(String yesterday) {
		Map <String,BigDecimal> returnMap = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> beforeDayEndMap = new HashMap<String,BigDecimal>();
		//获得前一个开盘日时间
		TimespamProj tp = timeSpam.selectYestoday(yesterday);
		Date beforeDay = tp.getTimeSpam();
//		Date beforeDayAddTen = addTenHours(beforeDay);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String StringBeforeDay = df.format(beforeDay);
		String date1 = StringBeforeDay+" 00:00:00";
		String date2 = StringBeforeDay+" 59:59:59";
		//获取前一个开盘日30日均量
		List<ThirtyAverageValueBeforeProj> beforeDayThrityList = tdao.selectBydata(date1, date2);
		//获取前一个开盘日收盘价格
		List<DayEndPriceProj> beforeDayEndList =  ddao.selectByDaataTime(date1,date2);
		for(int i =0;i<beforeDayEndList.size();i++) {
			beforeDayEndMap.put(beforeDayEndList.get(i).getSecurityCode(),new BigDecimal(beforeDayEndList.get(i).getNowPrice()));
		}
		for(int i =0;i<beforeDayThrityList.size();i++) {
			BigDecimal t = beforeDayThrityList.get(i).getThirtyAverageValue();
			BigDecimal e = beforeDayEndMap.get(beforeDayThrityList.get(i).getSecurityCode());
			if(null==e) {
				logger.info(beforeDayThrityList.get(i).getSecurityCode()+"再前天不存在===set1");
				continue;
			}
			if(t.compareTo(e)==1) {
				logger.info("前日30日均价："+t+"=========前日价格："+e);
				returnMap.put(beforeDayThrityList.get(i).getSecurityCode(), e);
			}
		}
		return returnMap;
	}
	/**
	 * 昨天大于昨天30日线
	 * 1.比较昨日收盘价与30均量，取大于30日均量的
	 * 2.比较昨日收盘价格大于前日收盘价格的
	 * 
	 * 返回值为
	 * 	key：	股票跑代码
	 * 	value：	昨日收盘价格
	 */
	private Map<String,BigDecimal> dyYestoday(Map<String,BigDecimal> beforeDayMap,String yesterday) {
		Map<String,BigDecimal> returnMap = new<String,BigDecimal> HashMap();
		Map<String,BigDecimal> yestodayEndMap = new HashMap<String,BigDecimal>();
		Map<String,BigDecimal> yestodayDYThrithyMap = new HashMap<String,BigDecimal>();
		String date1 = yesterday+" 00:00:00";
		String date2 = yesterday+" 59:59:59";
		//获取昨日开盘日30日均量
		List<ThirtyAverageValueBeforeProj> yestodayThrityList = tdao.selectBydata(date1, date2);
		//获取昨日开盘日收盘价格
		List<DayEndPriceProj> yestodayEndList =  ddao.selectByDaataTime(date1,date2);
		for(int i =0;i<yestodayEndList.size();i++) {
			yestodayEndMap.put(yestodayEndList.get(i).getSecurityCode(),new BigDecimal(yestodayEndList.get(i).getNowPrice()));
		}
		for(int i =0;i<yestodayThrityList.size();i++) {
			BigDecimal t = yestodayThrityList.get(i).getThirtyAverageValue();
			BigDecimal e = yestodayEndMap.get(yestodayThrityList.get(i).getSecurityCode());
			if(null==e) {
				logger.info(yestodayThrityList.get(i).getSecurityCode()+"不存在");
				continue;
			}
			if(e.compareTo(t)==1) {
				logger.info("昨日30日均价："+t+"=========昨日价格："+e);
				yestodayDYThrithyMap.put(yestodayThrityList.get(i).getSecurityCode(), e);
			}
		}
		for (Map.Entry<String, BigDecimal> entry : yestodayDYThrithyMap.entrySet()) { 
			String key = entry.getKey();
			BigDecimal beforePrice = beforeDayMap.get(key);
			if(null==beforePrice) {
				logger.info(key+"不符合规则===set2");
				continue;
			}
			BigDecimal yestodayPrice = yestodayDYThrithyMap.get(key);
			if(yestodayPrice.compareTo(beforePrice)==1) {
				logger.info("昨日价格："+yestodayPrice+"=========前日价格："+beforePrice);
				returnMap.put(key, entry.getValue());
			}
//			  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		}
		return returnMap;
	}
	/**
	 * 今天大于昨天收盘
	 */
	private Map<String,HalfPastFourteenPriceProj> dyToday(Map<String,BigDecimal> yestayMap) {
		Map<String,HalfPastFourteenPriceProj> returnMap = new <String,BigDecimal>HashMap();
		List<HalfPastFourteenPriceProj> hlist = hdao.selectAll();
		for(int i =0;i<hlist.size();i++) {
			BigDecimal yestodayPrice = yestayMap.get(hlist.get(i).getSecurityCode());
			if(null==yestodayPrice) {
				logger.info(hlist.get(i).getSecurityCode()+"不存在昨日规则价格===set3");
				continue;
			}
			BigDecimal todayPrice = new BigDecimal(hlist.get(i).getNowPrice());
			if(todayPrice.compareTo(yestodayPrice)==1) {
				logger.info("昨日价格："+yestodayPrice+"=========今日价格："+todayPrice);
				returnMap.put(hlist.get(i).getSecurityCode(), hlist.get(i));
			}
		}
		return returnMap;
	}
	
	/**
	 * 获取所有交易的5日均量
	 * 返回：
	 * 	key：股票代码
	 * 	value：从昨天开始向前推的5日平均量
	 */
	private Map<String,BigDecimal> getFiveAray() {
		Map <String,BigDecimal> returnMap = new HashMap<String,BigDecimal>();
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowDate = df.format(date);
		TimespamProj fiveBeforeTp =timeSpam.selectBeforeFive(nowDate);
		TimespamProj yestodayTp =timeSpam.selectYestoday(nowDate);
		String date1 = df2.format(fiveBeforeTp.getTimeSpam());
		String date2 = df2.format(yestodayTp.getTimeSpam());
		logger.info("nowDate:"+nowDate+"-----------date1"+date1+"---------date2"+date2);
		//date1小 date2大 查询前5日均量
		List <DayEndPriceProj> list = ddao.selectFiveAvg(date1, date2);
		for(int i =0;i<list.size();i++) {
			returnMap.put(list.get(i).getSecurityCode(), new BigDecimal(list.get(i).getFree1())); 
		}
		return returnMap;
	}
	
	
	//加12个小时
	private Date addTenHours(Date date) {
		Calendar Cal=Calendar.getInstance(); 
		Cal.setTime(date);
		Cal.add(Calendar.HOUR_OF_DAY,12); 
		return Cal.getTime();
	}


}
