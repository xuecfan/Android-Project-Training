package com.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.MyPublishService;

@RestController
public class MyPublishController {

	@Resource
	private MyPublishService myPublishService;
	
	@RequestMapping(value="/MyPublish",produces="text/html;charset=UTF-8")
	public String name(@RequestParam("op") String op,@RequestParam("key") String key,
			@RequestParam(value="id",required =false) Integer id,@RequestParam(value="name",required =false) String name,
			@RequestParam(value="sex",required =false) String sex,@RequestParam(value="grade",required =false) String grade,
			@RequestParam(value="subject",required =false) String subject,@RequestParam(value="week",required =false) String week,
			@RequestParam(value="university",required =false) String university,@RequestParam(value="college",required =false) String college,
			@RequestParam(value="major",required =false) String major,@RequestParam(value="time",required =false) String time,
			@RequestParam(value="price",required =false) String price,@RequestParam(value="introduce",required =false) String introduce) {
		System.out.println(op+key+id+name+sex);
		return this.myPublishService.mypublish(op, key, id, name, sex, grade, subject, week, university, college, major, time, price, introduce);
	}
}
