package ru.rsreu.Yamschikov.command;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.UserDAO;

public class UserSearchCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");

		String searchText = "";
		try {
			searchText = new String(request.getParameter("searchText").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ошибка в смене кодировки параметра");
			e.printStackTrace();
		}

		if (searchText.isEmpty()) {
			request.getSession().removeAttribute("userAction");
			
			request.setAttribute("usersList", userDAO.getAllUsers());
		} else {
			request.getSession().setAttribute("userAction", "user search");
			request.setAttribute("searchText", searchText);

			request.setAttribute("usersList", userDAO.getSearсhedUsers(searchText));
		}

		String page = ("path.page.admin");
		
		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}