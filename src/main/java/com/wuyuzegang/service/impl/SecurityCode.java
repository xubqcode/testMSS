package com.wuyuzegang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wuyuzegang.dao.SecurityCodeMapper;
import com.wuyuzegang.proj.SecurityCodeProj;
import com.wuyuzegang.service.ISecurityCode;

/**
 * 
 * 不走控制器需要从services开始引用
 *
 */
@Service("securityCodeService")
public class SecurityCode implements ISecurityCode {
	@Resource
	private SecurityCodeMapper securityCodeMapper;
	
	public List<SecurityCodeProj> selectAll() {
		return this.securityCodeMapper.selectAll();
	}
}



