<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 부트스트랩 css, js 임포트 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<%-- 컨텍스트패스 변수선언 --%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 부트스트랩5 테이블 --%>
<div class="container mt-3">
	<h2>전기차 충전소 운영정보</h2>
	<table class="table table-striped">
	  <thead>
	    <tr >
	    <%-- 위도,경도는 별도의 열이 아니라 tr이 속성으로 데이터를 가지고 있게 한다 --%>
	      <th>충전소명</th>
	      <th>주소</th>
	      <th>충전기명칭</th>
	      <th>충전기타입</th>
	      <th>충전방식</th>
	      <th>충전기상태</th>
	    </tr>
	  </thead>
	  <tbody> <%-- 백엔드로부터 받은 데이터로 forEach문 작성 --%>
		<c:forEach items="${elecCharge}" var="ec"> <!-- 컨트롤러의 key랑 items랑이름 같아야함 -->
			<tr data-lat="${ec.lat} data-longi="${ec.longi}">
				<td>${ec.csNm}</td>
				<td>${ec.addr}</td>
				<td>${ec.cpNm}</td>
				<td>${ec.chargeTp==1? '완속':'급속'}</td>
				<td>
					${
						ec.cpTp==1? 'B타입(5핀)':
						ec.cpTp==2? 'C타입(5핀)':
						ec.cpTp==3? 'BC타입(5핀)':
						ec.cpTp==4? 'BC타입(7핀)':
						ec.cpTp==5? 'DC차데모':
						ec.cpTp==6? 'AB3상':
						ec.cpTp==7? 'DC콤보':
						ec.cpTp==8? 'DC차데모+DC콤보':
						ec.cpTp==9? 'DC차데모+AC3상':
						ec.cpTp==10? 'DC차데모+DC콤보':''
					 }
				</td>
				<td>
					${
						ec.cpStat==0? '상태확인불가':
						ec.cpStat==1? '충전가능':
						ec.cpStat==2? '충전중':
						ec.cpStat==3? '고장/점검':
						ec.cpStat==4? '통신장애':
						ec.cpStat==5? '통신미연결':
						ec.cpStat==9? '통신장애':''
					 }
				</td>
				
				
			</tr>
		</c:forEach>
	  </tbody>
	</table>

<%-- 부트스트랩5 페이지네이션 --%>
   <ul class="pagination justify-content-center">
      
      <c:choose>
         <c:when test="${pageInfo.curPage>1}">
            <li class="page-item"><a class="page-link" href="${path}/eleccharge/${pageInfo.curPage-1}">Previous</a></li>
         </c:when>
         <c:otherwise>
            <li class="page-item"><a class="page-link" href="#" style="pointer-events:none">Previous</a></li>
         </c:otherwise>
      </c:choose>
   
      <c:forEach begin="${pageInfo.startPage}" end="${pageInfo.endPage}" var="i">
         <c:choose>
            <c:when test="${pageInfo.curPage==i }">
               <li class="page-item active"><a class="page-link" href="${path}/eleccharge/${i}">${i}</a></li>
            </c:when>
            <c:otherwise>
               <li class="page-item"><a class="page-link" href="${path}/eleccharge/${i}">${i}</a></li>
            </c:otherwise>
         </c:choose>
      </c:forEach>
      
      <c:choose>
         <c:when test="${pageInfo.curPage<pageInfo.allPage}">
            <li class="page-item"><a class="page-link" href="${path}/eleccharge/${pageInfo.curPage+1}">Next</a></li>
         </c:when>
         <c:otherwise>
            <li class="page-item"><a class="page-link" href="#" style="pointer-events:none">Next</a></li>
         </c:otherwise>
      </c:choose>
      
   </ul>

</div>
</body>
</html>