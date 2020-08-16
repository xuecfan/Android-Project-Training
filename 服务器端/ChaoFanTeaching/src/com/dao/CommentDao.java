package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Comment;
import com.entity.Message;

@Repository
public class CommentDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String addcomment(String option,String user,String objuser,String content,String time,String quality,int messageId) {
		if(option.equals("commenting")) {
			try {
				Session session=sessionFactory.getCurrentSession();
				Comment comment=new Comment();
				comment.setUser(user);
				comment.setObjuser(objuser);
				comment.setContent(content);
				comment.setTime(time);
				comment.setQuality(quality);
				comment.setMessageId(messageId);
				session.save(comment);
				System.out.println(user+"用户对"+objuser+"用户的评价添加成功");
				//Session session2=sessionFactory.getCurrentSession();
				String hql="select count(*) from Comment where messageId=?";
				Session session2=sessionFactory.openSession();
				Query query = session2.createQuery(hql).setInteger(0,messageId);
				//Long a=(long) ((Long) query.iterate().next()).intValue();
				
			
				Long a= (long) ( (Long) query.uniqueResult()).intValue();
				System.out.println(a);
				if(a>=1) {
					
					//String hql1="select * from Message where id=?";
					//Session session3=sessionFactory.openSession();
					//Query query1 = session3.createQuery(hql1).setInteger(0,messageId);
					//Message message=(Message) query1.uniqueResult(); 
					Session session3=sessionFactory.getCurrentSession();
					Query query2=session3.createQuery("from Message where id=?");
					query2.setParameter(0,messageId);
					Message message=(Message) query2.uniqueResult();
					
					message.setStatus("已完成");
					session3.save(message);
				}
				return "1";
			} catch (Exception e) {
				System.out.println(user+"用户对"+objuser+"用户的评价添加失败");
				return "0";
			}
		}
		return "";
	}
	
	public String lookusercomment(String user) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Comment where user=?");
		query.setParameter(0, user);
		List<Comment> comments=(List<Comment>)query.list();
		String a="";
		for(Comment comment:comments) {
			a+=comment.getUser()+","+comment.getObjuser()+","+comment.getContent()+","+comment.getTime()+","+comment.getQuality()+";";
		}
		return a;
	}
	
	public String lookobjusercomment(String objuser) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Comment where objuser=?");
		query.setParameter(0, objuser);
		List<Comment> comments=(List<Comment>)query.list();
		String a="";
		for(Comment comment:comments) {
			a+=comment.getUser()+","+comment.getObjuser()+","+comment.getContent()+","+comment.getTime()+","+comment.getQuality()+";";
		}
		return a;
	}
	public String avg(String objuser) {
		Session session=sessionFactory.openSession();
		String hql="select avg(time) from Comment where objuser=?";
		Query query=session.createQuery(hql);
		query.setString(0, objuser);
		Double count=(Double) query.uniqueResult();
		//int a = (int) Math.round(count);
		String a =String.format("%.1f", count);
		String hql1="select avg(quality) from Comment where objuser=?";
		Query query1=session.createQuery(hql1);
		query1.setString(0, objuser);
		Double count1=(Double) query1.uniqueResult();
		//int b = (int) Math.round(count1);
		String b =String.format("%.1f", count1);
		
		return a+""+","+b;
	}
}
