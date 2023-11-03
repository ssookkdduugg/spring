<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
   <style>
        body {
            margin: 0 auto;
            
        }

        .container {
            padding: 10px;
            border: 1px solid;
            width: 300px;
        }

        .header {
            height: 40px;
        }

        .row {
            height: 30px;
        }

        .title {
            width: 70px;
            float: left;
            font-weight: bold;
        }

        .input {
            float: left;
        }

        input[type='submit'] {
            font-weight: bold;
            width: 120px;
            background-color: lightgrey;
        }
    </style>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
	$(function() {
		let isIdCheck = false;
		$("#idcheck").click(function() {
			$.ajax({
				url:"${contextPath}/idcheck",
				type:"post",
				data:{id:$("#id").val()}, //{} 로 감싸져서 객체타입, id: 파라미터 이름
				success:function(res) { //컨트롤러에서 res를 받아오는데 
					console.log(res);
					if(res=="notexist") {
						isIdCheck=true;
						alert("사용 가능합니다")
					} else {
						alert("아이디가 중복됩니다")
					}
				},
				error:function(err) {
					console.log(err);
					alert("아이디가 중복체크 오류")
				}
			})
		})
		$("#id").change(function() {
			isIdCheck = false;
		})
		
		$("#form").submit(function(e) {
			if(isIdCheck==false) {
				alert("아이디 중복체크하세요");
				e.preventDefault();
			}
		})
	})
</script>    
</head>
<body>
<center>
        <div class="header">
            <h3>회원가입</h3>
        </div>
        <div class="container" id='query'>
            <form action="join" method="post" id='form'>
                <div class="row">
                    <div class="title">아이디</div>
                    <div class="input"><input type="text" name="id" id="id" required="required"></div>
                    <div><input type="button" id="idcheck" value="중복"></div>
                </div>
                <div class="row">
                    <div class="title">이름</div>
                    <div class="input"><input type="text" name="name" required="required"></div>
                </div>
                <div class="row">
                    <div class="title">비밀번호</div>
                    <div class="input"><input type="password" name="password" required="required"></div>
                </div>
                <div class="row">
                    <div class="title">이메일</div>
                    <div class="input"><input type="text" name="email"></div>
                </div>
                <div class="row">
                    <div class="title">주소</div>
                    <div class="input"><input type="text" name="address"></div>
                </div>
                <div class="button">
                    <input type="submit" value="회원가입">
                </div>
            </form>
        </div>	
</center>
</body>
</html>