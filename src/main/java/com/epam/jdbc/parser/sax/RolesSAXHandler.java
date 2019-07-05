package com.epam.jdbc.parser.sax;

import com.epam.jdbc.datalayer.dto.Role;
import com.epam.jdbc.datalayer.dto.RoleTitle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class RolesSAXHandler extends ChatSAXHandler {
    
    private static final String ROLE_MAIN_ELEMENT = "Role";
    private static final String TITLE_ELEMENT = "title";
    private static final String DESCRIPTION_ELEMENT = "description";
    
    private String currentElementName;
    private List<Role> roles;
    private Role newRole;
    
    @Override public List<?> getParsedObjects() {
        return roles;
    }
    
    @Override
    public void startDocument() throws SAXException {
        roles = new ArrayList<>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        currentElementName = qName;
        if (ROLE_MAIN_ELEMENT.equals(currentElementName)) {
            newRole = new Role();
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        String information = new String(ch, start, length);
        information = information.replace("%n", "").trim();
        
        if (!information.isEmpty() && newRole != null) {
            switch (currentElementName) {
                case TITLE_ELEMENT:
                    newRole.setTitle(RoleTitle.valueOf(information));
                    break;
                case DESCRIPTION_ELEMENT:
                    newRole.setDescription(information);
                    break;
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
        if (ROLE_MAIN_ELEMENT.equals(qName)) {
            roles.add(newRole);
            newRole = null;
        }
    }
    
}
