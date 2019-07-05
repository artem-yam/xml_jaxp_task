package ru.rsreu.Yamschikov.command;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.GoToPageHandler;
import ru.rsreu.Yamschikov.GoToPageMethodEnum;

public class SmallReportCommand implements ActionCommand {

	@Override
	public GoToPageHandler execute(HttpServletRequest request) {

		ScientificActivityDAO scientificActivityDAO = (ScientificActivityDAO) request.getServletContext()
				.getAttribute("scientificActivityDAO");
		
		request.getSession().setAttribute("userAction", "small report");

		String dateFrom = "";
		String dateTo = "";

		if (request.getParameter("reportTime").equals("certainPeriod")) {
			dateFrom = request.getParameter("dateFrom");
			dateTo = request.getParameter("dateTo");
		}

		request.getSession().setAttribute("activityList",
				scientificActivityDAO.getSmallReportActivities(request.getParameter("reportType"), dateFrom, dateTo));

		String page = ("path.page.report");

		GoToPageHandler handler = new GoToPageHandler(GoToPageMethodEnum.FORWARD, page);
		return handler;
	}
}