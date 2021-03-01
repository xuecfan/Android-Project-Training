package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.ListInfoService;

@RestController
public class ListInfoController {

	@Resource
	private ListInfoService listInfoService;
	
	@RequestMapping(value="/ListInfoServlet",produces="text/html;charset=UTF-8")
	public String listinfo(@RequestParam("op") String op,@RequestParam("key") String key) {
		return this.listInfoService.listinfo(op, key);
	}
	
}
