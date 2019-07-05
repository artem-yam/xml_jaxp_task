package com.epam.jdbc.datalayer.xml;

import com.epam.jdbc.datalayer.MessageDAO;
import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.parser.ParseHelper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class XMLMessageDAO implements MessageDAO {
    
    private ParseHelper parseHelper;
    
    public XMLMessageDAO(String sourcePath) {
        parseHelper = new ParseHelper(sourcePath);
    }
    
    @Override
    public List<Message> getLast(int count)
        throws IOException, SAXException, ParserConfigurationException {
        return parseHelper.getLastMessages(count);
    }
    
    @Override
    public void sendMessage(Message message)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException {
        parseHelper.sendMessage(message);
    }
    
}
