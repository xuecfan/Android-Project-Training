package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.InfoDetailDao;
import com.dao.LogDao;

@Service
@Transactional(readOnly=false)
public class InfoDetailService {

	@Resource
	private InfoDetailDao infoDetailDao;
	
	public String infodetail(String id,String key,String collector,String collection,String collectionName) {
		return this.infoDetailDao.infodetail(id, key, collector, collection, collectionName);
	}
}
