<%@ page import="org.springframework.web.client.RestTemplate" %>
<%@ page import="com.obj.meeting.dto.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: minjo
  Date: 2024-09-25
  Time: 오후 7:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String id =request.getParameter("id");

    RestTemplate restTemplate = new RestTemplate();

    User user = restTemplate.getForObject("http://localhost:8080/user/get/" + id, User.class);





%>

<%= user.getId()%>
<%= user.getUsername()%>
<%= user.getEmail()%>
<%= user.getRole()%>



</body>
</html>
