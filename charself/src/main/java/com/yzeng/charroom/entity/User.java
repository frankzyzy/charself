package com.yzeng.charroom.entity;

import java.util.Date;
import java.util.List;

public class User {
	private Integer id;
	private String username;
	private String password;
	private Date registertime;
	private Date lastTime;
	private String lastIp;
	public Date getRegistertime() {
		return registertime;
	}


	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}


	public Date getLastTime() {
		return lastTime;
	}


	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}


	public String getLastIp() {
		return lastIp;
	}


	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	private List<Message> msg;
	
	public User() {
		super();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Message> getMsg() {
		return msg;
	}
	public void setMsg(List<Message> msg) {
		this.msg = msg;
	}
	
}
