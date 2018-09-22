package com.wuyuzegang.init;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import com.wuyuzegang.proj.SecurityCodeProj;
import com.wuyuzegang.service.ISecurityCode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
//加载必须
@Component
public class Init{
	private static Logger logger = Logger.getLogger(Init.class);
	@Resource
	private  ISecurityCode securityCodeService = null;

	public static Map<String,String> AllSecurityCodeMap = Collections.synchronizedMap(new HashMap<String,String>());
	public static Map<String,String> SHSecurityCodeMap	= Collections.synchronizedMap(new HashMap<String,String>());
	public static Map<String,String> SZSecurityCodeMap	= Collections.synchronizedMap(new HashMap<String,String>());
	public static Map<String,String> CYSecurityCodeMap	= Collections.synchronizedMap(new HashMap<String,String>());
	
	//加载必须
	@PostConstruct
	public  void init() {
		logger.info("开始清空缓存");
		AllSecurityCodeMap.clear();
		SHSecurityCodeMap.clear();
		SZSecurityCodeMap.clear();
		CYSecurityCodeMap.clear();
		logger.info("开始加载缓存");
		initSecurityCodeMap();
	}
	
	
	
//----------------------------以上为缓存加载------------------------------------	
	public void initSecurityCodeMap(){
		int j =0;
		logger.info("开始加载股票代码");
		List<SecurityCodeProj> list = securityCodeService.selectAll();
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			AllSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
			j++;
		}
		logger.info("两市股票个数共"+j+"个");
		j=0;
		logger.info("开始过滤沪指股票代码");
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			if("60".equals(a.getSecurityCode().substring(0, 2))) {
				SHSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
			
		}
		logger.info("加载沪指股票代码"+j+"个");
		j=0;
		logger.info("开始过滤深指股票代码");
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			if("00".equals(a.getSecurityCode().substring(0, 2))) {
				SZSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
		}
		logger.info("加载深指股票代码"+j+"个");
		j=0;
		logger.info("开始过滤创业板股票代码");
		for(int i =0;i<list.size();i++) {
			SecurityCodeProj a = list.get(i);
			if("30".equals(a.getSecurityCode().substring(0, 2))) {
				CYSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
				j++;
			}
		}
		logger.info("加载创业板股票代码"+j+"个");
		logger.info("加载股票代码结束=========");
	}
	



}
