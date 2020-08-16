package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MyPublishDao;

@Service
@Transactional(readOnly=false)
public class MyPublishService {

	@Resource
	private MyPublishDao myPublishDao;
	
	public String mypublish(String op,String key,Integer id,String name,String sex,String grade,String subject,
			String week,String university,String college,String major,String time,String price,String introduce,String role,String user,String experience,String length,String tel,String requirement,String locate) {
		return this.myPublishDao.mypublish(op, key, id, name, sex, grade, subject, week, university, college, major, time, price, introduce, role, user, experience, length, tel, requirement, locate);
	}
	
}
