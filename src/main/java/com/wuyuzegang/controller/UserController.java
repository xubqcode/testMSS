package com.wuyuzegang.controller;
 
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import com.wuyuzegang.proj.User;
import com.wuyuzegang.service.IUserService;
 
@Controller
@RequestMapping("/user")
 
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model)
	{
		System.out.println("进入控制器！");
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user",user);
		System.out.println("控制器结束！");
		return "showUser";
	}
}
