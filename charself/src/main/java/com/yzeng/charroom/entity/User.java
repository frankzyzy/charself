package com.yzeng.charroom.entity;

import java.util.Date;
import java.util.List;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String loginTime;
	private List<Message> msg;
	
	public User() {
		super();
	}
	
	public User(Integer id, String username, String password, String loginTime, List<Message> msg) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.loginTime = loginTime;
		this.msg = msg;
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
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public List<Message> getMsg() {
		return msg;
	}
	public void setMsg(List<Message> msg) {
		this.msg = msg;
	}
	
}
