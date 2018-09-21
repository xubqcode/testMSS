package com.wuyuzegang.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wuyuzegang.proj.DayEndPriceProj;
import com.wuyuzegang.proj.SecurityCodeProj;
import com.wuyuzegang.sender.WangYiSender;
import com.wuyuzegang.service.IDayEndPrice;
import com.wuyuzegang.service.ISecurityCode;

@Controller
@RequestMapping("/batch")
public class BatchDo {
	private static Logger logger = Logger.getLogger(BatchDo.class);
	@Resource
	private IDayEndPrice dayEndPrice;
	@Resource
	private ISecurityCode scc;
	public static Map<String,String> AllSecurityCodeMap = Collections.synchronizedMap(new HashMap());
	public static Map<String,String> SHSecurityCodeMap	= Collections.synchronizedMap(new HashMap());
	public static Map<String,String> SZSecurityCodeMap	= Collections.synchronizedMap(new HashMap());
	public static Map<String,String> CYSecurityCodeMap	= Collections.synchronizedMap(new HashMap());
	public static String ALLCODE = "AllSecurityCodeMap";
	public static String SHCODE = "SHSecurityCodeMap";
	public static String SZLCODE = "SZSecurityCodeMap";
	public static String CYLCODE = "CYSecurityCodeMap";
	
	@RequestMapping("/getHistoryToEndDay/{start}/{end}")
	public void getThirtyAveragevalue(HttpServletRequest request,Model model,@PathVariable("start") String start,@PathVariable("end") String end) throws InterruptedException, ParseException {
		//获取所有的股票代码
		List<DayEndPriceProj> list = new ArrayList<DayEndPriceProj>();
		Map<String,Map<String,String>> codeMap = getAllSecurityCodeMap();
		Map<String,String> allcodeMap =codeMap.get(ALLCODE);
		List<String> codeList = new ArrayList<String>();
		for (String key :allcodeMap.keySet()) {
			if("60".equals(key.substring(0, 2))||"00".equals(key.substring(0, 2))||"30".equals(key.substring(0, 2))) {
				if("60".equals(key.substring(0, 2))) {
					codeList.add("0"+key);
				}else {
					codeList.add("1"+key);
				}
			}
		}
		logger.info("获取全股票代码"+codeList.size()+"条");
		for(int i=0;i<codeList.size();i++) {
			List <DayEndPriceProj> list1 = new ArrayList<DayEndPriceProj>();
			WangYiSender wySender = new WangYiSender();
			list1 = wySender.send(codeList.get(i), start, end);
			if(list1.size()>=1) {
				logger.info("准备落地"+list1.size()+"条数据");
				int v = dayEndPrice.insertBatch(list1);
				logger.info("落地条数为："+v);
				logger.info("落地完毕");
			}
		}

		
	}
	
//-----------------------------------分割线以上是逻辑代码--------------------------------------------------

	
	public Map<String,Map<String,String>> getAllSecurityCodeMap() {
		int v = initSecurityCode();
		Map map = new HashMap();
		map.put(ALLCODE, AllSecurityCodeMap);
		map.put(SHCODE, SHSecurityCodeMap);
		map.put(SZLCODE, SZSecurityCodeMap);
		map.put(CYLCODE, CYSecurityCodeMap);
		return map;
	}
	
	public int initSecurityCode() {
		int j =0;
		logger.info("开始加载股票代码");
		List<SecurityCodeProj> list = scc.selectAll();
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			AllSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
		}
		logger.info("==================开始过滤沪指股票代码==================");
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			if("60".equals(a.getSecurityCode().substring(0, 2))) {
				SHSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
			
		}
		logger.info("==================开始过滤深指股票代码==================");
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			if("00".equals(a.getSecurityCode().substring(0, 2))) {
				SZSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
		}
		logger.info("==================开始过滤创业板股票代码==================");
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			if("30".equals(a.getSecurityCode().substring(0, 2))) {
				CYSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
		}
		logger.info("股票代码加载完成");
		return j;
}
	
}
