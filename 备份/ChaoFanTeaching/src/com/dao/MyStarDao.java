package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.entity.Star;

@Repository
public class MyStarDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String mystar(String user,String op,String delname) {
		
		
		switch (op) {
		case "scan":
				Session session=sessionFactory.getCurrentSession();
				Query query=session.createQuery("from Star where user=?");
				query.setParameter(0, user);
				List<Star> stars=(List<Star>) query.list();
				String a="";
				for(Star star:stars) {
					a+=star.getStarname() +","+star.getStaruser()+",";
				}
				System.out.println(a);
				
				return a;
		case "del":
				try {
					Session session2=sessionFactory.openSession();
					Transaction tx=session2.beginTransaction();
					String hql="delete from Star where user=? and starname=?";
				    Query query1=session2.createQuery(hql);
					query1.setParameter(0,user);
					query1.setParameter(1,delname);
					query1.executeUpdate();
					tx.commit();
					System.out.println("删除信息成功");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("删除信息失败");
				}
				
			break;
		}
		return "";
	}
}
