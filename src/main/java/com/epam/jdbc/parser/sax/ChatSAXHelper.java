package com.epam.jdbc.parser.sax;

import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.Role;
import com.epam.jdbc.datalayer.dto.Status;
import com.epam.jdbc.datalayer.dto.User;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChatSAXHelper {
    
    private ChatSAXHandler messagesHandler = new MessagesSAXHandler();
    private ChatSAXHandler rolesHandler = new RolesSAXHandler();
    private ChatSAXHandler statusesHandler = new StatusesSAXHandler();
    private ChatSAXHandler usersHandler = new UsersSAXHandler();
    
    private List<?> parseFile(String filePath, ChatSAXHandler handler)
        throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,
            true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        
        saxParser.parse(new File(filePath), handler);
        
        return handler.getParsedObjects();
    }
    
    public List<Message> getMessages(String filePath)
        throws IOException, SAXException, ParserConfigurationException {
        return (List<Message>) parseFile(filePath, messagesHandler);
    }
    
    public List<Role> getRoles(String filePath)
        throws IOException, SAXException, ParserConfigurationException {
        return (List<Role>) parseFile(filePath, rolesHandler);
    }
    
    public List<Status> getStatuses(String filePath)
        throws IOException, SAXException, ParserConfigurationException {
        return (List<Status>) parseFile(filePath, statusesHandler);
    }
    
    public List<User> getUsers(String filePath)
        throws IOException, SAXException, ParserConfigurationException {
        return (List<User>) parseFile(filePath, usersHandler);
    }
    
}
