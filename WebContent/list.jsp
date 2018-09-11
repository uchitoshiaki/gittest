<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tr>
			<th>コード</th>
			<th>商品名</th>
			<th>単価</th>
		</tr>
		<c:forEach var="item" items="${list}">
		<tr>
			<td><c:out value="${item.itemNo}" /></td>
			<td><c:out value="${item.itemName}" /></td>
			<td><c:out value="${item.unitPrice}" /></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>