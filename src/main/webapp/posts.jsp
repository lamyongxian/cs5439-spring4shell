<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
	<div class="row">
		<div class="col-sm-4 col-sm-offset-4 text-center">
			<div class="row">
				<div class="col-sm-1 text-left">
					<a href="${pageContext.request.contextPath}/logout" onclick="return confirm('Are you sure?')" class="btn btn-danger">Logout &#9746;</a>
				</div>
				<div class="col-sm text-right">
					<a href="${pageContext.request.contextPath}/users" style="display:${strDisplayUsers};" class="btn btn-default">Users &rarr;</a>
				</div>
			</div>
			<h2>Posts</h2>
			<table class="table">
				<tr>
					<th>ID</th>
					<th>Title</th>
					<th>Status</th>
				</tr>
				<c:forEach items="${posts}" var="p">
					<tr>
						<td>${p.id}</td>
						<td>${p.title}</td>
						<td>${p.status}</td>
						<td><a href="${pageContext.request.contextPath}/edit_post?id=${p.id}">EDIT</a></td>
					</tr>
				</c:forEach>
			</table>
			<a href="${pageContext.request.contextPath}/new_post" class="btn btn-primary">Add New</a>
		</div>
	</div>
</body>
</html>
