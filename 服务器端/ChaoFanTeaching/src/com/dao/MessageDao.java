package com.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Message;

@Repository
public class MessageDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public void addmessage(Integer id,String user,String objuser,String name,String grade,String subject,String date,
			String length,String locate,String time,String pay,String tel,String other) {
		String nowtime=new Date().toLocaleString();
		try {
			Session session=sessionFactory.getCurrentSession();
			Message message=new Message();
			message.setId(id);
			message.setUser(user);
			message.setObjuser(objuser);
			message.setName(name);
			message.setGrade(grade);
			message.setSubject(subject);
			message.setDate(date);
			message.setLength(length);
			message.setLocate(locate);
			message.setTime(time);
			message.setPay(pay);
			message.setTel(tel);
			message.setOther(other);
			message.setNowtime(nowtime);
			session.save(message);
			System.out.println(user+"用户的添加试讲信息成功");
			
		} catch (Exception e) {
			System.out.println(user+"用户的添加试讲信息失败");
		}
	}
	
	public String lookmessage(Integer id) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Message where id=?");
		query.setParameter(0, id);
		Message message=(Message) query.uniqueResult();
		return message.getId()+","+message.getUser()+","+message.getObjuser()+","+message.getName()+","+message.getGrade()+","+message.getSubject()+","+message.getDate()+","+message.getTime()+","+message.getLocate()+","+message.getLength()+","+message.getPay()+","+message.getTel()+","+message.getOther()+","+message.getStatus();
		
	}
	
	public String lookusermessage(String user) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Message where user=?");
		query.setParameter(0, user);
		
		List<Message> messages= (List<Message>) query.list();
		String a="";
		for(Message message:messages) {
			a+=message.getId()+","+message.getStatus()+","+message.getObjuser()+","+message.getPay()+","+message.getNowtime()+";";
		}
		return a;
		
	}
	
	public String lookobjusermessage(String objuser) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Message where objuser=?");
		query.setParameter(0, objuser);
		List<Message> messages= (List<Message>) query.list();
		String a="";
		for(Message message:messages) {
			a+=message.getId()+","+message.getStatus()+","+message.getUser()+","+message.getPay()+","+message.getNowtime()+";";
		}
		return a;
	}
	
	public void deletemessage(Integer id) {
		
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Message where id=?");
			query.setParameter(0, id);
			Message message=(Message) query.uniqueResult();
			session.delete(message);
			System.out.println("删除"+id+"订单成功");
		} catch (Exception e) {
			System.out.println("删除"+id+"订单失败");
		}
	}
	
	public void editmessage(Integer id) {
		
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Message where id=?");
			query.setParameter(0, id);
			Message message=(Message) query.uniqueResult();
			message.setStatus("进行中");
			session.save(message);
			System.out.println(id+"的订单状态修改成功");
		} catch (Exception e) {
			System.out.println(id+"的订单状态修改失败");
		}
	}
	
	public void edit1message(Integer id) {
		try {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Message where id=?");
			query.setParameter(0, id);
			Message message=(Message) query.uniqueResult();
			message.setStatus("待评价");
			session.save(message);
			System.out.println(id+"的订单状态修改成功");
		} catch (Exception e) {
			System.out.println(id+"的订单状态修改失败");
		}
	}
}
