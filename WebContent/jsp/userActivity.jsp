<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Ваша научная деятельность</title>
	
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
            <div  class="active">
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
					<input type="hidden" name="command" value="useractivitysearch" /> 
        			<input id="searchText" type="text" name="searchText"
        				placeholder="Введите искомое название или ключевые слова" />
            		<input type="button" id="search_but" />
    			</form>
    		</div>
    			    	
    			
    	</div>
    	
    </div>
	
	
	<div id="content">
		
		<br />
		<c:if test="${userAction eq 'user activity search'}">
			<br />
			<hr />
			Ищем <b>${searchText}</b>!	
		</c:if>	
		
		<c:if test="${userAction eq 'activity edit'}">
			<br />
			<hr />
			<b>${actName}</b> успешно изменена!	
		</c:if>	
		
		<hr />
		<c:forEach var="act" items="${activityList}" >
		<div class="list_item">
			<div class="userActivity">
				<b>${act.getName()}</b> <br /> 
				Дата : ${act.getDate()} <br />
				Тип: ${act.getType().getValue()} <br />


			</div>
			
			
			<div class="edit_buttons">
    			
    			<form name="" method="POST" 
    				action="${pageContext.request.contextPath}/FrontController" 
    				id="activityReview${act.getId()}">
					<input type="hidden" name="command" value="activityreviewpage" />
					<input type="hidden" name="actId" value="${act.getId()}" />
					<div> 
						<a href="#" onclick="document.getElementById('activityReview${act.getId()}').submit()"> 
							Подробности 
						</a>
    				</div>
    			</form>
    			
				<form name="" method="POST" 
					action="${pageContext.request.contextPath}/FrontController" 
					id="activityEdit${act.getId()}">
					<input type="hidden" name="command" value="activityeditpage" />
					<input type="hidden" name="actId" value="${act.getId()}" />
					<div> 
						<a href="#" onclick="document.getElementById('activityEdit${act.getId()}').submit()"> 
							Редактировать 
						</a>
    				</div>
    			</form>
				
				<form name="" method="POST" 
					action="${pageContext.request.contextPath}/FrontController" 
					id="activityDelete${act.getId()}">
					<input type="hidden" name="command" value="activitydelete" />
					<input type="hidden" name="actId" value="${act.getId()}" />
					<div> 
						<a href="#" onclick="if (confirm('Подтвердите удаление')) {document.getElementById('activityDelete${act.getId()}').submit()}"> 
							Удалить 
						</a>
    				</div>
    			</form>
			
			
			</div>
			
		</div>
		<hr />
		</c:forEach>
		
		
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
	</div>
</body>
</html>