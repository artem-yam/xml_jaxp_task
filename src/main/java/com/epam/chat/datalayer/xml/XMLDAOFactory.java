package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.AbstractDAOFactory;
import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.UserDAO;

public class XMLDAOFactory extends AbstractDAOFactory {

    public static final String DEFAULT_SOURCE_XML_PATH =
            "src/main/resources/chat.xml";

    @Override
    public MessageDAO getMessageDAO() {
        return new XMLMessageDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new XMLUserDAO();
    }

}
