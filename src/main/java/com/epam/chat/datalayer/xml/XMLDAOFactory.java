package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.AbstractDAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;

public class XMLDAOFactory extends AbstractDAOFactory {
    
    public static String sourcePath;
    
    @Override
    public MessageDAO getMessageDAO(String sourcePath) {
        return new XMLMessageDAO(sourcePath);
    }
    
    @Override
    public UserDAO getUserDAO(String sourcePath) {
        return new XMLUserDAO(sourcePath);
    }
    
}
