package com.wuyuzegang.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wuyuzegang.service.impl.ComputeMyDataImpl;
import com.wuyuzegang.service.impl.ComputeTodayImpl;
import com.wuyuzegang.util.CompareSecurityCodeUtil;

@Controller
@RequestMapping("/getThirtyAveragevalue")
public class GetThirtyAveragevalue {
	private static Logger logger = Logger.getLogger(GetSecurityNowPrice.class);
	
	@Resource
	private ComputeMyDataImpl cservices ;
	@Resource
	private ComputeTodayImpl buyServices;
	
	@RequestMapping("/getBefore/{yestday}")
	public void  getThirtyAveragevalue(@PathVariable("yestday") String yestday,Model model) throws InterruptedException, ParseException {
		logger.info("进入符合两日传30日线");
		List<String> list = cservices.computeBuy(yestday);
		model.addAttribute("list",list);
		logger.info("今天，符合两日传30日线的，被选中的股票为：");	
//		return "showt";
	}
	
	@RequestMapping("/getBuyCode/{yestday}")
	public void  getBuyCode(@PathVariable("yestday") String yestday,Model model) throws InterruptedException, ParseException {
		logger.info("进入符合两日传30日线");
		List<String> list = buyServices.computeBuy(yestday);
		model.addAttribute("list",list);
		logger.info("今天，符合两日传30日线的，被选中的股票为：");	
//		return "showt";
	}
	/**
	 * 
	 * 比较了30日线
	 * @param yestday
	 * @param model
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@RequestMapping("/getBuyCode2/{yestday}")
	public void  getBuyCode2(@PathVariable("yestday") String yestday,Model model) throws InterruptedException, ParseException {
		logger.info("进入符合两日传30日线");
		CompareSecurityCodeUtil pUtil = new CompareSecurityCodeUtil();
		List<String> list = buyServices.computeBuy(yestday);
		List<String> returnList = pUtil.comparetoThrityPrice(yestday,list);
		model.addAttribute("list",returnList);
		logger.info("今天，符合两日传30日线的，被选中的股票为：");	
//		return "showt";
	}
}
