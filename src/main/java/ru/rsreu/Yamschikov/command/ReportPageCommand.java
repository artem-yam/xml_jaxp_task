package ru.rsreu.Yamschikov.command;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class ReportPageCommand implements ActionCommand {
	@Override
	public GoToPageHandler execute(HttpServletRequest request) {
		final String HALF_YEAR = "15552000000";

		request.getSession().removeAttribute("userAction");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date dateTo = new Date();
		String dateStr = format.format(dateTo);
		request.getSession().setAttribute("dateTo", dateStr);
		
		Date dateFrom = new Date(dateTo.getTime() - Long.parseLong(HALF_YEAR));
		dateStr = format.format(dateFrom);
		request.getSession().setAttribute("dateFrom", dateStr);

		String page = ("path.page.report");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}