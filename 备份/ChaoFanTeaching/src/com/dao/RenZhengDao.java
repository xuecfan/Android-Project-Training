package com.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RenZhengDao {

	@Resource
	private SessionFactory sessionFactory;
	
	public void renzheng(String province,String university,
			String studentNo,String xuezhi) {
		System.out.println(province+university+studentNo+xuezhi);
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from RenZheng where studentNo=?");
		query.setParameter(0,studentNo);
		
	}
}
