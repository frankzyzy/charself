package com.yzeng.charroom.service;

import java.util.List;

import com.yzeng.charroom.entity.Message;

public interface MessageService {

	/**
	 * 插入一条消息
	 * @param message
	 * @return
	 */
	Integer insertMsg (Message message);
	
	/**
	 * 根据ID查询消息
	 * @param id
	 * @return
	 */
	Message getMsgById(Integer id);
	
	/**
	 * 聊天记录查询
	 * @param message
	 * @return
	 */
	List<Message> getMessageloggingList(Message message,int pageNum, int pageSize);
	
	/**
	 * 查询该用户的离线消息 
	 * @param userId
	 * @return
	 */
	List<Message> getOfflineMessageList(Integer userId);
	
	/**
	 * 查询该用户的消息传输状态1：已读 0：未读 2：失败
	 * @param message
	 * @return
	 */
	List<Message> getMessageListByStatus(Message message);
	
	/**
	 * 更新消息状态
	 * @param message
	 * @return
	 */
	int updateMessage (Message message);
	
	/**
	 * 更新消息的
	 * @return
	 */
	String updateOffline();
}
