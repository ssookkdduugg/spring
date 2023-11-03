<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게사판글상세</title>
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
#content {
   height: 200px;
}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script type="text/javascript">
$(function() {
   $('#heart').click(function() {
      $.ajax({
         url:'${contextPath}/like',
         type:'post',
         data:{'num':'<c:out value="${board.num}"/>'}, // cf. 백엔드에서 가져온 데이터를 자바스크립트 코드에서 사용하려할때 바로 호환이 안되는 경우가 있으므로 c:out을 통해서 아예 그 자리에 데이터 자체를 출력해넣은것
         success: function(res) { // res : @ResponseBody어노테이션을 붙인 컨트롤러boardLike가 요청을 받아서 리턴한 데이터
            console.log(res);
            if(res==="true") {
               $("#heart").attr("src","${contextPath}/resources/img/redheart.png")
               $("#likecount").text(+$("#likecount").text()+1);
            } else {
               $("#heart").attr("src","${contextPath}/resources/img/blackheart.png")
               $("#likecount").text(+$("#likecount").text()-1);
            }
            // $("#likecount").text(res.likecount);
         },
         error:function(err) {
            console.log(err);
         }
      })
   })
})   

</script>
</head>
<body>
   <section id="./writeForm">
      <h2>게시판글상세</h2>
         <table border="1">
            <tr>
               <td class="td_left"><label for="writer">글쓴이</label></td>
               <td class="td_right">
               <%-- <input type="text" name="writer" id="writer" readonly="readonly" value="${board.writer}"/></td> --%>
               <span>${board.writer}</span></td>
            </tr>
            <tr>
               <td class="td_left"><label for="subject">제목</label></td>
               <td class="td_right">
               <%-- <input name="subject" type="text" id="subject" readonly="readonly" value="${board.subject}"/></td> --%>
               <span>${board.subject}</span>
               </td>
            </tr>
            <tr>
               <td class="td_left"><label for="content">내용</label></td>
               <%-- <td><textarea id="content" name="content" readonly="readonly" cols="40" rows="15">${board.content}</textarea></td> --%>
               <td><div id="content">${board.content}</div></td>
            </tr>
            <tr>
               <td class="td_left"><label>이미지</label></td>
               <td>
               <c:choose>
                  <c:when test="${board.fileurl ne null}">
                     <img src="${contextPath}/image/${board.fileurl}" width="100px" height="100px"/>
                  </c:when>
                  <c:otherwise>
                     <img src="${contextPath}/resources/img/images.png" width="100px" height="100px"/>      
                  </c:otherwise>
               </c:choose>
               </td>
            </tr>
            
         </table>
         <section id="commandCell">
            <c:if test="${user.id eq board.writer }">
            <a href="${contextPath}/boardmodify?num=${board.num}">수정</a>&nbsp;&nbsp;
            </c:if>
            <a href="${contextPath}/boardlist">목록</a>&nbsp;&nbsp;
            좋아요(<span id="likecount">${board.likecount}</span>)&nbsp;&nbsp;
            <c:if test="${user ne Empty}">
               <c:choose>
                  <c:when test="${select == true}">
                     <img id="heart" src="${contextPath}/resources/img/redheart.png" width="20px" height="20px"/>
                  </c:when>
                  <c:otherwise>
                     <img id="heart" src="${contextPath}/resources/img/blackheart.png" width="20px" height="20px"/>
                  </c:otherwise>
               </c:choose>
            </c:if>
         </section>
   </section>
   
   
   <%--
   cf. servlet-context.xml 참고 

   <resources mapping="/resources/**" location="/resources/" />
   <resources mapping="/resources/img/**" location="/resources/img/" />
   <!-- 이렇게 url매핑되어있는 요청에 대한 접근을 허용하는것
      원래 모든 url요청은 매핑된 컨트롤러를 통해서만 요청이 처리되어 리소스에 접근되는것이 원칙이나,
      이렇게 servlet-context.xml에 지정해놓은 URI는 일종의 공유폴더와 같이 컨트롤러를 통하지 않은 접근을 허용한다는 것 -->
    --%>
   
</body>
</html>