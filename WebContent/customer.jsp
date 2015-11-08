<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String username=null;
Cookie[] cookies=request.getCookies();
if(cookies!=null){
	for(Cookie cookie: cookies){
		username=cookie.getValue();
	}
}
if(username==null){
	%><jsp:forward page = "loginpage.jsp" /><%
}
%>
<center><h1>Welcome to eMarket</h1></center><p align="right">Hello, UserName
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="www.google.com" >Your Orders</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href="www.google.com" >Log Out</a></p>

<br>
<br>
<br>
<center>
<table width="700"><tr><td><b>Product Name</td><td><b>Price</td></td></tr>
</body>
</html>