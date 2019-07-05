package com.epam.jdbc.parser.sax;

import com.epam.jdbc.datalayer.dto.RoleTitle;
import com.epam.jdbc.datalayer.dto.User;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class UsersSAXHandler extends ChatSAXHandler {
    
    private static final String USER_MAIN_ELEMENT = "User";
    private static final String USER_NICK_ELEMENT = "nick";
    private static final String USER_ROLE_ELEMENT = "role";
    
    private String currentElementName;
    private List<User> users;
    private User newUser;
    
    @Override public List<?> getParsedObjects() {
        return users;
    }
    
    @Override
    public void startDocument() throws SAXException {
        users = new ArrayList<>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        currentElementName = qName;
        if (USER_MAIN_ELEMENT.equals(currentElementName)) {
            newUser = new User();
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        String information = new String(ch, start, length);
        information = information.replace("%n", "").trim();
        
        if (!information.isEmpty() && newUser != null) {
            switch (currentElementName) {
                case USER_NICK_ELEMENT:
                    newUser.setNick(information);
                    break;
                case USER_ROLE_ELEMENT:
                    newUser.setRole(RoleTitle.valueOf(information));
                    break;
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
        if (USER_MAIN_ELEMENT.equals(qName)) {
            users.add(newUser);
            newUser = null;
        }
        
    }
    
}
