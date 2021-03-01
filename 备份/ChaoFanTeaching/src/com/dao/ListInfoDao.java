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
import com.entity.Studentsinfo;

import DNG.DbUtil;

@Repository
public class ListInfoDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String listinfo(String op,String key) {
		switch (op) {
		case "sortexp":
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Studentsinfo order by exprience desc");
			List<Studentsinfo> studentsinfos=(List<Studentsinfo>) query.list();
			String a="";
			for(Studentsinfo studentsinfo:studentsinfos) {
				a+= studentsinfo.getName()+","+studentsinfo.getUniversity()+","+studentsinfo.getCollege()+","+studentsinfo.getSubject()+","+studentsinfo.getPrice()+","+studentsinfo.getExperience()+","+studentsinfo.getUser()+";";
			}
			return a;
		
		case "upprice":
			Session session1=sessionFactory.getCurrentSession();
			Query query1=session1.createQuery("from Studentsinfo order by price");
			List<Studentsinfo> studentsinfo1s=(List<Studentsinfo>) query1.list();
			String a1="";
			for(Studentsinfo studentsinfo1:studentsinfo1s) {
				a1+= studentsinfo1.getName()+","+studentsinfo1.getUniversity()+","+studentsinfo1.getCollege()+","+studentsinfo1.getSubject()+","+studentsinfo1.getPrice()+","+studentsinfo1.getExperience()+","+studentsinfo1.getUser()+";";
			}
			return a1;
			
		case "downprice":
			Session session2=sessionFactory.getCurrentSession();
			Query query2=session2.createQuery("from Studentsinfo order by price desc");
			List<Studentsinfo> studentsinfo2s=(List<Studentsinfo>) query2.list();
			String a2="";
			for(Studentsinfo studentsinfo2:studentsinfo2s) {
				a2+=studentsinfo2.getName()+","+studentsinfo2.getUniversity()+","+studentsinfo2.getCollege()+","+studentsinfo2.getSubject()+","+studentsinfo2.getPrice()+","+studentsinfo2.getExperience()+","+studentsinfo2.getUser()+";";
			}
			return a2;
		
		case "updis":
			Session session3=sessionFactory.getCurrentSession();
			Query query3=session3.createQuery("from Parentsinfo order by distance");
			List<Parentsinfo> parentsinfos=(List<Parentsinfo>) query3.list();
			String a3="";
			for(Parentsinfo parentsinfo:parentsinfos) {
				a3+=parentsinfo.getName()+","+parentsinfo.getSex()+","+parentsinfo.getGrade()+parentsinfo.getSubject()+","+parentsinfo.getPay()+","+parentsinfo.getWeek()+" "+parentsinfo.getHour()+":"+parentsinfo.getMinute()+","+parentsinfo.getDistance()+","+parentsinfo.getUser()+";";
			}
			return a3;
		
		case "downdis":
			Session session4=sessionFactory.getCurrentSession();
			Query query4=session4.createQuery("from Parentsinfo order by distance desc");
			List<Parentsinfo> parentsinfo1s=(List<Parentsinfo>) query4.list();
			String a4="";
			for(Parentsinfo parentsinfo1:parentsinfo1s) {
				a4+=parentsinfo1.getName()+","+parentsinfo1.getSex()+","+parentsinfo1.getGrade()+parentsinfo1.getSubject()+","+parentsinfo1.getPay()+","+parentsinfo1.getWeek()+" "+parentsinfo1.getHour()+":"+parentsinfo1.getMinute()+","+parentsinfo1.getDistance()+","+parentsinfo1.getUser()+";";
			}
			return a4;
			
		case "upprice1":
			Session session5=sessionFactory.getCurrentSession();
			Query query5=session5.createQuery("from Parentsinfo order by pay");
			List<Parentsinfo> parentsinfo2s=(List<Parentsinfo>) query5.list();
			String a5="";
			for(Parentsinfo parentsinfo2:parentsinfo2s) {
				a5+=parentsinfo2.getName()+","+parentsinfo2.getSex()+","+parentsinfo2.getGrade()+parentsinfo2.getSubject()+","+parentsinfo2.getPay()+","+parentsinfo2.getWeek()+" "+parentsinfo2.getHour()+":"+parentsinfo2.getMinute()+","+parentsinfo2.getDistance()+","+parentsinfo2.getUser()+";";
			}
			return a5;
		case "downprice1":
			Session session0=sessionFactory.getCurrentSession();
			Query query0=session0.createQuery("from Parentsinfo order by pay desc");
			List<Parentsinfo> parentsinfo3s=(List<Parentsinfo>) query0.list();
			String a6="";
			for(Parentsinfo parentsinfo3:parentsinfo3s) {
				a6+=parentsinfo3.getName()+","+parentsinfo3.getSex()+","+parentsinfo3.getGrade()+parentsinfo3.getSubject()+","+parentsinfo3.getPay()+","+parentsinfo3.getWeek()+" "+parentsinfo3.getHour()+":"+parentsinfo3.getMinute()+","+parentsinfo3.getDistance()+","+parentsinfo3.getUser()+";";
			}
			return a6;
		
		case "serach1":
			Session session9=sessionFactory.getCurrentSession();
			Query query9=session9.createQuery("from Parentsinfo where name like ? or grade like ? or subject like ? or pay like ?");
			query9.setParameter(0, "%"+key+"%");
			query9.setParameter(1, "%"+key+"%");
			query9.setParameter(2, "%"+key+"%");
			query9.setParameter(3, "%"+key+"%");
			List<Parentsinfo> parentsinfo4s=(List<Parentsinfo>) query9.list();
			String a7="";
			for(Parentsinfo parentsinfo4:parentsinfo4s) {
				a7+=parentsinfo4.getName()+","+parentsinfo4.getSex()+","+parentsinfo4.getGrade()+parentsinfo4.getSubject()+","+parentsinfo4.getPay()+","+parentsinfo4.getWeek()+" "+parentsinfo4.getHour()+":"+parentsinfo4.getMinute()+","+parentsinfo4.getDistance()+","+parentsinfo4.getUser()+";";
			}
			return a7;
			
		case "serach":
			Session session6=sessionFactory.getCurrentSession();
			Query query6=session6.createQuery("from Studentsinfo where name like ? or college like ? or subject like ? or price like ?");
			query6.setParameter(0, "%"+key+"%");
			query6.setParameter(1, "%"+key+"%");
			query6.setParameter(2, "%"+key+"%");
			query6.setParameter(3, "%"+key+"%");
			List<Studentsinfo> studentsinfo4s=(List<Studentsinfo>) query6.list();
			String a8="";
			for(Studentsinfo studentsinfo4:studentsinfo4s) {
				a8+=studentsinfo4.getName()+","+studentsinfo4.getUniversity()+","+studentsinfo4.getCollege()+","+studentsinfo4.getSubject()+","+studentsinfo4.getPrice()+","+studentsinfo4.getExperience()+","+studentsinfo4.getUser()+";";
				
			}
			 
			return a8;
		case "my0":
			Session session8=sessionFactory.getCurrentSession();
			Query query8=session8.createQuery("from Studentsinfo where user like ?");
			query8.setParameter(0, "%"+key+"%");
			Studentsinfo studentsinfo5=(Studentsinfo) query8.uniqueResult();
			return studentsinfo5.getName()+","+studentsinfo5.getId()+",";
			
		case "my1":
			Session session7=sessionFactory.getCurrentSession();
			Query query7=session7.createQuery("from Parentsinfo where user like ?");
			query7.setParameter(0, "%"+key+"%");
			Parentsinfo parentsinfo5=(Parentsinfo) query7.uniqueResult();
			return parentsinfo5.getName()+","+parentsinfo5.getId()+",";
			
		}
		return "";
	}
	
}
