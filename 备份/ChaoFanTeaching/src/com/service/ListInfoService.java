package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.ListInfoDao;

@Service
@Transactional(readOnly=false)
public class ListInfoService {

	@Resource
	private ListInfoDao listInfoDao;
	
	public String listinfo(String op,String key) {
		return this.listInfoDao.listinfo(op, key);
	}
}
