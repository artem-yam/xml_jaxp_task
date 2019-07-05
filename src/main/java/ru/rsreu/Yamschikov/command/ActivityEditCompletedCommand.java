package ru.rsreu.Yamschikov.command;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Award;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.AwardTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.AwardValueEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Dissertation;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.DissertationSienceDegreeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Event;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.EventLocationTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.EventTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Patent;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.PatentTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Publication;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.PublicationPublisherTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.PublicationTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.ScientificActivityTypeEnum;
import ru.rsreu.Yamschikov.datalayer.data.user.User;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.ScientificActivity;
import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class ActivityEditCompletedCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");

		request.getSession().setAttribute("userAction", "activity edit");

		String name = "";
		String description = "";
		String dateStr = "";
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
			description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			dateStr = new String(request.getParameter("date").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ошибка в смене кодировки параметра");
			e.printStackTrace();
		}

		String typeStr = request.getParameter("actType");
		int actId = Integer.parseInt(request.getParameter("actId"));
		ScientificActivityTypeEnum type = ScientificActivityTypeEnum.valueOf(typeStr.toUpperCase());

		User user = (User) request.getSession().getAttribute("currentUser");

		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ScientificActivity activity = null;

		switch (typeStr) {
		case "dissertation":

			String organization = "";
			try {
				organization = new String(request.getParameter("organization").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Ошибка в смене кодировки параметра");
				e.printStackTrace();
			}

			DissertationSienceDegreeEnum degree = DissertationSienceDegreeEnum
					.valueOf(request.getParameter("degree").toUpperCase());

			Dissertation diss = new Dissertation(actId, name, user, type, date, description, 0, organization, degree);
			activity = (ScientificActivity) diss;

			break;
		case "event":

			EventTypeEnum eventType = EventTypeEnum.valueOf(request.getParameter("eventType").toUpperCase());
			String location = "";
			try {
				location = new String(request.getParameter("location").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Ошибка в смене кодировки параметра");
				e.printStackTrace();
			}

			EventLocationTypeEnum locationType = EventLocationTypeEnum
					.valueOf(request.getParameter("eventLocationType").toUpperCase());

			boolean participation = false;

			if (request.getParameter("participation").equals("1")) {
				participation = true;
			} else {
				participation = false;
			}

			Event event = new Event(actId, name, user, type, date, description, 0, eventType, location, locationType,
					participation);
			activity = (ScientificActivity) event;

			break;
		case "patent":

			PatentTypeEnum patentType = PatentTypeEnum.valueOf(request.getParameter("patentType").toUpperCase());

			String declarant = "";
			try {
				declarant = new String(request.getParameter("declarant").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Ошибка в смене кодировки параметра");
				e.printStackTrace();
			}

			Patent patent = new Patent(actId, name, user, type, date, description, 0, patentType, declarant);
			activity = (ScientificActivity) patent;

			break;
		case "publication":

			PublicationTypeEnum publicationType = PublicationTypeEnum
					.valueOf(request.getParameter("publicationType").toUpperCase());
			int pagesCount = Integer.parseInt(request.getParameter("pagesCount"));
			String publisherName = "";
			try {
				publisherName = new String(request.getParameter("publisherName").getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Ошибка в смене кодировки параметра");
				e.printStackTrace();
			}

			PublicationPublisherTypeEnum publisherType = PublicationPublisherTypeEnum
					.valueOf(request.getParameter("publisherType").toUpperCase());

			Publication publication = new Publication(actId, name, user, type, date, description, 0, publicationType,
					pagesCount, publisherName, publisherType);
			activity = (ScientificActivity) publication;

			break;
		case "award":
			AwardTypeEnum awardType = AwardTypeEnum.valueOf(request.getParameter("awardType").toUpperCase());
			AwardValueEnum awardValue = AwardValueEnum.valueOf(request.getParameter("awardValue").toUpperCase());

			Award award = new Award(actId, name, user, type, date, description, 0, awardType, awardValue);
			activity = (ScientificActivity) award;

			break;
		default:
			break;
		}

		scientificActivityDAO.editActivity(activity);
		
		
		request.getSession().setAttribute("activityList", scientificActivityDAO.getUserActivity(user.getId()));

		String page = ("path.page.user.activity");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.REDIRECT, page);
		return handler;
	}
}