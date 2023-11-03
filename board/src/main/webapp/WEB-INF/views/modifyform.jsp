<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게사판글수정</title>
<style type="text/css">
h2 {
	text-align: center;
}
table {
	margin: auto;
	width: 450px;
}
.td_left {
	width: 150px;
	background: orange;
}
.td_right {
	width: 300px;
	background: skyblue;
}
#commandCell {
	text-align: center;
}
</style>

<script>
	window.onload = function() {
		const fileDom = document.querySelector('#file');
		const imageBox = document.querySelector('#image-box');
	
		fileDom.addEventListener('change', ()=> {
			const imageSrc = URL.createObjectURL(fileDom.files[0]); // 파일데이터를 만들어서
			imageBox.src = imageSrc; // id="image-box"인 img의 src에 넣어준다
		})
	}
</script>

</head>
<body>
	<section id="./writeForm">
		<h2>게사판글수정</h2>
		<form action="boardmodify" method="post" enctype="multipart/form-data">
		<input type="hidden" name="num" value="${board.num}"/>
		<input type="hidden" name="fileurl" value="${board.fileurl}"/>
			<table border="1">
				<tr>
					<td class="td_left"><label for="writer">글쓴이</label></td>
					<td class="td_right"><input type="text" name="writer"
						id="writer" readonly="readonly" value="${board.writer}"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="subject">제목</label></td>
					<td class="td_right"><input name="subject" type="text"
						id="subject" value="${board.subject}"/></td>
				</tr>
				<tr>
					<td class="td_left"><label for="content">내용</label></td>
					<td><textarea id="content" name="content"
							cols="40" rows="15" >${board.content}</textarea></td>
				</tr>
				<tr>
					<td class="td_left"><label for="file">이미지 파일 첨부</label></td>
					<td class="td_right">
					
					<c:choose>
						<c:when test="${board.fileurl ne null}">
							<img src="${contextPath}/image/${board.fileurl}" id="image-box" width="100px" height="100px"/>
						</c:when>
						<c:otherwise>
							<img src="${contextPath}/resources/img/images.png" id="image-box" width="100px" height="100px"/>		
						</c:otherwise>
					</c:choose>
					<input name="file" type="file" id="file" accept="image/*"/>
					
					</td>
				</tr>
			
			</table>
			<section id="commandCell">
				<input type="submit" value="수정">&nbsp;&nbsp;
				<a href="boardlist">목록</a>
			</section>
		</form>
	</section>
</body>
</html>