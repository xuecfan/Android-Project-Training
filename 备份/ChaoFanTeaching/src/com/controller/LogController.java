package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.LogService;


@RestController
public class LogController {

	@Resource
	private LogService logService;
	
	@RequestMapping(value="/LoginServlet",produces="text/html;charset=UTF-8")
	public String account(@RequestParam("myid") String myid,@RequestParam("mypw") String mypw) {
		System.out.println(myid+mypw);
		return this.logService.login(myid, mypw);
	}
	
	@RequestMapping(value="/AccountServlet",produces="text/html;charset=UTF-8")
	public String logon(@RequestParam("name") String name,@RequestParam("pasd") String pasd,
			@RequestParam("role") Integer role,@RequestParam("landingStatus") String landingStatus) {
		return this.logService.logon(name, pasd, role, landingStatus);
	}
	
	@RequestMapping(value="/LogoutServlet",produces="text/html;charset=UTF-8")
	public String logout(@RequestParam("userName") String userName) {
		return this.logService.logout(userName);
	}
	
}
