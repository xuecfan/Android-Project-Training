package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MyStarDao;

@Service
@Transactional(readOnly=false)
public class MyStarService {

		@Resource
		private MyStarDao myStarDao;
		
		public String mystar(String user,String op,String delname) {
			return this.myStarDao.mystar(user, op, delname);
			
		}
}
