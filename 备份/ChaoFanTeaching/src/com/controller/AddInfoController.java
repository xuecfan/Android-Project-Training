package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.AddInfoService;

@RestController
public class AddInfoController {

	@Resource
	private AddInfoService addInfoService;
	
	@RequestMapping(value="/AddInfoServlet",produces="text/html;charset=UTF-8")
	public void addinfo(@RequestParam("user") String user,@RequestParam("pay") String pay,
			@RequestParam("id") String id,@RequestParam("name") String name,
			@RequestParam("sex") String sex,@RequestParam("grade") String grade,
			@RequestParam("subject") String subject,@RequestParam("week") String week,
			@RequestParam(value="university",required =false) String university,@RequestParam(value="college",required =false) String college,
			@RequestParam(value="major",required =false) String major,@RequestParam(value="time",required =false) String time,
			@RequestParam(value="price",required =false) String price,@RequestParam(value="introduce",required =false) String introduce,
			@RequestParam("tel") String tel,@RequestParam("require") String require,
			@RequestParam(value="locate",required =false) String locate,@RequestParam(value="hour",required =false) String hour,
			@RequestParam(value="min",required =false) String min,@RequestParam(value="len",required =false) String len) {
		System.out.println(user+id+name+sex+grade+subject+week);
		this.addInfoService.addinfo(user, id, name, sex, grade, subject, week, pay, tel, require, locate, hour, min, len, university, college, major, time);
	}
}
