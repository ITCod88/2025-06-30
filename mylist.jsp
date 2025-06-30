<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>글 목록</h1>
	<table>
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="dto" items="${list }">
			<tr>
				<td>${dto.myno }</td>
				<td>${dto.myname }</td>
				<td><a href="myboard?command=detail&myno=${dto.myno }">${dto.mytitle }</a></td>
				<td>${dto.mydate }</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4">
				<button onclick="location.href='myboard?command=writeform'">글쓰기</button>
			</td>
		</tr>
	</table>
</body>
</html>