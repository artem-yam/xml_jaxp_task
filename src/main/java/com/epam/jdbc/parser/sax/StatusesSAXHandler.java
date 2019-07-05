package com.epam.jdbc.parser.sax;

import com.epam.jdbc.datalayer.dto.Status;
import com.epam.jdbc.datalayer.dto.StatusTitle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class StatusesSAXHandler extends ChatSAXHandler {
    
    private static final String STATUS_MAIN_ELEMENT = "Status";
    private static final String TITLE_ELEMENT = "title";
    private static final String DESCRIPTION_ELEMENT = "description";
    
    private String currentElementName;
    private List<Status> statuses;
    private Status newStatus;
    
    @Override public List<?> getParsedObjects() {
        return statuses;
    }
    
    @Override
    public void startDocument() throws SAXException {
        statuses = new ArrayList<>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        currentElementName = qName;
        if (STATUS_MAIN_ELEMENT.equals(currentElementName)) {
            newStatus = new Status();
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        String information = new String(ch, start, length);
        information = information.replace("%n", "").trim();
        
        if (!information.isEmpty() && newStatus != null) {
            switch (currentElementName) {
                case TITLE_ELEMENT:
                    newStatus.setTitle(StatusTitle.valueOf(information));
                    break;
                case DESCRIPTION_ELEMENT:
                    newStatus.setDescription(information);
                    break;
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
        if (STATUS_MAIN_ELEMENT.equals(qName)) {
            statuses.add(newStatus);
            newStatus = null;
        }
        
    }
    
}
