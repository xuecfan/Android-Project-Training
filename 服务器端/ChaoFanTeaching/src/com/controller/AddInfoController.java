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
	public String addinfo(@RequestParam("user") String user,@RequestParam(value="pay",required =false) String pay,
			@RequestParam(value="id",required =false) String id,@RequestParam(value="name",required =false) String name,
			@RequestParam(value="sex",required =false) String sex,@RequestParam(value="grade",required =false) String grade,
			@RequestParam(value="subject",required =false) String subject,@RequestParam(value="week",required =false) String week,
			@RequestParam(value="university",required =false) String university,@RequestParam(value="college",required =false) String college,
			@RequestParam(value="major",required =false) String major,@RequestParam(value="time",required =false) String time,
			@RequestParam(value="price",required =false) String price,@RequestParam(value="introduce",required =false) String introduce,
			@RequestParam(value="tel",required =false) String tel,@RequestParam(value="require",required =false) String require,
			@RequestParam(value="locate",required =false) String locate,@RequestParam(value="hour",required =false) String hour,
			@RequestParam(value="len",required =false) String len,@RequestParam(value="exp",required =false) String experience) {
		System.out.println(user+id+name+sex+grade+subject+week);
		return this.addInfoService.addinfo(user, id, name, sex, grade, subject, week, pay, tel, require, locate, hour, len, university, college, major, time,experience);
	}
}
