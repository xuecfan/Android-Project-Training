package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Parentsinfo;
import com.entity.Star;
import com.entity.Studentsinfo;

import DNG.DbUtil;

@Repository
public class InfoDetailDao {
	
	@Resource
	private SessionFactory sessionFactory;
	
	public String infodetail(String id,String key,String collector,String collection,String collectionName) {
		switch (id) {
		case "0":
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Studentsinfo where name like ?");
			query.setParameter(0, "%"+key+"%");
			String a="";
			List<Studentsinfo> studentsinfos=(List<Studentsinfo>) query.list();
			for(Studentsinfo studentsinfo:studentsinfos) {
				a+=studentsinfo.getName()+","+studentsinfo.getSex()+","+studentsinfo.getUniversity()+","+studentsinfo.getCollege()+","+studentsinfo.getMajor()+","+studentsinfo.getGrade()+","+studentsinfo.getSubject()+","+studentsinfo.getWeek()+studentsinfo.getTime()+","+studentsinfo.getPay()+","+studentsinfo.getExperience()+","+studentsinfo.getIntroduce()+","+studentsinfo.getUser()+","+studentsinfo.getAuthentication()+","+studentsinfo.getExperience();
			}
			return a;
		case "1":
			Session session1=sessionFactory.getCurrentSession();
			Query query1=session1.createQuery("from Parentsinfo where name like ?");
			query1.setParameter(0, "%"+key+"%");
			List<Parentsinfo> parentsinfos=(List<Parentsinfo>) query1.list();
			String a1="";
			for(Parentsinfo parentsinfo:parentsinfos) {
				a1+=parentsinfo.getName()+","+parentsinfo.getSex()+","+parentsinfo.getGrade()+","+parentsinfo.getSubject()+","+parentsinfo.getWeek()+" "+parentsinfo.getHour()+" "+parentsinfo.getLength()+"小时,"+parentsinfo.getPay()+","+parentsinfo.getTel()+","+parentsinfo.getRequirement()+","+parentsinfo.getLocate()+","+parentsinfo.getUser()+","+parentsinfo.getAuthentication();
			}
			return a1;
//		case "2":
//			try {	
//				Session session2=sessionFactory.getCurrentSession();
//				System.out.println("收藏功能传来的收藏者："+collector+"，被收藏者账号："+collection
//						+"，被收藏者名字："+collectionName+"。");
//				Star star=new Star();
//				star.setUser(collector);
//				star.setStaruser(collection);
//				star.setStarname(collectionName);
//				session2.save(star);
//				System.out.println("收藏成功");
//				return "1";
//				
//		
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				System.out.println("收藏不成功");
//				e.printStackTrace();
//				return "0";
//			}
//		}
		case "2":
			try {//用户点击收藏按钮触发
				Session session2=sessionFactory.getCurrentSession();
				Query query2=session2.createQuery("from Star where user=? and starname=? and staruser=?");
				query2.setParameter(0, collector);
				query2.setParameter(1, collectionName);
				query2.setParameter(2, collection);
				Star star=(Star) query2.uniqueResult();
				if(star!=null) {//已收藏,进行取消收藏操作
//					Session session3=sessionFactory.getCurrentSession();
//					String hql1="delete from Ptudentsinfo where id=?";
//				    Query query3=session3.createQuery(hql1);
//					query3.setParameter(0,key);
//					int n1= query3.executeUpdate();
//					session3.beginTransaction().commit();
					
					try {
						session2.delete(star);
						System.out.println("取消收藏成功");
						return "2";
						
					} catch (Exception e) {
						System.out.println("取消收藏失败");
						return "3";
					}
				}else {//未收藏，进行收藏操作
					try {
						Session session3=sessionFactory.getCurrentSession();
						Star star2=new Star();
						star2.setUser(collector);
						star2.setStarname(collectionName);
						star2.setStaruser(collection);
						session3.save(star2);
						System.out.println("收藏成功");
						return "0";
					} catch (Exception e) {
						System.out.println("收藏失败");
						return "1";
					}
				}						
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case "3":
			try {//用户进入详情页面触发
				Session session4=sessionFactory.getCurrentSession();
				Query query3=session4.createQuery("from Star where user=? and starname=? and staruser=?");
				query3.setParameter(0, collector);
				query3.setParameter(1, collectionName);
				query3.setParameter(2, collection);
				Star star1=(Star) query3.uniqueResult();
				if(star1!=null) {//已收藏
					System.out.println("已收藏");
					return "1";
					
					
				}else {//未收藏
					System.out.println("未收藏");
					return "0";
					
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		}
		return "";
	}
	
	
}
