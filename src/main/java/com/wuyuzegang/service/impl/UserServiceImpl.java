package com.wuyuzegang.service.impl;
 
import com.wuyuzegang.proj.User;
import com.wuyuzegang.service.IUserService;

import javax.annotation.Resource;
 
import org.springframework.stereotype.Service;
 
import com.wuyuzegang.dao.UserMapper;
 
@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Resource
	private UserMapper userMapper;
	
	public User getUserById(int userId) {
		return this.userMapper.selectByPrimaryKey(userId);
	}
}
