package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.Account;
import com.entity.MyData;
import com.entity.Parentsinfo;
import com.entity.Studentsinfo;

@Repository
public class MyDataDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String mydata(String id,String user,String name,String sex,String phone,String address,String index,String email,String locate,String role) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from MyData where user=?");
		query.setParameter(0, user);
		MyData myData=(MyData) query.uniqueResult();
		if(user!=null) {
			if(index.equals("insert")) {
				if(name.equals("")) {name="null";}
				if(sex.equals("")) {sex="null";}
				if(phone.equals("")) {phone="null";}
				if(address.equals("")) {address="null";}
				if(email.equals("")) {email="null";}
				myData.setName(name);
				myData.setSex(sex);
				myData.setPhone(phone);
				myData.setAddress(address);
				myData.setEmail(email);
				myData.setLocate(locate);
				session.save(myData);
				System.out.println("更改信息成功");
				return "更改信息成功";
			}else if(index.equals("rname")) {
				return myData.getName()+","+myData.getLocate();
			}
			else if(index.equals("look")){
					return myData.getName()+","+ myData.getSex()+","+myData.getPhone()+","+myData.getAddress()+","+myData.getEmail()+","+myData.getLocate()+";";
			}else if(index.equals("name")) {
				
					return myData.getName();
			}else if(index.equals("renzheng")) {
				
					return myData.getAuthentication();
			}else if(index.equals("id")) {
					myData.setJgid(id);
				
					System.out.println(user+"的id更改成功"+id);
			}
			else if(index.equals("init")) {
				if(role.equals("11")) {
					Session session2=sessionFactory.getCurrentSession();
					Query query2=session2.createQuery("from Studentsinfo where user=?");
					query2.setParameter(0, user);
					Studentsinfo studentsinfo2= (Studentsinfo) query2.uniqueResult();
					return studentsinfo2.getName()+","+studentsinfo2.getSex()+","+studentsinfo2.getUniversity()+","+studentsinfo2.getCollege()+","+studentsinfo2.getMajor()+","+studentsinfo2.getGrade()+","+studentsinfo2.getSubject()+","+studentsinfo2.getExperience()+","+studentsinfo2.getWeek()+","+studentsinfo2.getTime()+","+studentsinfo2.getPay()+","+studentsinfo2.getIntroduce();
				}
				else if(role.equals("10")) {
					Session session2=sessionFactory.getCurrentSession();
					Query query2=session2.createQuery("from Parentsinfo where user=?");
					query2.setParameter(0, user);
					Parentsinfo parentsinfo=(Parentsinfo) query2.uniqueResult();
					return parentsinfo.getName()+","+parentsinfo.getSex()+","+parentsinfo.getGrade()+","+parentsinfo.getSubject()+","+parentsinfo.getLength()+","+parentsinfo.getWeek()+","+parentsinfo.getHour()+","+parentsinfo.getPay()+","+parentsinfo.getTel()+","+parentsinfo.getRequirement()+","+parentsinfo.getLocate();
				}
			}
			else if(index.equals("ifPublish")) {
				if(role.equals("10")) {
					Session session2=sessionFactory.getCurrentSession();
					Query query2=session2.createQuery("from Parentsinfo where user=?");
					query2.setParameter(0, user);
					Parentsinfo parentsinfo=(Parentsinfo) query2.uniqueResult();
					System.out.println(parentsinfo);
					if(parentsinfo!=null) {
						return "1";
					}else {
						return "0";
					}
				}
				else if(role.equals("11")) {
					Session session2=sessionFactory.getCurrentSession();
					Query query2=session2.createQuery("from Studentsinfo where user=?");
					query2.setParameter(0,user);
					Studentsinfo studentsinfo= (Studentsinfo) query2.uniqueResult();
					System.out.println(studentsinfo);
					if(studentsinfo!=null) {
						return "1";
					}else {
						return "0";
					}
				}
					
			}
		}
		
		return "";
	}
}
