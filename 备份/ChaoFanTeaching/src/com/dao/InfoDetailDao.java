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
				a+=studentsinfo.getName()+","+studentsinfo.getSex()+","+studentsinfo.getUniversity()+","+studentsinfo.getCollege()+","+studentsinfo.getMajor()+","+studentsinfo.getGrade()+","+studentsinfo.getSubject()+","+studentsinfo.getWeek()+studentsinfo.getTime()+","+studentsinfo.getPrice()+","+studentsinfo.getExperience()+","+studentsinfo.getIntroduce()+","+studentsinfo.getDistance()+","+studentsinfo.getUser();
			}
			return a;
		case "1":
			Session session1=sessionFactory.getCurrentSession();
			Query query1=session1.createQuery("from Parentsinfo where name like ?");
			query1.setParameter(0, "%"+key+"%");
			List<Parentsinfo> parentsinfos=(List<Parentsinfo>) query1.list();
			String a1="";
			for(Parentsinfo parentsinfo:parentsinfos) {
				a1+=parentsinfo.getName()+","+parentsinfo.getSex()+","+parentsinfo.getGrade()+","+parentsinfo.getSubject()+","+parentsinfo.getWeek()+" "+parentsinfo.getHour()+":"+parentsinfo.getMinute()+" "+parentsinfo.getLength()+"小时,"+parentsinfo.getPay()+","+parentsinfo.getTel()+","+parentsinfo.getRequirement()+","+parentsinfo.getLocate()+","+parentsinfo.getExperience()+","+parentsinfo.getUser();
			}
			return a1;
		case "2":
			try {	
				Session session2=sessionFactory.getCurrentSession();
				System.out.println("收藏功能传来的收藏者："+collector+"，被收藏者账号："+collection
						+"，被收藏者名字："+collectionName+"。");
				Star star=new Star();
				star.setUser(collector);
				star.setStaruser(collection);
				star.setStarname(collectionName);
				session2.save(star);
				System.out.println("收藏成功");
				return "1";
				
		
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("收藏不成功");
				e.printStackTrace();
				return "0";
			}
		}
		return "";
	}
	
	
}
