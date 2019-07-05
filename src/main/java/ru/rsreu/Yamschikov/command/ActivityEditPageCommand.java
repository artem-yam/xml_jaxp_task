package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Award;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Dissertation;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Event;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Patent;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.Publication;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.ScientificActivity;

public class ActivityEditPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {
		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");

		ScientificActivity activity = scientificActivityDAO
				.getActivityById(Integer.parseInt(request.getParameter("actId")));

		request.getSession().setAttribute("actName", activity.getName());

		request.setAttribute("name", activity.getName());
		request.setAttribute("description", activity.getDescription());
		request.setAttribute("date", activity.getDate());

		request.setAttribute("actId", activity.getId());

		switch (activity.getType()) {
		case DISSERTATION:

			Dissertation diss = (Dissertation) activity;
			request.setAttribute("actType", "dissertation");

			request.setAttribute("diss", "checked");
			request.setAttribute("award", "disabled");
			request.setAttribute("event", "disabled");
			request.setAttribute("patent", "disabled");
			request.setAttribute("publ", "disabled");

			request.setAttribute("organization", diss.getOrganization());

			if (diss.getSienceDegree().equals("doctor")) {
				request.setAttribute("doctor", "selected");
			} else if (diss.getSienceDegree().equals("candidate")) {
				request.setAttribute("candidate", "selected");
			}

			break;
		case AWARD:

			Award award = (Award) activity;
			request.setAttribute("actType", "award");

			request.setAttribute("diss", "disabled");
			request.setAttribute("award", "checked");
			request.setAttribute("event", "disabled");
			request.setAttribute("patent", "disabled");
			request.setAttribute("publ", "disabled");

			if (award.getAwardValue().equals("international")) {
				request.setAttribute("international", "selected");
			} else if (award.getAwardValue().equals("federal")) {
				request.setAttribute("federal", "selected");
			} else if (award.getAwardValue().equals("regional")) {
				request.setAttribute("regional", "selected");
			}

			if (award.getAwardType().equals("prize")) {
				request.setAttribute("prize", "selected");
			} else if (award.getAwardType().equals("award")) {
				request.setAttribute("award", "selected");
			} else if (award.getAwardType().equals("diploma")) {
				request.setAttribute("diploma", "selected");
			} else if (award.getAwardType().equals("medal")) {
				request.setAttribute("medal", "selected");
			}

			break;
		case EVENT:

			Event event = (Event) activity;
			request.setAttribute("actType", "event");

			request.setAttribute("diss", "disabled");
			request.setAttribute("award", "disabled");
			request.setAttribute("event", "checked");
			request.setAttribute("patent", "disabled");
			request.setAttribute("publ", "disabled");

			if (event.getEventType().equals("exhibition")) {
				request.setAttribute("exhibition", "selected");
			} else if (event.getEventType().equals("conference")) {
				request.setAttribute("conference", "selected");
			}

			request.setAttribute("location", event.getLocation());

			if (event.getLocationType().equals("international")) {
				request.setAttribute("international", "selected");
			} else if (event.getLocationType().equals("all_russian")) {
				request.setAttribute("all_russian", "selected");
			} else if (event.getLocationType().equals("interregional")) {
				request.setAttribute("interregional", "selected");
			} else if (event.getLocationType().equals("university_based")) {
				request.setAttribute("university_based", "selected");
			}

			if (event.isParticipation()) {
				request.setAttribute("yes", "checked");
			} else if (!event.isParticipation()) {
				request.setAttribute("no", "checked");
			}

			break;
		case PATENT:

			Patent patent = (Patent) activity;
			request.setAttribute("actType", "patent");

			request.setAttribute("diss", "disabled");
			request.setAttribute("award", "disabled");
			request.setAttribute("event", "disabled");
			request.setAttribute("patent", "checked");
			request.setAttribute("publ", "disabled");

			if (patent.getPatentType().equals("russian_patent")) {
				request.setAttribute("russian_patent", "selected");
			} else if (patent.getPatentType().equals("foreign_patent")) {
				request.setAttribute("foreign_patent", "selected");
			} else if (patent.getPatentType().equals("supported_patent")) {
				request.setAttribute("supported_patent", "selected");
			}

			request.setAttribute("declarant", patent.getDeclarant());

			break;
		case PUBLICATION:

			Publication publ = (Publication) activity;
			request.setAttribute("actType", "publication");

			request.setAttribute("diss", "disabled");
			request.setAttribute("award", "disabled");
			request.setAttribute("event", "disabled");
			request.setAttribute("patent", "disabled");
			request.setAttribute("publ", "checked");

			if (publ.getPublicationType().equals("monograph")) {
				request.setAttribute("monograph", "selected");
			} else if (publ.getPublicationType().equals("collection_of_scientific_papers")) {
				request.setAttribute("collection_of_scientific_papers", "selected");
			} else if (publ.getPublicationType().equals("textbook")) {
				request.setAttribute("textbook", "selected");
			} else if (publ.getPublicationType().equals("schoolbook")) {
				request.setAttribute("schoolbook", "selected");
			} else if (publ.getPublicationType().equals("article")) {
				request.setAttribute("article", "selected");
			}

			request.setAttribute("pagesCount", publ.getPagesCount());

			request.setAttribute("publisherName", publ.getPublishingOfficeName());

			if (publ.getPublisherType().equals("foreign")) {
				request.setAttribute("foreign", "selected");
			} else if (publ.getPublisherType().equals("russian")) {
				request.setAttribute("russian", "selected");
			} else if (publ.getPublisherType().equals("university")) {
				request.setAttribute("university", "selected");
			}

			break;
		default:
			break;
		}

		String page = ("path.page.user.activity.edit");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}