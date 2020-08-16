package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CommentDao;

@Service
@Transactional(readOnly=false)
public class CommentService {
	@Resource
	private CommentDao commentDao;
	
	public String addcomment(String option,String user,String objuser,String content,String time,String quality,int messageId) {
		return this.commentDao.addcomment(option,user, objuser, content, time, quality,messageId);
	}
	
	public String lookusercomment(String user) {
		return this.commentDao.lookusercomment(user);
	}
	
	public String lookobjusercomment(String objuser) {
		return this.commentDao.lookobjusercomment(objuser);
	}
	
	public String avg(String objuser) {
		return this.commentDao.avg(objuser);
	}
}
