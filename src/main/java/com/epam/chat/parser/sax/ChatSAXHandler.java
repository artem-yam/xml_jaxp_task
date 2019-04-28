package com.epam.chat.parser.sax;

import com.epam.chat.datalayer.dto.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatSAXHandler extends DefaultHandler {
    
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'kk:mm:ss";
    
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_USER_ELEMENT = "user_from";
    private static final String MESSAGE_DATE_ELEMENT = "time_stamp";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    
    private static final String STATUS_MAIN_ELEMENT = "Status";
    private static final String ROLE_MAIN_ELEMENT = "Role";
    private static final String TITLE_ELEMENT = "title";
    private static final String DESCRIPTION_ELEMENT = "description";
    
    private static final String USER_MAIN_ELEMENT = "User";
    private static final String USER_NICK_ELEMENT = "nick";
    private static final String USER_ROLE_ELEMENT = "role";
    
    private String currentElementName;
    private List<Message> messages;
    private List<Status> statuses;
    private List<Role> roles;
    private List<User> users;
    
    private String userNick;
    private Date timeStamp;
    private String messageText;
    private String roleOrStatusTitle;
    private String roleOrStatusDescription;
    
    public List<Role> getRoles() {
        return roles;
    }
    
    public List<Message> getMessages() {
        return messages;
    }
    
    public List<Status> getStatuses() {
        return statuses;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void parseFile(String filePath)
        throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,
            true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        
        saxParser.parse(new File(filePath), this);
    }
    
    @Override
    public void startDocument() throws SAXException {
        messages = new ArrayList<>();
        statuses = new ArrayList<>();
        roles = new ArrayList<>();
        users = new ArrayList<>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
        Attributes attributes) throws SAXException {
        currentElementName = qName;
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        String information = new String(ch, start, length);
        information = information.replace("\n", "").trim();
        
        if (!information.isEmpty()) {
            switch (currentElementName) {
                case MESSAGE_USER_ELEMENT:
                    userNick = information;
                    break;
                case MESSAGE_DATE_ELEMENT:
                    try {
                        timeStamp = new SimpleDateFormat(DATE_FORMAT_PATTERN)
                                        .parse(information);
                    } catch (ParseException parseException) {
                        throw new SAXException(parseException);
                    }
                    break;
                case MESSAGE_TEXT_ELEMENT:
                    messageText = information;
                    break;
                case MESSAGE_STATUS_ELEMENT:
                    roleOrStatusTitle = information;
                    break;
                
                case TITLE_ELEMENT:
                    roleOrStatusTitle = information;
                    break;
                case DESCRIPTION_ELEMENT:
                    roleOrStatusDescription = information;
                    break;
                
                case USER_NICK_ELEMENT:
                    userNick = information;
                    break;
                case USER_ROLE_ELEMENT:
                    roleOrStatusTitle = information;
                    break;
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
        switch (qName) {
            case MESSAGE_MAIN_ELEMENT:
                messages.add(new Message(userNick, timeStamp, messageText,
                    new Status(StatusTitle.valueOf(roleOrStatusTitle))));
                break;
            case STATUS_MAIN_ELEMENT:
                statuses.add(new Status(StatusTitle.valueOf(roleOrStatusTitle),
                    roleOrStatusDescription));
                break;
            case ROLE_MAIN_ELEMENT:
                roles.add(new Role(RoleTitle.valueOf(roleOrStatusTitle),
                    roleOrStatusDescription));
                break;
            case USER_MAIN_ELEMENT:
                users.add(new User(userNick,
                    new Role(RoleTitle.valueOf(roleOrStatusTitle))));
                break;
        }
        
    }
    
}
