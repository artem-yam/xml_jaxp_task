<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Изменение научной деятельности</title>
	
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
		<form name="requestCreationForm" method="POST" 
			action="${pageContext.request.contextPath}/FrontController">
			<input type="hidden" name="command" value="activityeditcompleted" />
			<input type="hidden" name="actId" value="${actId}" />
			<fieldset>
				<legend>Редактирование научной деятельности</legend>
				
				Наименование :<br /> 
				<input type="text" class="textField" name="name" value="${name}" required /> <br />
				Описание :<br />
				<textarea cols="100" name="description">${description}
				</textarea> <br />
				Дата выполнения (В формате : гггг-мм-дд):<br /> 
				<input type="text" class="date" name="date" value="${date}"/> <br />
				
				Тип деятельности :<br />
				<label>
					<input type="radio" name="actType" value="publication"  ${publ} />
					Публикация
				</label>
				<label>
					<input type="radio" name="actType" value="dissertation"  ${diss} />
					Диссертация
				</label>
				<label>
					<input type="radio" name="actType" value="patent" ${patent} />
					Патент
				</label>
				<label>
					<input type="radio" name="actType" value="event" ${event} />
					Мероприятие
				</label>
				<label>
					<input type="radio" name="actType" value="award" ${award} />
					Награда
				</label> <br />
				
				
				
				
				
				
				<c:choose>
    				<c:when test="${actType=='publication'}">
      					
      				Тип публикации : 
      				<select name="publicationType" required>
						<option value="monograph" ${monograph}>
						монография
						</option>
						<option value="collection_of_scientific_papers" ${collection_of_scientific_papers}>
						сборник научный трудов
						</option>
						<option value="textbook" ${textbook}>
						учебник
						</option>
						<option value="schoolbook" ${schoolbook}>
						учебное пособие
						</option>
						<option value="article" ${article}>
						статья
						</option>	
					</select> <br />
      					 
      				Количество страниц : <br />
					<input type="number" min="1" value="${pagesCount}" name="pagesCount" required /> <br />	 
      					 
      				Название издательства :<br /> 
					<input type="text" class="textField" name="publisherName" value="${publisherName}" required /> <br />	 
      					 
      				Тип издательства : 
      				<select name="publisherType" required>
						<option value="foreign" ${foreign}>
						зарубежное
						</option>
						<option value="russian" ${russian}>
						российское
						</option>
						<option value="university" ${university}>
						вузовское
						</option>	
					</select> <br />	 
    				</c:when>
    				<c:when test="${actType=='dissertation'}">
    				
    					Организация :<br /> 
						<input type="text" class="textField" name="organization" value="${organization}" required /> <br />	 
      					 
      					Ученая степень : 
      					<select name="degree" required>
							<option value="doctor" ${doctor}>
							доктор наук
							</option>
							<option value="candidate" ${candidate}>
							кандидат наук
							</option>	
						</select> <br />	
    				
    				</c:when>
    				<c:when test="${actType=='patent'}">
    				  
      					Тип патента : 
      					<select name="patentType" required>
							<option value="russian_patent" ${russian_patent}>
							патент России
							</option>
							<option value="foreign_patent" ${foreign_patent}>
							зарубежный патент
							</option>
							<option value="supported_patent" ${supported_patent}>
							поддерживаемый патент
							</option>		
						</select> <br />	
    				
    				    Заявитель :<br /> 
						<input type="text" class="textField" name="declarant" value="${declarant}" required /> <br />	

    				</c:when>
    				<c:when test="${actType=='event'}">
    				
    					Тип мероприятия : 
      					<select name="eventType" required>
							<option value="exhibition" ${exhibition}>
							выставка
							</option>
							<option value="conference" ${conference}>
							конференция
							</option>	
						</select> <br />
    				
    					Место проведения :<br /> 
						<input type="text" class="textField" name="location" value="${location}" required /> <br />
    				
    					Разновидность по месту проведения : 
      					<select name="eventLocationType" required>
							<option value="international" ${international}>
							международное
							</option>
							<option value="all_russian" ${all_russian}>
							всероссийское
							</option>	
							<option value="interregional" ${interregional}>
							межрегиональное
							</option>
							<option value="university_based" ${university_based}>
							на базе вуза
							</option>		
						</select> <br />
					
    					Ваше участие (представлены экспонаты или опбликованы тезисы) :<br />
						<label>
							<input type="radio" name="participation" value="1" ${yes} />
							Да
						</label>
						<label>
							<input type="radio" name="participation" value="0" ${no} />
							Нет
						</label> <br />
    				
    				</c:when>
    				<c:when test="${actType=='award'}">
    				
    					Ценность награды : 
      					<select name="awardValue" required>
							<option value="international" ${international}>
							международный уровень
							</option>
							<option value="federal" ${federal}>
							федеральный уровень
							</option>	
							<option value="regional" ${regional}>
							региональный уровень
							</option>		
						</select> <br />
						
						Тип награды : 
      					<select name="awardType" required>
							<option value="prize" ${prize}>
							премия
							</option>
							<option value="award" ${award}>
							награда
							</option>	
							<option value="diploma" ${diploma}>
							диплом
							</option>	
							<option value="medal" ${medal}>
							медаль
							</option>		
						</select> <br />
    				
    				</c:when>
    				<c:otherwise>
   					</c:otherwise>
				</c:choose>
				
				
			
			</fieldset>
			<br />
			<input type="submit" value="Сохранить изменения" />
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