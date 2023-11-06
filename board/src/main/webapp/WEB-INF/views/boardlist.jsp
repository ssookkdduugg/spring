<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%-- /board를 의미한다. @PathVariable을 이용한 Restful형태로 요청하게되면, 그로 인한 예외가 발생하기 때문에 아예 루트부터 시작하는 URI요청이 되도록 변수에 저장. 

cf. aciton 또는 href 또는 src에서 url요청할때에 "URI"를 적은 것은 "./URI"와 같고 "/URI"와는 다르다.
"URI"요청의 경우, 요청이 이루어진 페이지의 현재 url의 첫번째 '/' 뒤로 URI가 붙는다.
기존의 쿼리스트링으로 파라미터 데이터를 넘기며 요청하는 방식이었을때는 포트번호 뒤로 ContextRoot(/board) 뒤로 붙는 /URI가 바뀌게 되어 "URI"요청으로 인한 문제가 발생할 여지가 없었다.
하지만 @PathVariable을 통해 '/'로 파라미터 데이터를 넘기며 요청하는 방식으로 변경하게되면서 마치 URI가 여러개 붙는 것처럼 인식되어서, 
이전 페이지의 url이 {포트번호}/{컨텍스트루트}/{URI}/{파라미터} 였을 경우에 그 다음에 요청할때는 /{파라미터} 부분이 /URI로 갈아끼워지면서 문제가 발생한다.
따라서 c:set으로 "/board" 즉 "/{컨텍스트루트}"에 해당하는 변수를 선언하여 "/{컨텍스트루트}/URI"로 요청되도록 한다.
이것은 "URI"=="./URI"와 달리 '/'로 시작하는 요청이기 때문에 요청한 (이전)페이지의 요청시점의 url가 어떤 모습인지와 상관없이 포트번호 뒤로 붙게되어 "{포트번호}/{컨텍스트루트}/{URI}"의 정상적인 요청이 이뤄진다.
(상대경로x 절대경로o 요청과 비슷하게 이해)
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판목록</title>
<style type="text/css">
h3,h5 { text-align: center; }
table { margin: auto; width: 800px }
#tr_top { background: orange; text-align: center; }
#emptyArea { margin: auto; width: 800px; text-align: center; }
#emptyArea a {
   display: inline-block;
   width: 20px;
   height: 20px;
   text-decoration: none;
}
#emptyArea .btn {
   background: lightgray;
}
#emptyArea .select {
   background: lightblue;
}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script>

   // 페이지번호 a태그의 기본 제출 동작을 가로챈 callBtn함수)
   function callBtn(num) {
       var type = $("#type").val();
       var keyword = $("#keyword").val();
       
       // 정상 검색 수행시
       if(type!='all' && keyword!=null && keyword.trim()!='') {
          $('#page').val(num);
          $('#searchform').submit();
          
          return false;
       }   
       
    }



</script>
</head>
<body>

<h3>글 목록 &nbsp;&nbsp;
<c:if test="${user ne Empty }">
   <a href="${contextPath}/boardwrite">글쓰기</a>
</c:if>

</h3>

<form action="${contextPath}/boardsearch" method="post" id="searchform">         
   <input type="hidden" name="page" id="page" value="1">
<h5>
   <select name="type" id="type">
      <option value="all">선택</option>
      <option value="subject" ${type eq 'subject'? 'selected':'' }>제목</option>
      <option value="writer" ${type eq 'writer'? 'selected':'' }>작성자</option>
      <option value="content" ${type eq 'content'? 'selected':'' }>내용</option>
   </select>
   <input type="text" name="keyword" id="keyword" value="${keyword }"/>
   <input type="submit" value="검색"/>
</h5>
</form>               
      <table>
         <tr id="tr_top"><th>번호</th><th>제목</th><th>작성자</th><th>날짜</th><th>조회수</th><th>삭제</th></tr>
         <c:forEach items="${boardList }" var="board">
            <tr>
               <td>${board.num }</td>
               <%-- <td><a href="boarddetail?num=${board.num}">${board.subject }</a></td> --%>
               <td><a href="${contextPath}/boarddetail/${board.num}/${pageInfo.curPage}">${board.subject }</a></td>
               <td>${board.writer }</td>
               <td>${board.writedate  }</td>
               <td>${board.viewcount  }</td>
               <td>
               <c:if test="${user.id == board.writer }">
                  <%-- <a href="boarddelete?num=${board.num }&page=${pageInfo.curPage}">삭제</a> --%>
                  <%-- <a href="boarddelete/${board.num }/${pageInfo.curPage}">삭제</a> --%>
                  <a href="${contextPath}/boarddelete/${board.num }/${pageInfo.curPage}">삭제</a>
               </c:if>
               </td>
            </tr>
         </c:forEach>
      </table>
      <div id="emptyArea">
         <c:choose>  
            <c:when test="${pageInfo.curPage>1}">
               <a href="${contextPath}/boardlist?page=${pageInfo.curPage-1}" onclick="return callBtn(${pageInfo.curPage-1});">&lt;</a>
            </c:when>
            <c:otherwise>
               &lt;
            </c:otherwise>
         </c:choose>&nbsp;

         <c:forEach begin="${pageInfo.startPage}" end="${pageInfo.endPage}" var="i">
            <c:choose>
               <c:when test="${pageInfo.curPage==i}">
                  <%-- 페이지번호 클릭시 일단 callBtn함수가 가로챈다. if문에 들어오면(정상검색 수행되는 경우라면) callBtn은 false를 반환하고, 그로인해 a태그의 href로의 이동을 막아지며 검색form이 제출된다 --%>
                  <a href="${contextPath}/boardlist?page=${i}" class="select" onclick="return callBtn(${i});">${i}</a>&nbsp;
               </c:when>
               <c:otherwise>
                  <a href="${contextPath}/boardlist?page=${i}" class="btn" onclick="return callBtn(${i});">${i}</a>&nbsp;
               </c:otherwise>
               
            </c:choose>
            
         </c:forEach>

         <c:choose>  
            <c:when test="${pageInfo.curPage<pageInfo.allPage}">
               <a href="${contextPath}/boardlist?page=${pageInfo.curPage+1}" onclick="return callBtn(${pageInfo.curPage+1});">&gt;</a>
            </c:when>
            <c:otherwise>
               &gt;
            </c:otherwise>
         </c:choose>
         &nbsp;&nbsp;
      </div>
</body>
</html>