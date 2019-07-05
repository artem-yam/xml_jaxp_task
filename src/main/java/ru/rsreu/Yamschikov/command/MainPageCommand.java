package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;

public class MainPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {
		request.getSession().removeAttribute("userAction");

		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");

		request.getSession().setAttribute("activityList", scientificActivityDAO.getAllActivities());

		String page = ("path.page.main");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}