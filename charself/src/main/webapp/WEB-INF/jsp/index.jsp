<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>WithMe</title>
    <link href="${cp}/img/logo.ico" rel="icon" type="image/x-ico"> 
    <link href="${cp}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${cp}/css/animate.css" rel="stylesheet">
    <link href="${cp}/css/style.css" rel="stylesheet">
    <link href="${cp}/css/main-style.css" rel="stylesheet">

	<script src="${cp}/js/jquery.js" type="text/javascript"></script>
    <script src="${cp}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${cp}/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${cp}/js/layer.js" type="text/javascript"></script>
    <script src="${cp}/js/sockjs.js" type="text/javascript"></script>

    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

<body>
  
<script type="text/javascript">
	$(function(){
		layer.open({
    	    type: 2,
    	    title: 'Char',
    	    shadeClose: true,
    	    shade: 0.5,
    	    area: ['900px','600px'],
    	    resize:true,
    	    maxmin:true,
    	    scrollbar:true,
    	    //content: '${cp}/index1', //iframe的url
    	    content: 'http://localhost:8087', //iframe的url
    	    end: function() {
    	    	layer.msg('wecolme');
    	    }
    	});		
	})
</script>

</body>

</html>