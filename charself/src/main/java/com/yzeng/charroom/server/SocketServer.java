package com.yzeng.charroom.server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzeng.charroom.entity.Message;
import com.yzeng.charroom.entity.User;
import com.yzeng.charroom.service.UserService;

@ServerEndpoint(value = "/socketserver/{userId}")
@Component
public class SocketServer {
	//表示与某个用户的连接会话，通过它给客户端发送数据
	private Session session;
	//在线人数
	private static Integer onlineCount = 0;
	
	private static Integer id = 0; 
	//客户端对象列表
	private static Map<User, SocketServer> clients = new ConcurrentHashMap<User,SocketServer>();
	//用户与websocket绑定session
	private static Map<Integer,Session> userPool = new HashMap<Integer,Session>();
	//当前用户
	private User user;
	
	//此处是解决无法注入的关键(同时配置启动类)
	private static ApplicationContext applicationContext;
	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}
	//你要注入的service或者dao
	private UserService userService;
	
	/**
    * 用户连接时触发
    * @param session
    * @param userid
    */
	@OnOpen
	public void open(Session session,@PathParam(value="userId")Integer userId) {
		this.session = session;
		userService = applicationContext.getBean(UserService.class);
		user = userService.getUser(userId);
		addOnlineCount();
		clients.put(user, this);
		userPool.put(userId, session);
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Message msg = new Message();
		JSONObject jsonMsg = JSON.parseObject(message); 
		Integer type = jsonMsg.getIntValue("type");
		Integer from = jsonMsg.getIntValue("from");
		Integer to = jsonMsg.getIntValue("to");
		String content = jsonMsg.getString("content");
		msg.setMsgType(type);
		msg.setContent(content);
		msg.setFromUserId(from);
		msg.setFromUserName(user.getUsername());
		msg.setToUserId(to);
		msg.setToUserName(userService.getUser(to).getUsername());
		msg.setSendMsgTime(formatter.format(new Date()));
		sendMessage(msg);
		System.out.println("当前发送人sessionid为"+session.getId()+"发送内容为"+message);
	}
	
	/**
    * 连接关闭触发
    */
	@OnClose
	public void onClose() {
		clients.remove(user);  
		userPool.remove(user.getId());
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
	
    public void sendMessage(Message message) throws IOException {
    	Session toSession = userPool.get(message.getToUserId());
    	if(toSession.isOpen()) {
    		toSession.getBasicRemote().sendText(getMessage(message));
    	}else {
    		
    	}
    }
    
    public void sendMessageTo(String message, String To) throws IOException {  
//        for (SocketServer item : clients.values()) { 
//            if (item.user.getUsername().equals(To) ) {
//            	System.out.println(JSON.toJSONString(user.getMsg().get(0)));
//				String jsonString = JSON.toJSONString(user.getMsg().get(0));
//				item.session.getAsyncRemote().sendText(jsonString);  
//			}
//        }  
    	SocketServer server = clients.get(user);
    	server.session.getAsyncRemote().sendText(message);
    	System.out.println(server);
    }  
      
    public void sendMessageAll(String message) throws IOException {  
        for (SocketServer item : clients.values()) {  
            item.session.getAsyncRemote().sendText(message);  
        }  
    }  
	
	
    /**
     * 根据Message实体组装Json格式的数据返回给前台
     */
	public String getMessage(Message message){
		//使用JSONObject方法构建Json数据
        JSONObject jsonObjectMessage = new JSONObject();
        jsonObjectMessage.put("from", String.valueOf(message.getFromUserId()));
        jsonObjectMessage.put("fromName", String.valueOf(message.getFromUserName()));
        jsonObjectMessage.put("to", new String[] {String.valueOf(message.getToUserId())});
        jsonObjectMessage.put("toName", new String[] {String.valueOf(message.getToUserName())});
        jsonObjectMessage.put("content", String.valueOf(message.getContent()));
        jsonObjectMessage.put("type", String.valueOf(message.getMsgType()));
        jsonObjectMessage.put("time", message.getSendMsgTime());
        return jsonObjectMessage.toString();
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
