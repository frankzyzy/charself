package com.yzeng.charroom.entity;

import java.util.Date;

public class Message {
	private Integer id;
	private String msg;
	private Integer fromUserId;
	private Integer toUserId;
	private String fromUserName;
	private String toUserName;
	private String sendMsgTime;
	
	public Message(Integer id, String msg, Integer fromUserId, Integer toUserId, String fromUserName, String toUserName,
			String sendMsgTime) {
		super();
		this.id = id;
		this.msg = msg;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.fromUserName = fromUserName;
		this.toUserName = toUserName;
		this.sendMsgTime = sendMsgTime;
	}
	public String getSendMsgTime() {
		return sendMsgTime;
	}
	public void setSendMsgTime(String sendMsgTime) {
		this.sendMsgTime = sendMsgTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	
}
