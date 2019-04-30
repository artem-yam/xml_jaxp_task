package com.epam.chat.parser;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.Role;
import com.epam.chat.datalayer.dto.Status;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.parser.dom.ChatDOMHelper;
import com.epam.chat.parser.sax.ChatSAXHelper;
import com.epam.chat.utils.MessageByDateReverseComparator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ParseHelper {
    
    private static final String ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "Can't send message from not existing user: %s";
    
    private ChatSAXHelper saxHelper = new ChatSAXHelper();
    private ChatDOMHelper domHelper = new ChatDOMHelper();
    
    public ParseHelper() {
    }
    
    public ParseHelper(ChatSAXHelper saxHelper, ChatDOMHelper domHelper) {
        this.saxHelper = saxHelper;
        this.domHelper = domHelper;
    }
    
    public List<User> getUsers(String sourceXMLPath)
        throws ParserConfigurationException, SAXException, IOException {
        return saxHelper.getUsers(sourceXMLPath);
    }
    
    public List<Status> getStatuses(String sourceXMLPath)
        throws ParserConfigurationException, SAXException, IOException {
        return saxHelper.getStatuses(sourceXMLPath);
    }
    
    public List<Role> getRoles(String sourceXMLPath)
        throws ParserConfigurationException, SAXException, IOException {
        return saxHelper.getRoles(sourceXMLPath);
    }
    
    public List<Message> getLastMessages(String sourceXMLPath)
        throws ParserConfigurationException, SAXException, IOException {
        return getLastMessages(sourceXMLPath, 0);
    }
    
    public List<Message> getLastMessages(String sourceXMLPath, int count)
        throws IOException, SAXException, ParserConfigurationException {
        
        List<Message> messages = saxHelper.getMessages(sourceXMLPath);
        
        messages.sort(new MessageByDateReverseComparator<>());
        
        if (messages.size() > count && count != 0) {
            messages = messages.subList(0, count);
        }
        
        return messages;
    }
    
    public void sendMessage(String sourceXMLPath, Message message)
        throws IOException, SAXException, ParserConfigurationException,
                   IllegalAccessException, TransformerException {
        
        if (isUserExists(sourceXMLPath, message.getSenderNick())) {
            domHelper.addMessage(sourceXMLPath, message);
        } else {
            throw new IllegalAccessException(String.format(
                ILLEGAL_ACCESS_EXCEPTION_MESSAGE, message.getSenderNick()));
        }
    }
    
    public void addUser(String sourceXMLPath, User user)
        throws IOException, SAXException, TransformerException {
        domHelper.addUser(sourceXMLPath, user);
    }
    
    public boolean isUserExists(String sourceXMLPath, String userNick)
        throws IOException, SAXException, ParserConfigurationException {
        
        List<User> allUsers = saxHelper.getUsers(sourceXMLPath);
        
        boolean userExists = false;
        for (int i = 0; i < allUsers.size() && !userExists; i++) {
            userExists = allUsers.get(i).getNick().equals(userNick);
        }
        
        return userExists;
    }
    
    public void unkick(String sourceXMLPath, String userNick)
        throws IOException, SAXException, TransformerException {
        domHelper.unkick(sourceXMLPath, userNick);
    }
}
