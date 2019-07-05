package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class LogoutCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {
		String page = ("path.page.login");

		request.getSession().invalidate();
		
		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}