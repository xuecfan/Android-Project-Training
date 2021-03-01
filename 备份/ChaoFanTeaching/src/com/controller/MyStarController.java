package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.MyStarService;

@RestController
public class MyStarController {

	@Resource
	private MyStarService myStarService;
	
	@RequestMapping(value="/MyStar",produces="text/html;charset=UTF-8")
	public String mystar(@RequestParam("user") String user,@RequestParam("op") String op,
			@RequestParam(value="delname",required=false) String delname) {
		System.out.println(user+op+delname);
		return this.myStarService.mystar(user, op, delname);
	}
	
}
