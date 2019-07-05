<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<html>
<head>
	<title>Изменение пользователя</title>
	<style>
		<%@ include file="/css/style.css" %>	
	</style>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>


	<div id="main">
		<div id="header">

			<div></div>

		</div>


		<div id="content">
			<form name="userCreationForm" method="POST"
				action="${pageContext.request.contextPath}/FrontController">
				<fieldset>
					<legend>
						Редактирование пользователя <i>
							${userLastName}
							${userFirstName}
							${userMiddleName}</i>
					</legend>

					<input type="hidden" name="command" value="usereditcompleted" />

					Логин : <br /> <input type="text" class="textField"
						name="userLogin" value="${userLogin}" required /> <br /> Пароль :
					<br /> <input type="text" class="textField" name="userPassword"
						value="${userPassword}" required /> <br />
					<fieldset>
						<legend>ФИО</legend>
						Фамилия : <br /> <input type="text" class="textField"
							name="lastName" value="${userLastName}" required /> <br /> Имя :
						<br /> <input type="text" class="textField" name="firstName"
							value="${userFirstName}" required /> <br /> Отчество : <br /> <input
							type="text" class="textField" name="middleName"
							value="${userMiddleName}" /> <br />
					</fieldset>

					Тип пользователя : <select name="userType">
						<option value="administrator" ${admin}>администратор</option>
						<option value="worker" ${worker}>сотрудник</option>
					</select> <br /> Номер телефона :<br /> <input type="text"
						class="textField" name="phoneNumber" value="${userPhone}" /> <br />

					Дополнительная информация :<br />
					<textarea cols="100" name="description">${userInfo}	
				</textarea>
					<br /> Кафедра :<br /> <input type="text" class="textField"
						name="userDepartment" value="${userDepartment}" required /> <br />

					Должность / Ученая степень :<br /> <input type="text"
						class="textField" name="userDegreePost" value="${userDegreePost}"
						required /> <br />


				</fieldset>
				<br /> <input type="submit" value="Сохранить изменения" />
			</form>

			<div class="footer">
				<div>
					<a
						href="${pageContext.request.contextPath}/FrontController?command=adminpage">
						К списку пользователей </a>
				</div>
				<div>
					<a
						href="${pageContext.request.contextPath}/FrontController?command=logout">
						Выйти </a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>