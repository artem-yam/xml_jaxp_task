<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Добавление научной деятельности</title>
	<style>
		<%@include file="/css/style.css" %>
	</style>
</head>
<body>



	<div id="main">
	<div id="header">
	
		<div id="menu">
        	<div>
            	<a href="${pageContext.request.contextPath}/FrontController?command=mainpage">
            		Вся научная деятельность</a>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/FrontController?command=useractivitypage">
                	Ваша научная деятельность</a>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/FrontController?command=reportpage">
                	Формирование отчёта</a>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/FrontController?command=logout">Выйти</a>
            </div>
        </div>
    	
    </div>
	
	
	<div id="content">
		<form name="vacancyCreationForm" method="POST" 
			action="${pageContext.request.contextPath}/FrontController">
			<fieldset>
				<legend>Добавление новой научной деятельности</legend>
				<input type="hidden" name="command" value="activitycreationstep2" />
				Наименование :<br /> 
				<input type="text" class="textField" name="name" required /> <br />
				Описание :<br />
				<textarea cols="100" name="description">
				</textarea> <br />
				Дата выполнения (В формате : гггг-мм-дд):<br /> 
				<input type="text" class="date" name="date" value="${date}"/> <br />
				
				Тип деятельности :<br />
				<label>
					<input type="radio" name="actType" value="publication" checked />
					Публикация
				</label>
				<label>
					<input type="radio" name="actType" value="dissertation"  />
					Диссертация
				</label>
				<label>
					<input type="radio" name="actType" value="patent" />
					Патент
				</label>
				<label>
					<input type="radio" name="actType" value="event" />
					Мероприятие
				</label>
				<label>
					<input type="radio" name="actType" value="award" />
					Награда
				</label> <br />
				
			</fieldset>
			<br />
			<input type="submit" value="Продолжить заполнение данных" />
		</form>
		
		<div class="footer">
			<c:if test="${currentUser.getUserType().getValue()=='администратор'}">
				<div>
					<a href="${pageContext.request.contextPath}/FrontController?command=adminpage"> 
						Панель админа </a>
				</div>
			</c:if>


		</div>
	</div>
	</div>
</body>
</html>