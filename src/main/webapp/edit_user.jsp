<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js" integrity="sha384-xBuQ/xzmlsLoJpyjoggmTEz8OWUFM0/RC5BsqQBDX2v5cMvDHcMakNTNrHIW2I5f" crossorigin="anonymous"></script>

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<script src='https://cloud.tinymce.com/stable/tinymce.min.js'></script>

<!-- Toggle Switch Dependency -->
<link rel="stylesheet" href="<c:url value="/resources/w3school/toggle.css" />" />

<script>
  tinymce.init({
    selector: '#txtContent'
  });
</script>
</head>
<body>
	<div class="row">
		<div class="col-sm-5 col-sm-offset-4">
			<h2>Edit User</h2>
			<div class="alert alert-info alert-dismissable" style="display:${strDisplayMsg};">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				${strMsg}
			</div>
			<form:form action="save_user" method="post" modelAttribute="user" enctype="multipart/form-data">
				<div class="form-group">
					<label for="txtID">#</label>
					<form:input path="id" type="text" class="form-control-plaintext" id="txtID" readonly="true" value="${user.id}"></form:input>
				</div>
				<div class="form-group">
					<label for="txtTitle">Username</label>
					<form:input path="name" type="text" class="form-control" id="txtName" placeholder="Enter your name" required="required" value="${user.name}"></form:input>
				</div>
				<div class="form-group">
					<label for="txtEmail">Email</label>
					<form:input path="email" type="text" class="form-control" id="txtEmail" placeholder="e.g. user@company.com" required="required" value="${user.email}"></form:input>
					<form:errors path="email" style="color:#f32c12;" />
				</div>
				<div class="form-group">
					<label for="txtPassword">Password</label>
					<form:input path="password" type="password" class="form-control" id="txtPassword" placeholder="${user.id > 0 ? '(No Change)' : 'Set your password'}" value="${user.password}"></form:input>
					<form:errors path="password" style="color:#f32c12;" />
				</div>
				<c:forEach items="${roles}" var="r" varStatus="rs">
					<div class="form-check">
						<form:radiobutton path="role" name="rgRole" id="rb${r}" value="${r}" checked="${r == user.getRole() ? 'checked' : ''}"></form:radiobutton>
						<label for="rb${r}" class="form-check-label">${r}</label>
					</div>
				</c:forEach>
				<label for="cbActive">Is Active</label>
				<div class="form-group">
					<label class="switch">
					  <form:checkbox path="active" id="cbActive" checked="${user.isActive() ? 'checked' : ''}"></form:checkbox>
					  <span id="spActive" class="slider"></span>
					</label>
					<script type="text/javascript">
						$("#cbActive").before().insertBefore("#spActive");
						//Hack to fix non-toggling caused by extra hiddenfield
					</script>
				</div>
				<a href="users" class="btn btn-secondary">Back</a>
				<a href="delete_user?id=${user.id}" class="btn btn-danger" onclick="return confirm('Are you sure?')" style="display:${user.id > 0? '':'none'};" >Delete</a>
				<form:button type="submit" class="btn btn-primary">Save</form:button>
				
			</form:form>
		</div>
	</div>
</body>
</html>