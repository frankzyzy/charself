package com.yzeng.charroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.mapper.MessageMapper;
import com.yzeng.charroom.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public Integer insertMsg(Message message) {
		return messageMapper.insertMsg(message);
	}

	@Override
	public Message getMsgById(Integer id) {
		return messageMapper.getMsgById(id);
	}

}
