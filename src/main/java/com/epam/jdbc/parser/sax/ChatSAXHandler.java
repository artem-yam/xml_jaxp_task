package com.epam.jdbc.parser.sax;

import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public abstract class ChatSAXHandler extends DefaultHandler {
    
    public abstract List<?> getParsedObjects();
}
