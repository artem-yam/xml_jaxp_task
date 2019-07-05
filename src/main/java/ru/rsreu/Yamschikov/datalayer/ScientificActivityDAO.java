package ru.rsreu.Yamschikov.datalayer;

import java.util.List;

import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.ScientificActivity;

public interface ScientificActivityDAO {
	List<ScientificActivity> getAllActivities();

	List<ScientificActivity> getUserActivity(int userId);

	List<ScientificActivity> getSearсhedActivity(int userId, String searchText);

	ScientificActivity getActivityById(int actId);

	void createActivity(ScientificActivity activity);

	void editActivity(ScientificActivity activity);

	int getNextId();

	void deleteActivity(int actId);

	List<ScientificActivity> getReportActivities(String dateFrom, String dateTo, int userId);
	
	List<ScientificActivity> getSmallReportActivities(String reportType, String dateFrom, String dateTo);
}
