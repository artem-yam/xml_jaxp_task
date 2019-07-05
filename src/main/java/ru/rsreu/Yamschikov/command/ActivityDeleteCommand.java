package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;
import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class ActivityDeleteCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");

		User user = (User) request.getSession().getAttribute("currentUser");

		scientificActivityDAO.deleteActivity(Integer.parseInt(request.getParameter("actId")));

		request.getSession().setAttribute("activityList", scientificActivityDAO.getUserActivity(user.getId()));

		String page = ("path.page.user.activity");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.REDIRECT, page);
		return handler;
	}
}