<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 부트스트랩 css, js 임포트 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%-- 컨텍스트패스 변수선언 --%>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>Home</title>
	 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>


<div class="container mt-10">
  <h2>사용자 정보 </h2>
  <table class="table">
    <thead>
      <tr>
        <th>ID</th>
        <td>${userInfo.id}</td>
      </tr>
    </thead>
    <tbody>
      <tr>
        <th>Nickname</th>
        <td>${userInfo.nickname}</td>
      </tr>
      <tr>
        <th>Name</th>
        <td>${userInfo.name}</td>
      </tr>
      <tr>
        <th>Email</th>
        <td>${userInfo.email}</td>
      </tr>
        <tr>
        <th>Mobile</th>
        <td>${userInfo.mobile}</td>
      </tr>
        <tr>
        <th>Gender</th>
        <td>${userInfo.gender}</td>
      </tr>
       <tr>
        <th>Age</th>
        <td>${userInfo.age}</td>
      </tr>
       <tr>
        <th>BirthDay</th>
        <td>${userInfo.birthday}</td>
      </tr>
    </tbody>
  </table>
</div>

</body>
</html>
