<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>dsad</title>
<script type="text/javascript" src="${ctx}/js/jquery1.9.1/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
</head>

<body>
    <br/><input id="username" type="text" value="${user.username }"/>
    <br/><input id="text" type="text"/>
    <button onclick="send()">发送消息</button>
    <hr/>
    <button onclick="connect()">WebSocket连接</button>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>
    <hr/>
    <div id="message"></div>
<script type="text/javascript">
var websocket = null;
var toUserName = '';
function connect(){
	if($("#username").val() == "admin"){
		toUserName = "tom";
	}else{
		toUserName = "admin";
	}
	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
	    websocket = new WebSocket("ws://localhost:8086/socketserver/${user.id}");
	}
	else {
	    alert('当前浏览器 Not support websocket')
	}
	
	//连接发生错误的回调方法
	websocket.onerror = function () {
	    setMessageInnerHTML("WebSocket连接发生错误");
	};
	
	//连接成功建立的回调方法
	websocket.onopen = function () {
	    setMessageInnerHTML("WebSocket连接成功");
	}
	
	//接收到消息的回调方法
	websocket.onmessage = function (event) {
		console.log(event);
		var data = $.parseJSON(event.data);
		var stringTime = hssduc.util.DateUtils.formatTimeStamp(data.time,"yyyy-MM-dd HH:mm:ss")
	    setMessageInnerHTML("发送者："+data.fromUserName+" 时间："+stringTime +" : "+ data.content);
	}
	
	//连接关闭的回调方法
	websocket.onclose = function () {
	    setMessageInnerHTML("WebSocket连接关闭");
	}
	
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function () {
	    closeWebSocket();
	}
}
//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    var param = {
   		from : ${user.id},
		to : 2,
		content : $("#text").val(),
		type : 0,
    }
    websocket.send(JSON.stringify(param));
}
</script>




</body>

</html>