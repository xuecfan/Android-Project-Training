package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.RenZhengDao;

@Service
@Transactional(readOnly=false)
public class RenZhengService {

	@Resource
	private RenZhengDao renZhengDao;
	
	public void renzheng(String province,String university,
			String studentNo,String xuezhi) {
		this.renZhengDao.renzheng(province, university, studentNo, xuezhi);
	}
}
