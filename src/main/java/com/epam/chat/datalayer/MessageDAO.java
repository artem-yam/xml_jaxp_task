package com.epam.chat.datalayer;

import com.epam.chat.datalayer.dto.Message;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface MessageDAO {

    List<Message> getLast(int count)
            throws IOException, SAXException, ParseException;

    void sendMessage(Message message)
            throws IOException, SAXException, TransformerException;
}
