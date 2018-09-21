package com.wuyuzegang.init;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wuyuzegang.proj.SecurityCodeProj;
import com.wuyuzegang.service.ISecurityCode;

public class Init implements ServletContextListener {
//	@Resource
//	private  ISecurityCode securityCodeService = null;

//	public static Map AllSecurityCodeMap = Collections.synchronizedMap(new HashMap());
//	public static Map SHSecurityCodeMap	= Collections.synchronizedMap(new HashMap());
//	public static Map SZSecurityCodeMap	= Collections.synchronizedMap(new HashMap());
//	public static Map CYSecurityCodeMap	= Collections.synchronizedMap(new HashMap());
	
	
	
	public static void init() {
		System.out.println("开始清空缓存");
//		AllSecurityCodeMap.clear();
//		SHSecurityCodeMap.clear();
//		SZSecurityCodeMap.clear();
//		CYSecurityCodeMap.clear();
		System.out.println("开始加载缓存");
//		initSecurityCodeMap();
	}
//	public static void initSecurityCodeMap(){
//		System.out.println("开始加载股票代码");
//		List<SecurityCodeProj> list = securityCodeService.selectAll();
//		for(int i =0;i<list.size();i++) {
//			SecurityCodeProj a = list.get(i);
//			AllSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
//			System.out.println(a.getSecurityCode()+":"+a.getSecurityName());
//		}
//		for(int i =0;i<list.size();i++) {
//			System.out.println("开始过滤沪指股票代码");
//			SecurityCodeProj a = list.get(i);
//			if("60".equals(a.getSecurityCode().substring(0, 2))) {
//				SHSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
//			}
//			
//		}
//		for(int i =0;i<list.size();i++) {
//			System.out.println("开始过滤深指股票代码");
//			SecurityCodeProj a = list.get(i);
//			if("00".equals(a.getSecurityCode().substring(0, 2))) {
//				SZSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
//			}
//		}
//		for(int i =0;i<list.size();i++) {
//			System.out.println("开始过滤创业板股票代码");
//			SecurityCodeProj a = list.get(i);
//			if("30".equals(a.getSecurityCode().substring(0, 2))) {
//				CYSecurityCodeMap.put(a.getSecurityCode(), a.getSecurityName());
//			}
//		}
//		System.out.println("加载股票代码结束=========");
//	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		init();
		
	}

}
