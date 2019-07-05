package com.epam.jdbc.datalayer;

public abstract class AbstractDAOFactory {
    
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
        "Unsupported DB type: %s";
    
    public static AbstractDAOFactory getInstance(DBType dbType)
        throws IllegalArgumentException {
        if (!DBType.XML.equals(dbType)) {
            throw new IllegalArgumentException(
                String.format(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, dbType));
        }
        return dbType.getDAOFactory();
    }
    
    public abstract MessageDAO getMessageDAO(String sourcePath);
    
    public abstract UserDAO getUserDAO(String sourcePath);
}
