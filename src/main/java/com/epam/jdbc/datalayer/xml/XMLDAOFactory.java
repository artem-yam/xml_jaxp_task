package com.epam.jdbc.datalayer.xml;

import com.epam.jdbc.datalayer.AbstractDAOFactory;
import com.epam.jdbc.datalayer.MessageDAO;
import com.epam.jdbc.datalayer.UserDAO;

public class XMLDAOFactory extends AbstractDAOFactory {
    
    @Override
    public MessageDAO getMessageDAO(String sourcePath) {
        return new XMLMessageDAO(sourcePath);
    }
    
    @Override
    public UserDAO getUserDAO(String sourcePath) {
        return new XMLUserDAO(sourcePath);
    }
    
}
