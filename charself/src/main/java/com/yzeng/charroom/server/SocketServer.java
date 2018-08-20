package com.yzeng.charroom.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.entity.User;

@ServerEndpoint(value = "/socketserver/{userId}")
@Component
public class SocketServer {
	
	private Session session;
	private static Integer onlineCount = 0;
	private static Integer id = 0; 
	private static Map<User, SocketServer> clients = new ConcurrentHashMap<User,SocketServer>();
	private User user;
	
	/**
    * 用户连接时触发
    * @param session
    * @param userid
    */
	@OnOpen
	public void open(Session session,@PathParam(value="userId")String userId) {
		this.session = session;
		
		user = new User();
		user.setId(id+1);
		user.setUsername(userId);
		
		addOnlineCount();
		clients.put(user, this);
		System.out.println(userId+"已连接");
	}
	
	/**
    * 收到信息时触发
    * @param message 客户端发送过来的消息
    * @param session 可选的参数
	 * @throws IOException 
    */		
	@OnMessage
	public void onMessage(String message,Session session) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<Message> msgList = new ArrayList<Message>();
		JSONObject jsonMsg = JSON.parseObject(message); 
		String sendMethod = jsonMsg.getString("sendMethod");
		String sendUserId = jsonMsg.getString("sendUserId");
		String msg = jsonMsg.getString("msg");
		Message msgVo = new Message(id+1, msg, user.getId(), 88, user.getUsername(), "tom", formatter.format(new Date()));
		msgList.add(msgVo);
		user.setMsg(msgList);
		if(sendMethod.equals("to")) {
			sendMessageTo(msg, sendUserId);
		}else if(sendMethod.equals("all")){
			sendMessageAll(msg);
		}
		System.out.println("当前发送人sessionid为"+session.getId()+"发送内容为"+message);
	}
	
	/**
    * 连接关闭触发
    */
	@OnClose
	public void onClose() {
		clients.remove(user);  
        subOnlineCount();  
	}
	
	/**
    * 发生错误时触发
    * @param session
    * @param error
    */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
	
    
    public void sendMessageTo(String message, String To) throws IOException {  
        for (SocketServer item : clients.values()) { 
            if (item.user.getUsername().equals(To) ) {
            	System.out.println(JSON.toJSONString(user.getMsg().get(0)));
				String jsonString = JSON.toJSONString(user.getMsg().get(0));
				item.session.getAsyncRemote().sendText(jsonString);  
			}
        }  
    }  
      
    public void sendMessageAll(String message) throws IOException {  
        for (SocketServer item : clients.values()) {  
            item.session.getAsyncRemote().sendText(message);  
        }  
    }  
	
	
    public static synchronized int getOnlineCount() {  
        return onlineCount;  
    }  
  
    public static synchronized void addOnlineCount() {  
    	SocketServer.onlineCount++;  
    }  
  
    public static synchronized void subOnlineCount() {  
    	SocketServer.onlineCount--;  
    }  
	
	
	
}
