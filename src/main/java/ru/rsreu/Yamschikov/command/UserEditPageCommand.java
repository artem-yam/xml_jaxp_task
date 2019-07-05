package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.UserDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;

public class UserEditPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {		
		
		UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");

		User user = userDAO.getUserByLogin(request.getParameter("userLogin"));

		request.getSession().setAttribute("editingUser", request.getParameter("userLogin"));
		
		request.setAttribute("userLogin", user.getLogin());
		request.setAttribute("userPassword", user.getPassword());
		request.setAttribute("userLastName", user.getUserData().getFullName().getLastName());
		request.setAttribute("userFirstName", user.getUserData().getFullName().getFirstName());
		request.setAttribute("userMiddleName", user.getUserData().getFullName().getMiddleName());
		request.setAttribute("userPhone", user.getUserData().getPhoneNumber());
		request.setAttribute("userInfo", user.getUserData().getExtraInfo());
		request.setAttribute("userDepartment", user.getUserData().getDepartmentName());
		request.setAttribute("userDegreePost", user.getUserData().getDegreeAndPost());

		if (user.getUserType().getValue().equals("администратор")) {
			request.setAttribute("admin", "selected");
		} else if (user.getUserType().getValue().equals("сотрудник")) {
			request.setAttribute("worker", "selected");
		}

		String page = ("path.page.user.edit");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}