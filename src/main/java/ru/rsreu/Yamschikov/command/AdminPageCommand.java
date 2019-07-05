package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.UserDAO;

public class AdminPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {
		request.getSession().removeAttribute("userAction");
		
		UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
		
		request.getSession().setAttribute("usersList", userDAO.getAllUsers());
		
		String page = ("path.page.admin");
		
		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}