<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<c:choose>
    	<c:when test="${userAction eq 'login'}">
			<title>Добро пожаловать</title>
    	</c:when>    
   	 	<c:otherwise>
       		 <title>Вся научная деятельность</title>
    	</c:otherwise>
	</c:choose>


	<style>
		<%@include file="/css/style.css" %>
	</style>
	
</head>
<body>
	
	<div id="main">
	<div id="header">
	
		<div id="menu">
        	<div  class="active">
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
		
		<div id="subHeader">
		
			<div id="search">
				<form method="get" 
					action="${pageContext.request.contextPath}/FrontController">
					<input type="hidden" name="command" value="activitysearch" /> 
        			<input id="searchText" type="text" name="searchText"
        				placeholder="Введите искомое название или ключевые слова" />
            		<input type="submit" id="search_but" value="" />
    			</form>
    		</div>
    	</div>
    	
    </div>
	
	
	<div id="content">
		<c:if test="${userAction eq 'login'}">
			<h4>Добро пожаловать</h4>
			<hr />
			Здравствуйте, <b>${currentUser.getUserData().getFullName().getLastName()}
						 ${currentUser.getUserData().getFullName().getFirstName()}
						 ${currentUser.getUserData().getFullName().getMiddleName()}</b> !	
			<hr />
		</c:if>
		
		<c:if test="${userAction eq 'activity search'}">
			<br />
			<hr />
			Ищем <b>${searchText}</b>!	
			<hr />
		</c:if>
		
		<c:if test="${userAction eq 'activity create'}">
			<br />
			<hr />
			Научная деятельность <b>${actName}</b> добавлена!	
			<hr />
		</c:if>


		<c:forEach var="act" items="${activityList}" >
			<div class="activity">
				<form name="" method="POST" 
					action="${pageContext.request.contextPath}/FrontController" 
					id="actForm${act.getId()}">
					<input type="hidden" name="command" value="activityreviewpage" />
					<input type="hidden" name="actId" value="${act.getId()}" />
					<a href="#" onclick="document.getElementById('actForm${act.getId()}').submit()">
						${act.getName()}
					</a><br />
					Сотрудник : ${act.getOwner()} <br />
					Дата : ${act.getDate()} <br />
					Тип: ${act.getType().getValue()} <br />
					
					
				</form>
			</div>
			<hr />
		</c:forEach>
		
	
	</div>
	
	
	<div class="footer">
		<c:if test="${currentUser.getUserType().getValue() eq 'администратор'}">
			<div>
				<a href="${pageContext.request.contextPath}/FrontController?command=adminpage"> 
						Панель админа </a>
			</div>
		</c:if>
			
		<div>
			<a href="${pageContext.request.contextPath}/FrontController?command=activitycreationpage"> 
					Добавить новую научную деятельность </a>
		</div>
	</div>
	
	
	</div>
</body>
</html>