package ru.rsreu.Yamschikov.datalayer;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DBType dbType) {
		DAOFactory result = dbType.getDAOFactory();
		return result;
	}

	public abstract UserDAO getUserDAO();

	public abstract ScientificActivityDAO getScientificActivityDAO();
}
