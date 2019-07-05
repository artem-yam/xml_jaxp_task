package ru.rsreu.Yamschikov.command;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class UserActivitySearchCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");

		User user = (User) request.getSession().getAttribute("currentUser");

		String searchText = "";
		try {
			searchText = new String(request.getParameter("searchText").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ошибка в смене кодировки параметра");
			e.printStackTrace();
		}

		if (searchText.isEmpty()) {
			request.getSession().removeAttribute("userAction");

			request.getSession().setAttribute("activityList", scientificActivityDAO.getUserActivity(user.getId()));
		} else {
			request.getSession().setAttribute("userAction", "activity search");
			request.setAttribute("searchText", searchText);

			request.getSession().setAttribute("activityList",
					scientificActivityDAO.getSearсhedActivity(user.getId(), searchText));
		}

		String page = ("path.page.user.activity");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}