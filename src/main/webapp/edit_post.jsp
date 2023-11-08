<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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

<!-- DateTimePicker Dependency -->
<link rel="stylesheet" href="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.min.css" />" />
<script type="text/javascript" src="<c:url value="/resources/datetimepicker/moment.js" />"> </script>
<script type="text/javascript" src="<c:url value="/resources/datetimepicker/bootstrap-datetimepicker.js" />"> </script>

<script>
  tinymce.init({
    selector: '#txtContent'
  });
</script>
<script type="text/javascript">
	$(function () { //Init DateTimePicker
	                $('#dtpPublishToDate').datetimepicker();
	            });
</script>
</head>
<body>
	<div class="row">
		<div class="col-sm-5 col-sm-offset-4">
			<h2>Edit Post</h2>
			<div class="alert alert-info alert-dismissable" style="display:${strDisplayMsg};">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				${strMsg}
			</div>
			<form:form action="save_post" method="post" modelAttribute="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="txtID">#</label>
					<form:input path="id" type="text" class="form-control-plaintext" id="txtID" readonly="true" value="${post.id}"></form:input>
				</div>
				<div class="form-group">
					<label for="txtTitle">Title</label>
					<form:input path="title" type="text" class="form-control" id="txtTitle" placeholder="Type a nice title ..." value="${post.title}"></form:input>
				</div>
				<div class="form-group">
					<label for="txtContent">Content</label>
					<form:textarea path="content" class="form-control" id="txtContent" placeholder="Write something ..." value="${post.content}"></form:textarea>
				</div>
				<div class="form-group">
					<label for="imgImage">Banner Image</label>
					<form:input path="file" type="file" class="form-control" id="imgImage" accept="image/*" ></form:input>
				</div>
				<div class="form-group">
					<label for="txtPublishToDate">Publish To Date</label>
					<div class="input-group date" id="dtpPublishToDate">
						<fmt:formatDate type="both" pattern="MM/dd/yyyy hh:mm a"  value="${post.publishToDate}" var="strPublishToDate" />
						<form:input path="publishToDate" type="text" class="form-control" id="txtPublishToDate" placeholder="e.g. MM/DD/YYYY HH:MM AM" value="${strPublishToDate}"></form:input>
						<span class="input-group-addon">
                        	<span class="glyphicon glyphicon-calendar"></span>
                    	</span>
					</div>
				</div>
				<c:forEach items="${statuses}" var="s" varStatus="status">
					<div class="form-check">
						<form:radiobutton path="status" name="rgStatus" id="rb${s}" value="${s}" checked="${s == post.getStatus()? 'checked' : ''}"></form:radiobutton>
						<label for="rb${s}" class="form-check-label">${s}</label>
					</div>
				</c:forEach>
				
				<a href="posts" class="btn btn-secondary">Back</a>
				<a href="delete_post?id=${post.id}" class="btn btn-danger" onclick="return confirm('Are you sure?')" style="display:${post.id > 0? '':'none'};" >Delete</a>
				<form:button type="submit" class="btn btn-primary">Save</form:button>
				<a href="view_post?id=${post.id}" class="btn btn-secondary">View</a>
				
			</form:form>
		</div>
	</div>
</body>
</html>