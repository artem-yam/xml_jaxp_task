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
				<legend>
					Заполнение данных
					<c:choose>
    					<c:when test="${actType=='publication'}">
      					 новой публикации
    					</c:when>
    					<c:when test="${actType=='dissertation'}">
      					 новой диссертации
    					</c:when>
    					 <c:when test="${actType=='patent'}">
      					 нового патента
    					</c:when>
    					<c:when test="${actType=='event'}">
      					 нового мероприятия
    					</c:when>
    					<c:when test="${actType=='award'}">
      					 новой награды
    					</c:when>
    					
   					 	<c:otherwise>
   						 </c:otherwise>
				</c:choose>
				</legend>
				<input type="hidden" name="command" value="activitycreationcompleted" />
				
				
				<c:choose>
    				<c:when test="${actType=='publication'}">
      					
      				Тип публикации : 
      				<select name="publicationType" required>
						<option value="monograph" selected>
						монография
						</option>
						<option value="collection_of_scientific_papers">
						сборник научный трудов
						</option>
						<option value="textbook">
						учебник
						</option>
						<option value="schoolbook">
						учебное пособие
						</option>
						<option value="article">
						статья
						</option>	
					</select> <br />
      					 
      				Количество страниц : <br />
					<input type="number" min="1" value="1" name="pagesCount" required /> <br />	 
      					 
      				Название издательства :<br /> 
					<input type="text" class="textField" name="publisherName" required /> <br />	 
      					 
      				Тип издательства : 
      				<select name="publisherType" required>
						<option value="foreign" selected>
						зарубежное издательство
						</option>
						<option value="russian">
						российское издательство
						</option>
						<option value="university">
						вузовское издательство
						</option>	
					</select> <br />	 
    				</c:when>
    				<c:when test="${actType=='dissertation'}">
    				
    					Организация :<br /> 
						<input type="text" class="textField" name="organization" required /> <br />	 
      					 
      					Ученая степень : 
      					<select name="degree" required>
							<option value="doctor" selected>
							доктор наук
							</option>
							<option value="candidate">
							кандидат наук
							</option>	
						</select> <br />	
    				
    				</c:when>
    				<c:when test="${actType=='patent'}">
    				  
      					Тип патента : 
      					<select name="patentType" required>
							<option value="russian_patent" selected>
							патент России
							</option>
							<option value="foreign_patent">
							зарубежный патент
							</option>
							<option value="supported_patent">
							поддерживаемый патент
							</option>		
						</select> <br />	
    				
    				    Заявитель :<br /> 
						<input type="text" class="textField" name="declarant" required /> <br />	

    				</c:when>
    				<c:when test="${actType=='event'}">
    				
    					Тип мероприятия : 
      					<select name="eventType" required>
							<option value="exhibition" selected>
							выставка
							</option>
							<option value="conference">
							конференция
							</option>	
						</select> <br />
    				
    					Место проведения :<br /> 
						<input type="text" class="textField" name="location" required /> <br />
    				
    					Разновидность по месту проведения : 
      					<select name="eventLocationType" required>
							<option value="international" selected>
							международное
							</option>
							<option value="all_russian">
							всероссийское
							</option>	
							<option value="interregional">
							межрегиональное
							</option>
							<option value="university_based">
							на базе вуза
							</option>		
						</select> <br />
					
    					Ваше участие (представлены экспонаты или опбликованы тезисы) :<br />
						<label>
							<input type="radio" name="participation" value="1" checked />
							Да
						</label>
						<label>
							<input type="radio" name="participation" value="0" />
							Нет
						</label> <br />
    				
    				</c:when>
    				<c:when test="${actType=='award'}">
    				
    					Ценность награды : 
      					<select name="awardValue" required>
							<option value="international" selected>
							международный уровень
							</option>
							<option value="federal">
							федеральный уровень
							</option>	
							<option value="regional">
							региональный уровень
							</option>		
						</select> <br />
						
						Тип награды : 
      					<select name="awardType" required>
							<option value="prize" selected>
							премия
							</option>
							<option value="award">
							награда
							</option>	
							<option value="diploma">
							диплом
							</option>	
							<option value="medal">
							медаль
							</option>		
						</select> <br />
    				
    				</c:when>
    				<c:otherwise>
   					</c:otherwise>
				</c:choose>
				
				
			</fieldset>
			<br />
			<input type="submit" value="Добавить" />
		</form>
		
		<div class="footer">
			<c:if test="${currentUser.getUserType().getValue() eq 'администратор'}">
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