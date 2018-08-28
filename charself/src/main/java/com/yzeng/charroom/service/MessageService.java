package com.yzeng.charroom.service;

import com.yzeng.charroom.entity.Message;

public interface MessageService {

	Integer insertMsg (Message message);
	
	Message getMsgById(Integer id);
	
}
