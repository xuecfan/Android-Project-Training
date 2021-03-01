package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Account;
import com.entity.MyData;

@Repository
public class LogDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String login(String myid,String mypw) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Account where user=?");
		query.setParameter(0, myid);
		Account account=(Account) query.uniqueResult();
		if(account!=null) {
			if (mypw.equals(account.getPassword())) {
				if (account.getLandingStatus().equals("0")) {
					account.setLandingStatus("1");
					session.save(account);
					System.out.println(myid+"用户登录成功");
					return "1"+account.getRole();//共两位，第一位表示登陆是否成功，第二位表示用户身份，共四种情况：00-01-10-11,其中00和01不予使用
				}else if (account.getLandingStatus().equals("1")) {
					return "900";//表示用户登陆中，不允许再次登陆
				}
				
				System.out.println("密码一致");
				System.out.println();
			}else {
				System.out.println("密码不一致");
				return "0";

			}
		}else {
			System.out.println("没有此用户");
			return "0";
		}
		return "";
		
	}
	public String logon(String name,String pasd,Integer role,String landingStatus) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Account where user=?");
		query.setParameter(0, name);
		//System.out.println(account);
		if((Account) query.uniqueResult()!=null) {
			System.out.println("添加用户失败,用户已存在");
			return "0";
		}else {
			try {
					Account account=new Account();
					account.setUser(name);
					account.setPassword(pasd);
					account.setRole(role);
					account.setLandingStatus(landingStatus);
					session.save(account);
					MyData myData=new MyData();
					myData.setUser(name);
					myData.setAuthentication("0");
					session.save(myData);
				
					System.out.println("添加"+name+"用户成功");
					return "1";
				} catch (Exception e) {
					e.printStackTrace();
					return "0";
				}
			
		}

	}
	
	public String logout(String userName) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Account where user=?");
		query.setParameter(0, userName);
		Account account=(Account) query.uniqueResult();
		account.setLandingStatus("0");
		session.save(account);

		System.out.println(userName+"用户注销成功");
		return "1";
	}
	
	
}
