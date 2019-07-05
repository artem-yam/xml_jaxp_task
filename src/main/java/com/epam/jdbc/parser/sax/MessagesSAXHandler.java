package com.epam.jdbc.parser.sax;

import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.StatusTitle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessagesSAXHandler extends ChatSAXHandler {
    
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'kk:mm:ss";
    
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_USER_ELEMENT = "user_from";
    private static final String MESSAGE_DATE_ELEMENT = "time_stamp";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    
    private String currentElementName;
    private List<Message> messages;
    private Message newMessage;
    
    @Override public List<?> getParsedObjects() {
        return messages;
    }
    
    @Override
    public void startDocument() throws SAXException {
        messages = new ArrayList<>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        currentElementName = qName;
        if (MESSAGE_MAIN_ELEMENT.equals(currentElementName)) {
            newMessage = new Message();
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        String information = new String(ch, start, length);
        information = information.replace("%n", "").trim();
        
        if (!information.isEmpty() && newMessage != null) {
            switch (currentElementName) {
                case MESSAGE_USER_ELEMENT:
                    newMessage.setSenderNick(information);
                    break;
                case MESSAGE_DATE_ELEMENT:
                    try {
                        newMessage.setTimeStamp(
                            new SimpleDateFormat(DATE_FORMAT_PATTERN)
                                .parse(information));
                    } catch (ParseException parseException) {
                        throw new SAXException(parseException);
                    }
                    break;
                case MESSAGE_TEXT_ELEMENT:
                    newMessage.setMessage(information);
                    break;
                case MESSAGE_STATUS_ELEMENT:
                    newMessage.setStatus(StatusTitle.valueOf(information));
                    break;
            }
        }
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
        if (MESSAGE_MAIN_ELEMENT.equals(qName)) {
            messages.add(newMessage);
            newMessage = null;
        }
    }
    
}
