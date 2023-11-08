<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<%-- 컨텍스트패스 변수선언 --%>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 로그인</title>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
<%-- Documents>네이버로그인>API명세>1.naverlogin.jsp --%>
<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=GeaPAv7X8Q58pPjEI9oE&redirect_uri=http://localhost:8090/api/naverlogin&state=9898">
  <!-- //원래 a태그누르면 바로 페이지로 갔었다면 여기서는 localhost:8090/api/naverlogin을써서 naverlogin 함수를 또 부른다 : 콜백함수
  근데 콜백함수는 함수안에 매개변수로 또 함수를써도 그것도 콜백함수 또 함수를 불러서 요청하는거!  -->
   <img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/>
</a>

</body>
</html>