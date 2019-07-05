<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
	<title>Создание пользователя</title>
	<style>
		<%@include file="/css/style.css" %>
	</style>
</head>
<body>


	<div id="main">
	<div id="header">
	
	<div> </div>
    	
    </div>
	
	
	<div id="content">
		<form name="userCreationForm" method="POST" 
			action="${pageContext.request.contextPath}/FrontController">
			<fieldset>
				<legend>Создание нового пользователя</legend>
				<input type="hidden" name="command" value="usercreationcompleted" />

				Логин :<br /> 
				<input type="text" class="textField" name="userLogin" required /> <br />
				Пароль :<br /> 
				<input type="text" class="textField" name="userPassword" required /> <br />
				<fieldset>
					<legend>ФИО</legend>
					Фамилия :<br /> 
					<input type="text" class="textField" name="lastName" required /> <br />
					Имя :<br /> 
					<input type="text" class="textField" name="firstName" required /> <br />
					Отчество :<br /> 
					<input type="text" class="textField" name="middleName"/> <br />
				</fieldset>
				
				Тип пользователя :
				<select name="userType">
					<option value="administrator">
						администратор
					</option>
					<option value="worker" selected>
						сотрудник
					</option>
				</select> <br />
				
				Номер телефона :<br /> 
				<input type="text" class="textField" name="phoneNumber" /> <br />
				
				Дополнительная информация :<br />
				<textarea cols="100" name="description" required>
				</textarea> <br />
				
				Кафедра :<br /> 
				<input type="text" class="textField" name="userDepartment" required /> <br />
				
				Должность / Ученая степень :<br /> 
				<input type="text" class="textField" name="userDegreePost" required /> <br />
				
		
			</fieldset>
			<br />
			<input type="submit" value="Добавить пользователя" />
		</form>
		
		<div class="footer">
			<div>
				<a href="${pageContext.request.contextPath}/FrontController?command=adminpage"> К списку пользователей </a>
			</div>
			<div>
				<a href="${pageContext.request.contextPath}/FrontController?command=logout"> Выйти </a>
			</div>
		</div>
	</div>
	</div>
</body>
</html>