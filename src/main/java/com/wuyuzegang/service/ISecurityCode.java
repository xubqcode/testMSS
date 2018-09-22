package com.wuyuzegang.service;

import java.util.List;
import com.wuyuzegang.proj.SecurityCodeProj;

public interface ISecurityCode {
	public List<SecurityCodeProj> selectAll();
	
	public void init();
}
