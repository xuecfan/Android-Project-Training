package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Parentsinfo;
import com.entity.Studentsinfo;

import DNG.DbUtil;

@Repository
public class MyPublishDao {

	@Resource
	private SessionFactory sessionFactory;
	public String mypublish(String op,String key,Integer id,String name,String sex,String grade,String subject,
			String week,String university,String college,String major,String time,String price,String introduce) {
		switch (op) {
		case "my0":
				Session session=sessionFactory.getCurrentSession();
				Query query=session.createQuery("from Studentsinfo where user=?");
				query.setParameter(0, "%"+key+"%");
				Studentsinfo studentsinfo=(Studentsinfo) query.uniqueResult();
				if(studentsinfo!=null) {
					return studentsinfo.getName()+","+studentsinfo.getId()+",";	
				}
					
		case "my1":
				Session session1=sessionFactory.getCurrentSession();
				Query query1=session1.createQuery("from Parentsinfo where user=?");
				query1.setParameter(0, "%"+key+"%");
				
				Parentsinfo parentsinfo=(Parentsinfo) query1.uniqueResult();
				if(parentsinfo!=null) {
					return parentsinfo.getName()+","+parentsinfo.getId()+",";
				}
				
		case "del":
				Session session2=sessionFactory.getCurrentSession();
				String hql="delete from Studentsinfo where id=?";
			    Query query2=session2.createQuery(hql);
				query2.setParameter(0,key);
				int n= query2.executeUpdate();
				session2.beginTransaction().commit();
				
					if(n>0) {
						System.out.println("删除信息成功");
					}else {
						System.out.println("删除信息失败");
					}
					break;
		case "del1":
				Session session3=sessionFactory.getCurrentSession();
				String hql1="delete from Ptudentsinfo where id=?";
			    Query query3=session3.createQuery(hql1);
				query3.setParameter(0,key);
				int n1= query3.executeUpdate();
				session3.beginTransaction().commit();
			
					if(n1>0) {
						System.out.println("删除信息成功");
					}else {
						System.out.println("删除信息失败");
					}
		case "edit":
				Session session4=sessionFactory.getCurrentSession();
				Query query4=session4.createQuery("from Studentsinfo where id=?");
				query4.setParameter(0, id);
				Studentsinfo studentsinfo2=(Studentsinfo) query4.uniqueResult();
				studentsinfo2.setName(name);
				studentsinfo2.setSex(sex);
				studentsinfo2.setUniversity(university);
				studentsinfo2.setCollege(college);
				studentsinfo2.setMajor(major);
				studentsinfo2.setGrade(grade);
				studentsinfo2.setSubject(subject);
				studentsinfo2.setWeek(week);
				studentsinfo2.setTime(time);
				studentsinfo2.setPrice(price);
				studentsinfo2.setIntroduce(introduce);
				studentsinfo2.setId(id);
				session4.save(studentsinfo2);
				
				System.out.println("修改信息成功");
				break;
	}
		return "";
	}
	
}
