<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>

<style>
.open-button,.btn-cancel {
	background-color: #555;
	color: white;
	padding: 10px 15px;
	border: none;
	cursor: pointer;
	opacity: 0.8;
	position: relative;
	left: 45%;
	width: 150px;
}
.form-popup {
	display: none;
	position: relative;
	right: 15px;
	border: 3px solid #f1f1f1;
	z-index: 9;
}

.table-container,.form-container {
	background-color: white;
}
</style>

</head>

<body>

	<br> ${userInfo}
	<br>

	<div align="justify">
		Name : ${user.getName()} <br>

	</div>

	<h3 align="center">Groups</h3>

	<div align="center">

		<table class="table_container" border="1">
			<thead>
				<tr>
					<td width=150 align="center">Group Name</td>
					<td width=130 align="center">Group Type</td>
					<td width=130 align="center">Balance</td>
				<tr>
			</thead>
			<c:forEach var="group" items="${group_list}">
				<tr>
					<td align="center"><a
						href="viewGroup?groupLinkId=${group.getId()}">${group.getGroup().getName()}</a></td>
					<td align="center">${group.getGroup().getType()}</td>
					<td align="center">${group.getBalance()}</td>
				</tr>

			</c:forEach>
		</table>
	</div>
	<br>
	<button class="open-button" onclick="openForm()">Show Expense</button>

	<div class="form-popup" id="expSheet" align="center">
	
		<table class="table-container" border="1">
			<thead>
				<tr>
					<td width=120 align="center">Amount</td>
					<td width=150 align="center">Date</td>
				<tr>
			</thead>
			<c:forEach var="expense" items="${user.getExpense_list()}">
				<tr>
					<td align="center">${expense.getAmount()}</td>
					<td align="center">${expense.getDate()}</td>
				</tr>
			</c:forEach>
		</table>
			<button type="button" class="btn-cancel" onclick="closeForm()">Close</button>
	</div>
	
	<br>
	<br>
	
	<button class="open-button" onclick="openGroup()">Add Group</button>
	
	<div class="form-popup" id="addGroup" align="center">
		
		<form:form action="add-Group" class="form-container" modelAttribute="group" method="POST">
			
			<label>Name :  </label>
			<form:input path="name" placeholder="Enter Gruop Name "/><br>
			
			<label>Type :  </label>
			<form:input path="type" placeholder="Enter Group Type "/><br>
			
			<input type="submit" value="submit">
			<button type="button" class="btn-cancel" onclick="closeGroup()">Close</button>
		</form:form>
		
	</div>
	
	
	
	
	<script>
		function openForm() {
			document.getElementById("expSheet").style.display = "block";
		}

		function closeForm() {
			document.getElementById("expSheet").style.display = "none";
		}
		function openGroup() {
			document.getElementById("addGroup").style.display = "block";
		}

		function closeGroup() {
			document.getElementById("addGroup").style.display = "none";
		}
	</script>


</body>
</html>