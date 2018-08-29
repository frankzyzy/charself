package com.yzeng.charroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.service.MessageService;

@Controller
@RequestMapping("msg")
public class MessageController {
	
	@Autowired
	private MessageService messageService; 

	@RequestMapping("{pageNum}-{pageSize}")
	@ResponseBody
	public List<Message> getList(@PathVariable("pageNum")Integer pageNum,@PathVariable("pageSize")Integer pageSize){
		Message message = new Message();
		message.setFromUserId(1);
		message.setToUserId(2);
		message.setMsgType(0);
		return messageService.getMessageloggingList(message,pageNum, pageSize);
	}
	
	@RequestMapping("getOfflineMessageList")
	@ResponseBody
	public List<Message> getOfflineMessageList(Integer userId){
		Message message = new Message();
		message.setToUserId(userId);
		message.setIsTransport(0);
		return messageService.getMessageListByStatus(message);
	}
	
	@RequestMapping("updateOffline")
	public String updateOffline(int[] messageIds) {
		Message message = new Message();
		message.setIsTransport(1);
		messageService.updateMessage(message);
		return "";
	}
}
