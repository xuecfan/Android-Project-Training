package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String user;
	private String password;
	private int role;
	private String photoaddress;
	private String landingStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getPhotoaddress() {
		return photoaddress;
	}
	public void setPhotoaddress(String photoaddress) {
		this.photoaddress = photoaddress;
	}
	public String getLandingStatus() {
		return landingStatus;
	}
	public void setLandingStatus(String landingStatus) {
		this.landingStatus = landingStatus;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", user=" + user + ", password=" + password + ", role=" + role + ", photoaddress="
				+ photoaddress + ", landingStatus=" + landingStatus + "]";
	}
	
}
