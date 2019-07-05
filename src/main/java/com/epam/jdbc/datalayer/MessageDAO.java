package com.epam.jdbc.datalayer;

import com.epam.jdbc.datalayer.dto.Message;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface MessageDAO {

    List<Message> getLast(int count)
            throws IOException, SAXException, ParserConfigurationException;

    void sendMessage(Message message)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException;
}
