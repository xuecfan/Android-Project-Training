package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Parentsinfo;
import com.entity.Studentsinfo;

@Repository
public class AddInfoDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String addinfo(String user,String id,String name,String sex,String grade,String subject,String week,String pay,String tel,
			String require,String locate,String hour,String len,String university,
			String college,String major,String time,String experience) {
		switch (id) {
		case "0":
				Session session=sessionFactory.getCurrentSession();
				Parentsinfo parentsinfo=new Parentsinfo();
				parentsinfo.setUser(user);
				parentsinfo.setName(name);
				parentsinfo.setSex(sex);
				parentsinfo.setGrade(grade);
				parentsinfo.setSubject(subject);
				parentsinfo.setWeek(week);
				parentsinfo.setHour(hour);
				//parentsinfo.setMinute(min);
				parentsinfo.setLength(len);
				parentsinfo.setPay(pay);
				parentsinfo.setTel(tel);
				parentsinfo.setRequirement(require);
				parentsinfo.setLocate(locate);
				try {
					session.save(parentsinfo);
					System.out.println("保存成功");
					return "1";
				} catch (Exception e) {
					System.out.println("保存失败");
					return "0";
				}
		case "1":
				Session session1=sessionFactory.getCurrentSession();
				Studentsinfo studentsinfo=new Studentsinfo();
				studentsinfo.setUser(user);
				studentsinfo.setName(name);
				studentsinfo.setSex(sex);
				studentsinfo.setUniversity(university);
				studentsinfo.setCollege(college);
				studentsinfo.setMajor(major);
				studentsinfo.setGrade(grade);
				studentsinfo.setSubject(subject);
				studentsinfo.setWeek(week);
				studentsinfo.setTime(time);
				studentsinfo.setPay(pay);
				studentsinfo.setIntroduce(require);	
				studentsinfo.setExperience(experience);
				try {
					session1.save(studentsinfo);
					System.out.println("保存成功");
					return "1";
				} catch (Exception e) {
					System.out.println("保存失败");
					return "0";
				}
		}
		return "";
		
	}
	
	
}
