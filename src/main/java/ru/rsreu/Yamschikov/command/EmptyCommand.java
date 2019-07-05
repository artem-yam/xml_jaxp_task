package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class EmptyCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		String page = ("path.page.login");
		
		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}