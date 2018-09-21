package com.wuyuzegang.service;

import com.wuyuzegang.proj.TimespamProj;

public interface ITimespam {
	public TimespamProj selectBeforeThrity(String date1); 
	
	public TimespamProj selectYestoday(String date1);
}
