package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class UserActivityPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {
		request.getSession().removeAttribute("userAction");

		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");

		User user = (User) request.getSession().getAttribute("currentUser");

		request.getSession().setAttribute("activityList", scientificActivityDAO.getUserActivity(user.getId()));

		String page = ("path.page.user.activity");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}