package com.wuyuzegang.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wuyuzegang.controller.GetSecurityNowPrice;
 
@Component
public class HelloJob {
	private static Logger log = Logger.getLogger(GetSecurityNowPrice.class);
	public HelloJob() {
		log.info("定时调度创建成功");
	}
	@Scheduled(cron = "00 30 2 * * ? ")
	// 每隔1秒隔行一次
	public void run() {
		System.out.println("Hello MyJob  " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));
	}
}
