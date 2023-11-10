<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Styling -->
<link rel="stylesheet" href="<c:url value="/resources/style.css" />">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"  integrity="sha384-xBuQ/xzmlsLoJpyjoggmTEz8OWUFM0/RC5BsqQBDX2v5cMvDHcMakNTNrHIW2I5f" crossorigin="anonymous"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
	<div class="row">
		<div class="col-sm-4 col-sm-offset-4 text-center">
			<img class="logo-width" src="<c:url value="/resources/crm.png" />" />
			<h2>Hello! This is a secure Java system.</h2>
			<div class="alert alert-danger alert-dismissable ${strDisplayMsg}">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				${strMsg}
			</div>
			<form:form action="loginB2" method="post" modelAttribute="login" >

				<div class="input-group">
				<form:input type="text" id="username" path="username" class="form-control" placeholder="Username"></form:input>
				<form:input type="password" id="password" path="password" class="form-control" placeholder="Password"></form:input>
				<input type="submit" id="submit" class="btn btn-primary form-control" value="Login" />

				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
