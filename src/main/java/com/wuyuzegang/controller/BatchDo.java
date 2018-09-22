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

import com.wuyuzegang.init.Init;
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
	@RequestMapping("/getHistoryToEndDay/{start}/{end}")
	public void getThirtyAveragevalue(HttpServletRequest request,Model model,@PathVariable("start") String start,@PathVariable("end") String end) throws InterruptedException, ParseException {
		//获取所有的股票代码
		List<DayEndPriceProj> list = new ArrayList<DayEndPriceProj>();
		Map<String,String> allcodeMap =Init.AllSecurityCodeMap;
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
	
}
