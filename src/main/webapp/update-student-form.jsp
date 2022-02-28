<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Add Student</title>
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/add-student-style.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<body>
		<div class="container">
		<header>
			<h1 class="title-table">FooBar University</h1>
		</header>
		
		<div class="content">
			<h3>Add Student</h3>
			
			<c:url var="backLink" value="StudentController">
				<c:param name="command" value="LIST"></c:param>
			</c:url>
			<form action="StudentController" method="get">
				<input type="hidden" name="command" value="UPDATE" />
				<input type="hidden" name="id" value="${THE_STUDENT.id}"/>
				<table>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" value="${THE_STUDENT.firstName}"/></td>
					</tr>
					
					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" value="${THE_STUDENT.lastName}" /></td>
					</tr>
					
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" value="${THE_STUDENT.email}"/></td>
					</tr>
					
					<tr>
						<td></td>
						<td><input type="submit" value="Update" class="button-submit"/></td>
					</tr>
				</table>
			</form>
			
			<a href="${backLink}">Back to list</a>
		</div>
	</div>
</body>
</html>