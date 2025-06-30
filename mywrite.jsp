<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>글 작성</h1>
	<form action="myboard" method="post">
		<input type="hidden" name="command" value="write">
		<table>
			<tr>
				<th>이 름</th>
				<td><input type="text" name="myname"></td>
			</tr>
			<tr>
				<th>제 목</th>
				<td><input type="text" name="mytitle"></td>
			</tr>
			<tr>
				<th>내 용</th>
				<td><input type="text" name="mycontent"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="글 작성">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>