package com.epam.chat.datalayer;

public abstract class AbstractDAOFactory {

    public static AbstractDAOFactory getInstance(DBType dbType) {
        AbstractDAOFactory result = dbType.getDAOFactory();
        return result;
    }

    public abstract MessageDAO getMessageDAO();

    public abstract UserDAO getUserDAO();
}
