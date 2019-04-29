package com.epam.chat.datalayer;

public abstract class AbstractDAOFactory {
    
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
        "Unsupported DB type: %s";
    
    public static AbstractDAOFactory getInstance(DBType dbType)
        throws IllegalArgumentException {
        if (!dbType.equals(DBType.XML)) {
            throw new IllegalArgumentException(
                String.format(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, dbType));
        }
        return dbType.getDAOFactory();
    }
    
    public abstract MessageDAO getMessageDAO(String sourcePath);
    
    public abstract UserDAO getUserDAO(String sourcePath);
}
