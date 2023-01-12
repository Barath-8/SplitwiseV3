<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Group Info</title>

<style>
.open-button {
	background-color: #555;
	color: white;
	padding: 10px 15px;
	border: none;
	cursor: pointer;
	opacity: 0.8;
	position: absolute;
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

.btn, .btn-cancel {
	background-color: #955;
	color: white;
	padding: 10px 15px;
	border: none;
	cursor: pointer;
	position: relative;
	opacity: 0.8;
}

.form-container {
	max-width: 300px;
	padding: 10px;
	background-color: white;
}

form-container .btn:hover, .open-button:hover {
	opacity: 1;
}
</style>

</head>
<body>

	
	<div align="center">
		<h2>Group Name : ${group.getName()}</h2>

		<table border="1">
			<thead>
				<tr>
					<td width=150 align="center">Name</td>
					<td width=150 align="center">borrowed</td>
					<td width=150 align="center">lent</td>
				<tr>
			</thead>
			<c:forEach var="member" items="${members}">
				<tr>
					<td align="center">${member.getUser().getName()}</td>
					
					<c:forEach var="expense" items="${group.getGroupExpense()}" >
						<c:if test="${expense.getSender().equals(user)}">
							<c:if test="${expense.getReciever().equals(member.getUser())}">
							<td align = "center">${expense.getAmount()}</td>
							</c:if>
						</c:if>
						
					</c:forEach>
					
					<c:forEach var="expense" items="${group.getGroupExpense()}" >
						
						<c:if test="${expense.getReciever().equals(user)}">
							<c:if test="${expense.getSender().equals(member.getUser())}">							
							<td align = "center">${ expense.getAmount()}</td>
							</c:if>
						</c:if>
					
					</c:forEach>
					
				</tr>
			</c:forEach>
		</table>

	</div>


	<button class="open-button" onclick="openForm()">Add Expense</button>

	<div class="form-popup" id="expForm" align="center">
		<form action="add-expense" class="form-container" method="post">
			<h1>Add Expense</h1>

			<label for="desc"><b>Description</b></label> <input type="text"
				placeholder="Enter Description" name="description" required><br>

			<label for="amt"><b>Amount</b></label> <input type="number"
				placeholder="Enter amount" name="amount" required><br>
			<br>

			<button type="submit" class="btn">Add</button>
			<button type="button" class="btn-cancel" onclick="closeForm()">Close</button>
		</form>
	</div>
	<br>
	<br>
	<button class="open-button" onclick="openMember()">Add Member</button>
	
	<div class="form-popup" id="addMember" align="center">
		
		<form:form action="add-member" class="form-container" modelAttribute="newMember" method="POST">
			
			<label>Email :  </label>
			<form:input path="email" placeholder="Enter Member Email  "/><br>
			<br>
			
			<input class="btn-cancel" type="submit" value="submit">
			<button type="button" class="btn-cancel" onclick="closeMember()">Close</button>
		</form:form>
		
	</div>


	<script>
		function openForm() {
			document.getElementById("expForm").style.display = "block";
		}

		function closeForm() {
			document.getElementById("expForm").style.display = "none";
		}
		function openMember() {
			document.getElementById("addMember").style.display = "block";
		}

		function closeMember() {
			document.getElementById("addMember").style.display = "none";
		}
	</script>



</body>
</html>