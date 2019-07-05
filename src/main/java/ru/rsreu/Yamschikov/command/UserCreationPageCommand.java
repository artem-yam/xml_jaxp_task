package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class UserCreationPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {		
		String page = ("path.page.user.creation");
		
		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}