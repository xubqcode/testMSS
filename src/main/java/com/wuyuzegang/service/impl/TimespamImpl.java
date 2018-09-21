package com.wuyuzegang.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.dao.TimespamProjMapper;
import com.wuyuzegang.proj.TimespamProj;
import com.wuyuzegang.service.ITimespam;

@Service("timespamServices")
public class TimespamImpl implements ITimespam{
	@Resource
	TimespamProjMapper timespam;

	@Override
	public TimespamProj selectBeforeThrity(String date1) {
		return timespam.selectBeforeThrity(date1);
	}
	
	@Override
	public TimespamProj selectYestoday(String date1) {
		return timespam.selectYestoday(date1);
	}
}
