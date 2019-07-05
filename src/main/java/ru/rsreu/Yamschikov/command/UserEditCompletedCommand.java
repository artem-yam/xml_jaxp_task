package ru.rsreu.Yamschikov.command;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;
import ru.rsreu.Yamschikov.datalayer.UserDAO;
import ru.rsreu.Yamschikov.datalayer.data.user.User;
import ru.rsreu.Yamschikov.datalayer.data.user.UserFullName;
import ru.rsreu.Yamschikov.datalayer.data.user.UserPersonalData;

public class UserEditCompletedCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		UserDAO userDAO = (UserDAO) request.getServletContext().getAttribute("userDAO");
		request.getSession().setAttribute("userAction", "user edit");

		User user = userDAO.getUserByLogin((String) request.getSession().getAttribute("editingUser"));

		request.getSession().setAttribute("editedUser",
				user.getUserData().getFullName().getLastName() + " " + user.getUserData().getFullName().getFirstName()
						+ " " + user.getUserData().getFullName().getMiddleName());

		String userLogin = "";
		String userPassword = "";
		String lastName = "";
		String firstName = "";
		String middleName = "";
		String phoneNumber = "";
		String description = "";
		String userDepartment = "";
		String userDegreePost = "";
		try {
			lastName = new String(request.getParameter("lastName").getBytes("ISO-8859-1"), "UTF-8");
			firstName = new String(request.getParameter("firstName").getBytes("ISO-8859-1"), "UTF-8");
			middleName = new String(request.getParameter("middleName").getBytes("ISO-8859-1"), "UTF-8");
			phoneNumber = new String(request.getParameter("phoneNumber").getBytes("ISO-8859-1"), "UTF-8");
			description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			userDepartment = new String(request.getParameter("userDepartment").getBytes("ISO-8859-1"), "UTF-8");
			userDegreePost = new String(request.getParameter("userDegreePost").getBytes("ISO-8859-1"), "UTF-8");
			userLogin = new String(request.getParameter("userLogin").getBytes("ISO-8859-1"), "UTF-8");
			userPassword = new String(request.getParameter("userPassword").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ошибка в смене кодировки параметра");
			e.printStackTrace();
		}

		UserFullName userName = new UserFullName(lastName, firstName, middleName);

		UserPersonalData userData = new UserPersonalData(userName, phoneNumber, description, userDepartment,
				userDegreePost);

		String userType = "";
		switch ((String) request.getParameter("userType")) {
		case "administrator":
			userType = "администратор";
			break;
		case "worker":
			userType = "сотрудник";
			break;
		default:
			break;
		}

		userDAO.editUser((String) request.getSession().getAttribute("editingUser"), userLogin, userPassword, userData,
				userType);

		request.getSession().setAttribute("usersList", userDAO.getAllUsers());

		String page = ("path.page.admin");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.REDIRECT, page);
		return handler;
	}
}