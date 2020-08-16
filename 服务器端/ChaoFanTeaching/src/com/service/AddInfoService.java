package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.AddInfoDao;

@Service
@Transactional(readOnly=false)
public class AddInfoService {

	@Resource
	private AddInfoDao addInfoDao;
	
	public String addinfo(String user,String id,String name,String sex,String grade,String subject,String week,String pay,String tel,
			String require,String locate,String hour,String len,String university,
			String college,String major,String time,String experience) {
		return this.addInfoDao.addinfo(user, id, name, sex, grade, subject, week, pay, tel, require, locate, hour, len, university, college, major, time,experience);
	}
}
