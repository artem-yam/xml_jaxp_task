package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.UserDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;
import ru.rsreu.Yamschikov.datalayer.data.user.UserFullName;

public class UserDeleteCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
		request.getSession().setAttribute("userAction", "user deletion");

		User deletingUser = userDAO.getUserByLogin(request.getParameter("userLogin"));
		UserFullName userName = deletingUser.getUserData().getFullName();

		request.getSession().setAttribute("deletedUser",
				userName.getLastName() + " " + userName.getFirstName() + " " + userName.getMiddleName());

		userDAO.deleteUser(request.getParameter("userLogin"));

		request.getSession().setAttribute("usersList", userDAO.getAllUsers());

		String page = ("path.page.admin");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.REDIRECT, page);
		return handler;
	}
}