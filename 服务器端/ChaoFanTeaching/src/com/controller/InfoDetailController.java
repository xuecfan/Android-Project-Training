package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.InfoDetailService;

@RestController
public class InfoDetailController {

	@Resource
	private InfoDetailService infoDetailService;
	
	@RequestMapping(value="/InfoDetailServlet",produces="text/html;charset=UTF-8")
	public String infodetail(@RequestParam("id") String id,@RequestParam(value="key",required =false) String key,
			@RequestParam(value="collector",required =false) String collector,@RequestParam(value="collection",required =false) String collection,
			@RequestParam(value="collectionName",required =false) String collectionName) {
		
		return this.infoDetailService.infodetail(id, key, collector, collection, collectionName);
	}
}
