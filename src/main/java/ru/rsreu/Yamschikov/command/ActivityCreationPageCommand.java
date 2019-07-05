package ru.rsreu.Yamschikov.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class ActivityCreationPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		String page = ("path.page.activity.creation");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(new Date());

		request.setAttribute("date", dateStr);

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}