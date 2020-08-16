package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="star")
public class Star {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String user;
	private String starname;
	private String staruser;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getStarname() {
		return starname;
	}
	public void setStarname(String starname) {
		this.starname = starname;
	}
	public String getStaruser() {
		return staruser;
	}
	public void setStaruser(String staruser) {
		this.staruser = staruser;
	}
	
}
