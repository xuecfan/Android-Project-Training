package com.dao;

import java.nio.channels.SeekableByteChannel;
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
			String week,String university,String college,String major,String time,String price,String introduce,String role,String user,String experience,String length,String tel,String requirement,String locate) {
		switch (op) {
		case "delete":
			
			if(role.equals("11")) {
				Session session=sessionFactory.getCurrentSession();
				Query query=session.createQuery("from Studentsinfo where user=?");
				query.setParameter(0, user);
				Studentsinfo studentsinfo=(Studentsinfo) query.uniqueResult();
				if(studentsinfo!=null) {
					session.delete(studentsinfo);
					System.out.println("发布的学生信息删除成功");
					return "1";
				}else {
					System.out.println("发布的学生信息删除失败");
					return "0";
				}
				
			}else if(role.equals("10")) {
				Session session=sessionFactory.getCurrentSession();
				Query query=session.createQuery("from Parentsinfo where user=?");
				query.setParameter(0, user);
				Parentsinfo parentsinfo=(Parentsinfo) query.uniqueResult();
				
				if(parentsinfo!=null) {
					session.delete(parentsinfo);
					System.out.println("发布的家长信息删除成功");
					return "1";
				}else {
					System.out.println("发布的家长信息删除失败");
					return "0";
				}
			}
			break;
			case "editParent":
				Session session2=sessionFactory.getCurrentSession();
				Query query=session2.createQuery("from Parentsinfo where user=?");
				query.setParameter(0, user);
				Parentsinfo parentsinfo=(Parentsinfo) query.uniqueResult();
				parentsinfo.setName(name);
				parentsinfo.setSex(sex);
				parentsinfo.setGrade(grade);
				parentsinfo.setSubject(subject);
				parentsinfo.setWeek(week);
				parentsinfo.setHour(time);
				parentsinfo.setLength(length);
				parentsinfo.setPay(price);
				parentsinfo.setTel(tel);
				parentsinfo.setRequirement(requirement);
				parentsinfo.setLocate(locate);
				try {
					session2.save(parentsinfo);
					System.out.println(user+"修改信息成功");
					return "1";
				} catch (Exception e) {
					System.out.println(user+"修改信息失败");
					return "0";
				}
			case "editTeacher":
				Session session3=sessionFactory.getCurrentSession();
				Query query1=session3.createQuery("from Studentsinfo where user=?");
				query1.setParameter(0, user);
				Studentsinfo studentsinfo=(Studentsinfo) query1.uniqueResult();
				
				studentsinfo.setName(name);
				studentsinfo.setSex(sex);
				studentsinfo.setUniversity(university);
				studentsinfo.setCollege(college);
				studentsinfo.setMajor(major);
				studentsinfo.setGrade(grade);
				studentsinfo.setSubject(subject);
				studentsinfo.setWeek(week);
				studentsinfo.setTime(time);
				studentsinfo.setExperience(experience);
				studentsinfo.setPay(price);
				studentsinfo.setIntroduce(introduce);
				try {
					session3.save(studentsinfo);
					System.out.println(user+"修改信息成功");
					return "1";
				} catch (Exception e) {
					System.out.println(user+"修改信息失败");
					return "0";
				}
				
				
//		case "my0":
//				Session session=sessionFactory.getCurrentSession();
//				Query query=session.createQuery("from Studentsinfo where user=?");
//				query.setParameter(0,key);
//				Studentsinfo studentsinfo=(Studentsinfo) query.uniqueResult();
//				if(studentsinfo!=null) {
//					return studentsinfo.getName()+","+studentsinfo.getId()+",";	
//				}
//				break;	
//		case "my1":
//				Session session1=sessionFactory.getCurrentSession();
//				Query query1=session1.createQuery("from Parentsinfo where user=?");
//				query1.setParameter(0,key);
//				
//				Parentsinfo parentsinfo=(Parentsinfo) query1.uniqueResult();
//				if(parentsinfo!=null) {
//					return parentsinfo.getName()+","+parentsinfo.getId()+",";
//				}
//				break;
//		case "del":
//				
//				try {
//					Session session2=sessionFactory.getCurrentSession();
//				    Query query2=session2.createQuery("from Studentsinfo where id=?");
//				    System.out.println(key);
//					query2.setParameter(0,id);
//					Studentsinfo studentsinfo2=(Studentsinfo) query2.uniqueResult();
//					session2.delete(studentsinfo2);
//					System.out.println("发布的学生信息删除成功");
//				} catch (Exception e) {
//					System.out.println("发布的学生信息删除失败");
//				}	
//				break;		
//					
//				
//		case "del1":
//				
//				try {
//					Session session3=sessionFactory.getCurrentSession();
//				    Query query3=session3.createQuery("from Parentsinfo where id=?");
//					query3.setParameter(0,id);
//					Parentsinfo parentsinfo2=(Parentsinfo) query3.uniqueResult();
//					session3.delete(parentsinfo2);
//					System.out.println("发布的家长信息删除成功");
//				} catch (Exception e) {
//					System.out.println("发布的家长信息删除失败");
//				}
//				break;
//		case "edit":
//				
//				try {
//					Session session4=sessionFactory.getCurrentSession();
//					Query query4=session4.createQuery("from Studentsinfo where id=?");
//					query4.setParameter(0, id);
//					Studentsinfo studentsinfo2=(Studentsinfo) query4.uniqueResult();
//					studentsinfo2.setName(name);
//					studentsinfo2.setSex(sex);
//					studentsinfo2.setUniversity(university);
//					studentsinfo2.setCollege(college);
//					studentsinfo2.setMajor(major);
//					studentsinfo2.setGrade(grade);
//					studentsinfo2.setSubject(subject);
//					studentsinfo2.setWeek(week);
//					studentsinfo2.setTime(time);
//					studentsinfo2.setIntroduce(introduce);
//					session4.save(studentsinfo2);
//					System.out.println("修改信息成功");
//				} catch (Exception e) {
//					System.out.println("修改信息失败");
//				}
//				
//				break;
	}
		return "";
	}
	
}
