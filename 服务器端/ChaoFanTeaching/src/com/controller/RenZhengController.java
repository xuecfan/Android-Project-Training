package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.RenZhengService;

@RestController
public class RenZhengController {

	@Resource
	private RenZhengService renZhengService;
	
	@RequestMapping(value="/Renzheng",produces="text/html;charset=UTF-8")
	public void renzheng(@RequestParam("province") String province,@RequestParam("university") String university,
			@RequestParam("studentNo") String studentNo,@RequestParam("xuezhi") String xuezhi) {
		 this.renZhengService.renzheng(province, university, studentNo, xuezhi);
	}
}
