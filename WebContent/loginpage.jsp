<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LogIn</title>
</head>
<body>
<center>
<h1>Sign In</h1>
<%
String username=null;
Cookie[] cookies=request.getCookies();
if(cookies!=null){
	for(Cookie cookie: cookies){
		if(cookie.getName().equals("username")){
			username=cookie.getValue();
		}
		if(cookie.getName().equals("id")){
			cookie.setValue("1");
		}
	}
}
if(username!=null){
	System.out.println("came to if");
	%><jsp:forward page = "loginsuccess.jsp" /><%
}
else{
	System.out.println("came to else");
%>
<form action="loginservelet?action=loginpage" method="post">
UserName:<input type="text" name="username" value=""><br>
Password:<input type="password" name="password" value=""><br><br>
Login as: 
<input type="radio" name="option" value="customer">customer</input> &nbsp &nbsp
<input type="radio" name="option" value="seller">seller </input> &nbsp &nbsp
<input type="radio" name="option" value="transporter">transporter</input> &nbsp &nbsp
<input type="radio" name="option" value="admin">administrator</input> &nbsp &nbsp
<br>
<input type="submit"></input><br>
</form>
<a href="register.jsp">Register Here</a>
<%} %>
</center>
</body>
</html>