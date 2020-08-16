package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MyDataDao;

@Service
@Transactional(readOnly=false)
public class MyDataService {
	@Resource
	private MyDataDao myDataDao;
	
	public String mydata(String id,String user,String name,String sex,String phone,String address,String index,String email,String locate,String role) {
		return this.myDataDao.mydata(id, user, name, sex, phone, address, index, email,locate,role);
		
	}
}
