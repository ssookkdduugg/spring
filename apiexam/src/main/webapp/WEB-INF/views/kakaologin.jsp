<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="https://kauth.kakao.com/oauth/authorize?client_id=6995e278624264293de03a555a01e3b0&redirect_uri=http://localhost:8090/api/kakaologin&response_type=code">
	<img src="${path}/resources/img/kakao_login_medium_narrow.png"/>
</a>
</body>
</html>