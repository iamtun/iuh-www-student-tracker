<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Student Tracker App</title>
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<header>
			<h1 class="title-table">FooBar University</h1>
		</header>
		
		<div class="content">
			<input type="button" value="Add Student"
					onclick="window.location.href='add-student-form.jsp'; return false;"
					class="add-student-button"
			/>
			
			<table class="table table-striped">
				  <thead>
				    <tr>
				      <th scope="col">First Name</th>
				      <th scope="col">Last Name</th>
				      <th scope="col">Email</th>
				      <th scope="col">Image</th>
				      <th scope="col">Action</th>
				    </tr>
				  </thead>
				  
				  <c:forEach var="tempStudent" items="${STUDENT_LIST}">
				  	
				  	<c:url var="tempLink" value="StudentController">
				  		<c:param name="command" value="LOAD"/>
				  		<c:param name="studentId" value="${tempStudent.id}"/>
				  	</c:url>
				  	
				  	<c:url var="deleteLink" value="StudentController">
				  		<c:param name="command" value="DELETE"/>
				  		<c:param name="studentId" value="${tempStudent.id}"/>
				  	</c:url>
				  	
				  	<tr>
				  		<td>${tempStudent.firstName}</td>
				  		<td>${tempStudent.lastName}</td>
				  		<td>${tempStudent.email}</td>
				  		<td><img src="uploads/${tempStudent.image}" alt="picture" width="120" height="150"></td>
				  		<td>
				  			<a href="${tempLink}">UPDATE</a> |
				  			<a href="${deleteLink}"
				  			onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false;">DELETE</a>
				  		</td>
				  	</tr>
				  	
				  </c:forEach>
			</table>
		</div>
	</div>
</body>
</html>