package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MessageDao;

@Service
@Transactional(readOnly=false)
public class MessageService {
	@Resource
	private MessageDao messageDao;
	
	public void addmessage(Integer id,String user,String objuser,String name,String grade,String subject,String date,
			String length,String locate,String time,String pay,String tel,String other) {
		this.messageDao.addmessage(id,user, objuser,name, grade, subject, date, length, locate, time, pay, tel, other);
	}
	
	public String lookmessage(Integer id) {
		return this.messageDao.lookmessage(id);
	}
	
	public String lookusermessage(String user) {
		return this.messageDao.lookusermessage(user);
	}
	
	public String lookobjusermessage(String objuser) {
		return this.messageDao.lookobjusermessage(objuser);
	}
	public void deletemessage(Integer id) {
		this.messageDao.deletemessage(id);
	}
	
	public void editmessage(Integer id) {
		this.messageDao.editmessage(id);
	}
	
	public void edit1message(Integer id) {
		this.messageDao.edit1message(id);
	}
	
}
