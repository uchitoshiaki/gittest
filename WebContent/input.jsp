<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品登録</title>
</head>
<body>
	<ul>
	<c:forEach var="message" items="${messages }">
		<li><c:out value="${message }" /></li>
	</c:forEach>
	</ul>

	<form method="post" action="./regist">
		<table>
			<tr>
				<th>商品コード</th>
				<td>
					<input type="text" name="code" value="<c:out value="${item.itemNo }" />">
					<input type="hidden" name="hiddencode" value="<c:out value="${item.itemNo }" />">
				</td>

			</tr>
			<tr>
				<th>商品名</th>
				<td><input type="text" name="name" value="<c:out value="${item.itemName }" />"></td>
			</tr>
			<tr>
				<th>商品単価</th>
				<td><input type="number" name="unitprice" value="<c:out value="${item.unitPrice}" />"></td>
			</tr>
		</table>
		<input type="submit" value="登録">
	</form>
</body>
</html>