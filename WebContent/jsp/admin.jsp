<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Панель администратора</title>
	<style>
		<%@include file="/css/style.css" %>
	</style>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
	
	<div id="main">
	<div id="header">
	
		<div> </div>
        
        <div id="subHeader">
		
			<div id="search">
				<form method="post" 
					action="${pageContext.request.contextPath}/FrontController">
					<input type="hidden" name="command" value="usersearch" /> 
        			<input id="searchText" type="text" name="searchText"
        				placeholder="Введите искомое название или ключевые слова" />
            		<input type="submit" id="search_but" value="" />
    			</form>
    		</div>
    			
    	</div>
    	
    </div>
	
	
	<div id="content">
		<c:if test="${userAction eq 'user deletion'}">
			<hr />
			Пользователь <b>${deletedUser}</b> был удален!	
			<hr />
		</c:if>
			
		<c:if test="${userAction eq 'user edit'}">
			<hr />
			Информация пользователя <b>${editedUser}</b> успешно изменена!	
			<hr />
		</c:if>
		
		<c:if test="${userAction eq 'user create'}">
			<hr />
			Пользователь <b>${createdUser}</b> успешно создан!	
			<hr />
		</c:if>
		
		<c:if test="${userAction eq 'user search'}">
			<hr />
			Ищем <b>${searchText}</b>!	
			<hr />
		</c:if>
		
		
		
		<c:forEach var="user" items="${usersList}" >
			<div class="list_item">
				<div>
					ФИО: <b>${user.getUserData().getFullName().getLastName()}
						 ${user.getUserData().getFullName().getFirstName()}
						 ${user.getUserData().getFullName().getMiddleName()}</b><br /> 
					Логин: <b>${user.getLogin()}</b><br /> 
					Тип пользователя: <b>${user.getUserType().getValue()}</b> <br />
					Статус :
					<c:if test="${user.getStatus() eq 'online'}">
						<span class="greenSpan">	
							${user.getStatus()}
						</span> 
					</c:if>
					<c:if test="${user.getStatus() eq 'offline'}">
						<span class="redSpan">	
							${user.getStatus()}
						</span> 
					</c:if>
					
					<br />	
				</div>
				<div class="edit_buttons">
					<form name="" method="POST" 
						action="${pageContext.request.contextPath}/FrontController" 
						id="editForm${user.getLogin()}">
						<input type="hidden" name="command" value="usereditpage" />
						<input type="hidden" name="userLogin" value="${user.getLogin()}" />
						<div class="">
							<a href="#" onclick="document.getElementById('editForm${user.getLogin()}').submit()"> 
								Редактировать 
							</a>
						</div>
					</form>
					
					<c:if test="${user.getLogin() ne currentUser.getLogin()}">
						<div class="">
							<form name="" method="POST" 
							action="${pageContext.request.contextPath}/FrontController" 
							id="deleteForm${user.getLogin()}">
								<input type="hidden" name="command" value="userdelete" />
								<input type="hidden" name="userLogin" value="${user.getLogin()}" />
								<a href="#" onclick="if (confirm('Подтвердите удаление')) {document.getElementById('deleteForm${user.getLogin()}').submit() }">
									Удалить 
								</a>
							</form>
						</div>	
					</c:if>
					
				</div>
			</div>
			<hr />
			
		</c:forEach>

		
		
		<div class="footer" id="admin">
			<div>
				<a href="${pageContext.request.contextPath}/FrontController?command=mainpage"> Выход из панели админа </a>
			</div>
			<div>
				<a href="${pageContext.request.contextPath}/FrontController?command=usercreationpage"> Создать нового пользователя </a>
			</div>
			<div>
				<a href="${pageContext.request.contextPath}/FrontController?command=logout"> Выйти </a>
			</div>
		</div>
		
		
		
	</div>
	</div>
</body>
</html>