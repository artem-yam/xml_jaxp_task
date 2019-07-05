package ru.rsreu.Yamschikov.command;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class ActivityCreationStep2Command implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		String name = "";
		String description = "";
		String date = "";
		try {
			name = new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
			description = new String(request.getParameter("description").getBytes("ISO-8859-1"), "UTF-8");
			date = new String(request.getParameter("date").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ошибка в смене кодировки параметра");
			e.printStackTrace();
		}

		request.getSession().setAttribute("actName", name);
		request.getSession().setAttribute("actDescription", description);
		request.getSession().setAttribute("actDate", date);
		request.getSession().setAttribute("actType", request.getParameter("actType"));


		String page = ("path.page.activity.creation.2");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}