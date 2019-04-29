package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.MessageDAO;
import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.parser.ParseHelper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class XMLMessageDAO implements MessageDAO {
    
    private ParseHelper parseHelper;
    private String sourceXMLPath;
    
    public XMLMessageDAO(String sourcePath) {
        this.sourceXMLPath = sourcePath;
        parseHelper = new ParseHelper();
    }
    
    @Override
    public List<Message> getLast(int count)
        throws IOException, SAXException, ParserConfigurationException {
        return parseHelper.getLastMessages(sourceXMLPath, count);
    }
    
    @Override
    public void sendMessage(Message message)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException {
        parseHelper.sendMessage(sourceXMLPath, message);
        
    }
    
}
