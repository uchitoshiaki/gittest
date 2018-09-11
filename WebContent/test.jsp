<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>セッションテスト</title>
</head>
<body>
	<table>
		<c:forEach var="item" items="${items }">
			<tr>
				<td><c:out value="${item.itemNo }"></c:out></td>
			</tr>
		</c:forEach>
	</table>
<!-- 	<form method="get" action="./sessiontest"> -->
	<form method="get" action="localhost:8080/sample/sessiontest">
		<input type="submit" value="商品追加" />
	</form>
</body>
</html>