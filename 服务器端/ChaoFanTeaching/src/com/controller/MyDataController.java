package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.LogService;
import com.service.MyDataService;

@RestController
public class MyDataController {

	@Resource
	private MyDataService myDataService;
	
	@RequestMapping(value="/MyData",produces="text/html;charset=UTF-8")
	public String mydata(@RequestParam(value="id",required=false) String id,@RequestParam("name") String user,@RequestParam(value="role",required=false) String role,
			@RequestParam(value="nameContent",required=false) String name,@RequestParam(value="sexContent",required=false) String sex,
			@RequestParam(value="phoneContent",required=false) String phone,@RequestParam(value="addressContent",required=false) String address,
			@RequestParam("index") String index,@RequestParam(value="email",required=false) String email,@RequestParam(value="locate",required=false) String locate) {
		System.out.println(index+user);
		return this.myDataService.mydata(id, user, name, sex, phone, address, index, email,locate,role);
	}
}
