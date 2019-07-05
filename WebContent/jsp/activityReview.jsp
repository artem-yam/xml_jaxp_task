<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Просмотр научной деятельности</title>
	
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
		<form name="activityReviewForm" method="POST" 
			action="${pageContext.request.contextPath}/FrontController">
		<fieldset>
			<legend>Информация о научной деятельности</legend>
				
				Наименование :<br /> 
				<input type="text" class="textField" name="name" value="${name}" readonly /> <br />				
				Сотрудник : ${owner} <br /><br />
				Описание :<br />
				<textarea cols="100" name="description" readonly>${description}
				</textarea> <br />
				Дата выполнения (В формате : гггг-мм-дд):<br /> 
				<input type="text" class="date" name="date" value="${date}" readonly /> <br />
				
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
      				<select name="publicationType" disabled>
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
					<input type="number" min="1" value="${pagesCount}" name="pagesCount" readonly /> <br />	 
      					 
      				Название издательства :<br /> 
					<input type="text" class="textField" name="publisherName" value="${publisherName}" readonly /> <br />	 
      					 
      				Тип издательства : 
      				<select name="publisherType" disabled>
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
						<input type="text" class="textField" name="organization" value="${organization}" readonly /> <br />	 
      					 
      					Ученая степень : 
      					<select name="degree" disabled>
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
      					<select name="patentType" disabled>
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
						<input type="text" class="textField" name="declarant" value="${declarant}" readonly /> <br />	

    				</c:when>
    				<c:when test="${actType=='event'}">
    				
    					Тип мероприятия : 
      					<select name="eventType" disabled>
							<option value="exhibition" ${exhibition}>
							выставка
							</option>
							<option value="conference" ${conference}>
							конференция
							</option>	
						</select> <br />
    				
    					Место проведения :<br /> 
						<input type="text" class="textField" name="location" value="${location}" readonly /> <br />
    				
    					Разновидность по месту проведения : 
      					<select name="eventLocationType" disabled>
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
      					<select name="awardValue" disabled>
							<option value="international" ${international}>
							международная
							</option>
							<option value="federal" ${federal}>
							федеральная
							</option>	
							<option value="regional" ${regional}>
							региональная
							</option>		
						</select> <br />
						
						Тип награды : 
      					<select name="awardType" disabled>
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
		</form>
		
		
		
		<c:if test="${owner.getId()==currentUser.getId()}">
			<div class="buttons">
			
				<form name="" method="POST" 
					action="${pageContext.request.contextPath}/FrontController" 
					id="activityEdit${actId}">
					<input type="hidden" name="command" value="activityeditpage" />
					<input type="hidden" name="actId" value="${actId}" />
					<div class="actBut"> 
						<a href="#" onclick="document.getElementById('activityEdit${actId}').submit()"> 
							Редактировать 
						</a>
    				</div>
    			</form>

				<form name="" method="POST" 
					action="${pageContext.request.contextPath}/FrontController" 
					id="activityDelete${actId}">
					<input type="hidden" name="command" value="activitydelete" />
					<input type="hidden" name="actId" value="${actId}" />
					<div class="actBut"> 
						<a href="#" onclick="if (confirm('Подтвердите удаление')) {document.getElementById('activityDelete${actId}').submit()}"> 
							Удалить 
						</a>
    				</div>
    			</form>
		
			
			</div>
		</c:if>
		<br />
		
		
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