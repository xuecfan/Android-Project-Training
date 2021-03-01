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

@Repository
public class MyDataDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public String mydata(String id,String user,String name,String sex,String phone,String address,String index,String email) {
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
				session.save(myData);
				System.out.println("更改信息成功");
				return "更改信息成功";
			}else if(index.equals("look")){
					return myData.getSex()+","+ myData.getPhone()+","+myData.getAddress()+","+myData.getEmail()+","+myData.getJgid()+";";
			}else if(index.equals("name")) {
				
					return myData.getName();
			}else if(index.equals("renzheng")) {
				
					return myData.getAuthentication();
			}else if(index.equals("id")) {
					myData.setJgid(id);
				
					System.out.println(user+"的id更改成功"+id);
			}
		}
		
		return "";
	}
}
