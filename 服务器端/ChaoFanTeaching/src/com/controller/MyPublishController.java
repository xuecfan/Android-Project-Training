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
	public String name(@RequestParam("op") String op,@RequestParam(value="key",required=false) String key,@RequestParam(value="duration",required =false) String length,
			@RequestParam(value="id",required =false) Integer id,@RequestParam(value="user",required =false) String user,@RequestParam(value="phone",required =false) String tel,
			@RequestParam(value="sex",required =false) String sex,@RequestParam(value="grade",required =false) String grade,@RequestParam(value="requirement",required =false) String requirement,
			@RequestParam(value="course",required =false) String subject,@RequestParam(value="freeWeek",required =false) String week,@RequestParam(value="location",required =false) String locate,
			@RequestParam(value="university",required =false) String university,@RequestParam(value="college",required =false) String college,
			@RequestParam(value="major",required =false) String major,@RequestParam(value="freeTime",required =false) String time,
			@RequestParam(value="fee",required =false) String price,@RequestParam(value="introduction",required =false) String introduce,
			@RequestParam(value="role",required =false) String role,@RequestParam(value="name",required =false) String name,@RequestParam(value="experience",required =false) String experience) {
		System.out.println(op+user+role);
		return this.myPublishService.mypublish(op, key, id, name, sex, grade, subject, week, university, college, major, time, price, introduce, role, user, experience, length, tel, requirement, locate);
	};
}
