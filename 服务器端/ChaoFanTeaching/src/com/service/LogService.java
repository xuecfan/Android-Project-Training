package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LogDao;

@Service
@Transactional(readOnly=false)
public class LogService {

	@Resource
	private LogDao logDao;
	
	public String login(String myid,String mypw) {
		return this.logDao.login(myid, mypw);
		
	}
	
	public String logon(String name,String pasd,Integer role,String landingStatus) {
		return this.logDao.logon(name, pasd, role, landingStatus);
		
	}
	
	public String logout(String userName) {
		return this.logDao.logout(userName);
		
	}
	
	
}
