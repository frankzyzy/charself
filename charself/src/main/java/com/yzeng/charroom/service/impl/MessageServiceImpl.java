package com.yzeng.charroom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
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

	@Override
	public List<Message> getMessageloggingList(Message message,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return messageMapper.getMessageloggingList(message);
	}
	
	@Override
	public List<Message> getOfflineMessageList(Integer userId) {
		return messageMapper.getOfflineMessageList(userId);
	}

	@Override
	public int updateMessage(Message message) {
		return messageMapper.updateMessage(message);
	}

	@Override
	public List<Message> getMessageListByStatus(Message message) {
		return messageMapper.getMessageListByStatus(message);
	}

}