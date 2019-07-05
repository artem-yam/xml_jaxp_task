<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Отчёты</title>
	
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
            <div class="active">
                <a href="${pageContext.request.contextPath}/FrontController?command=reportpage">
                	Формирование отчёта</a>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/FrontController?command=logout">Выйти</a>
            </div>
        </div>
	
    </div>
	
	
	<div id="content">
		
		<c:if test="${userAction eq 'small report'}">
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
		</c:if>
		
		
		
		
		
		
		<c:if test="${userAction ne 'small report'}">
		
		<form name="requestCreationForm" method="POST" 
			action="${pageContext.request.contextPath}/FrontController">
			<input type="hidden" name="command" value="smallreport" />
			
			<fieldset>
				<legend>Сформировать список НИР</legend>
		
				<select name="reportType">
  					<option value="" selected>Выбрать</option>
 					<optgroup label="Диссертации">
   						<option value="allDissertations">Все диссертации</option>				
   						<option value="doctorDissertations">Докторские</option>
   						<option value="candidateDissertations">Кандидатские</option>
   						<option value="RSREUDissertations">В РГРТУ</option>
   						<option value="otherDissertations">В иных организациях</option>	
   					</optgroup>
   					<optgroup label="Публикации">
   						<option value="allPublications">Все публикации</option>				
   						<option value="foreignPublications">Зарубежные публикации</option>
   						<option value="russianPublications">Российские публикации</option>	
   						<option  value="universityPublications">Вузовские публикации</option>
   						
   						<option value="allMonographs">Все монографии</option>
   						<option value="foreignMonographs">Зарубежные монографии</option>
   						<option value="russianMonographs">Российские монографии</option>	
   						<option value="universityMonographs">Вузовские монографии</option>
   						
   						<option value="allCollections">Все сборники научных трудов</option>
   						<option value="foreignCollections">Зарубежные сборники</option>
   						<option value="russianCollections">Российские сборники</option>	
   						<option value="universityCollections">Вузовские сборники</option>
   						
   						<option value="allTextbooks">Все учебники</option>
   						<option value="ETextbooks">Электронные учебники</option>
   						
   						<option value="allSchoolbooks">Все учебные пособия</option>
   						<option value="defMinRFSchoolbooks">С грифом Минобрнауки России</option>
   						<option value="YMOSchoolbooks">С грифом УМО</option>
   						<option value="HMСSchoolbooks">С грифом НМС</option>
   						<option value="federalSchoolbooks">С грифами других федеральных органов власти</option>
   						
   						<option value="allArticles">Все статьи</option>
   						<option value="BAKArticles">В изданиях из списка ВАК</option>
   						<option value="foreignArticles">В зарубежных изданиях</option>
   						<option value="russianArticles">В российских изданиях</option>	
   						<option value="universityArticles">В вузовских изданиях</option>	
   					</optgroup>
   					<optgroup label="Патенты">
   						<option value="allPatents">Все патенты</option>
   						<option value="RSREUPatents">Патенты от РГРТУ</option>
   						<option value="russianPatents">Патенты России</option>
   						<option value="foreignPatents">Зарубежные патенты</option>	
   						<option value="supportedPatents">Поддерживаемые патенты</option>
   					</optgroup>
   					<optgroup label="Мероприятия">
   						<option value="allEvents">Все мероприятия</option>
   						
   						<option value="allExhibitions">Все выставки</option>
   						<option value="internationalExhibitions">Международные выставки</option>
   						<option value="allRussianExhibitions">Всероссийские выставки</option>	
   						<option value="interregionalExhibitions">Межрегиональные выставки</option>
   						<option value="universityExhibitions">Выставки на базе вуза</option>
   						
   						<option value="allExhibits">Все экспонаты</option>
   						<option value="internationalExhibits">Экспонаты на международных выставках</option>
   						<option value="allRussianExhibits">Экспонаты на всероссийских выставках</option>	
   						<option value="interregionalExhibits">Экспонаты на межрегиональных выставках</option>
   						<option value="universityExhibits">Экспонаты на выставках на базе вуза</option>
   						
   						<option value="allConferences">Все конференции</option>
   						<option value="internationalConferences">Международные конференции</option>
   						<option value="allRussianConferences">Всероссийские конференции</option>	
   						<option value="interregionalConferences">Межрегиональные конференции</option>
   						<option value="universityConferences">Конференции на базе вуза</option>
   						
   						<option value="allReportsThesises">Все доклады и тезисы</option>
   						<option value="internationalReportsThesises">Доклады и тезисы на международных конференциях</option>
   						<option value="allRussianReportsThesises">Доклады и тезисы на всероссийских конференциях</option>	
   						<option value="interregionalReportsThesises">Доклады и тезисы на межрегиональных конференциях</option>
   						<option value="universityReportsThesises">Доклады и тезисы на конференциях на базе вуза</option>
   					</optgroup>
   					<optgroup label="Награды">
   						<option value="allAwards">Все награды</option>
   						<option value="internationalAwards">Награды международного уровня</option>
   						<option value="federalAwards">Награды федерального уровня</option>
   						<option value="regionalAwards">Награды регионального уровня</option>
   						<option value="prizeAwards">Премии</option>
   						<option value="diplomaAwards">Дипломы</option>
   						<option value="medalAwards">Медали</option>
   					</optgroup>
   				</select>
   				<br /><br />
   				
				<label>
					<input type="radio" name="reportTime" value="noTimeLimit" checked />
						За всё время
				</label>
				<label>
					<input type="radio" name="reportTime" value="certainPeriod"  />
						За период времени
				</label>
				<br /> <br /> 
   				Период времени (В формате : гггг-мм-дд):
				<br /> <br /> 
				От:	<input type="text" class="date" name="dateFrom" value="${dateFrom}"/> 
				До:	<input type="text" class="date" name="dateTo" value="${dateTo}"/> 
				<br /> <br />
   			
   				<input type="submit" value="Сформировать список" />
   			</fieldset>
   		</form>
   		
		
		
		
		<form name="requestCreationForm" method="POST" 
			action="${pageContext.request.contextPath}/FrontController">
			<input type="hidden" name="command" value="formreport" />
			
			
			<fieldset>
				<legend>Формирование отчёта</legend>
				Период времени (В формате : гггг-мм-дд):
				<br /> <br /> 
				От:	<input type="text" class="date" name="dateFrom" value="${dateFrom}"/> 
				До:	<input type="text" class="date" name="dateTo" value="${dateTo}"/> 
				<br /> <br />
				Учитывать НИР: <br />
				<label>
					<input type="radio" name="reportType" value="allUsers" checked />
						Всех сотрудников
				</label>
				<label>
					<input type="radio" name="reportType" value="currentUser"  />
						Только ваши
				</label>
				<input type="submit" value="Сохранить отчёт" />
			</fieldset>
		</form>
		
		</c:if>
		
		
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