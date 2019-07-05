package com.epam.jdbc.parser;

import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.Role;
import com.epam.jdbc.datalayer.dto.Status;
import com.epam.jdbc.datalayer.dto.User;
import com.epam.jdbc.parser.dom.ChatDOMHelper;
import com.epam.jdbc.parser.sax.ChatSAXHelper;
import com.epam.jdbc.utils.MessageByDateReverseComparator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ParseHelper {
    
    private static final String ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "Can't send message from not existing user: %s";
    private static final int MESSAGES_MIN_COUNT = 0;
    
    private String sourceFilePath;
    private ChatSAXHelper saxHelper = new ChatSAXHelper();
    private ChatDOMHelper domHelper = new ChatDOMHelper();
    
    public ParseHelper(String sourcePath) {
        this.sourceFilePath = sourcePath;
    }
    
    public ParseHelper(ChatSAXHelper saxHelper, ChatDOMHelper domHelper) {
        this.saxHelper = saxHelper;
        this.domHelper = domHelper;
    }
    
    public List<User> getUsers()
        throws ParserConfigurationException, SAXException, IOException {
        return saxHelper.getUsers(sourceFilePath);
    }
    
    public List<Status> getStatuses()
        throws ParserConfigurationException, SAXException, IOException {
        return saxHelper.getStatuses(sourceFilePath);
    }
    
    public List<Role> getRoles()
        throws ParserConfigurationException, SAXException, IOException {
        return saxHelper.getRoles(sourceFilePath);
    }
    
    public List<Message> getLastMessages()
        throws ParserConfigurationException, SAXException, IOException {
        return getLastMessages(MESSAGES_MIN_COUNT);
    }
    
    public List<Message> getLastMessages(int count)
        throws IOException, SAXException, ParserConfigurationException {
        
        List<Message> messages = saxHelper.getMessages(sourceFilePath);
        
        messages.sort(new MessageByDateReverseComparator<>());
        
        if (messages.size() > count && count > MESSAGES_MIN_COUNT) {
            messages = messages.subList(MESSAGES_MIN_COUNT, count);
        }
        
        return messages;
    }
    
    public void sendMessage(Message message)
        throws IOException, SAXException, ParserConfigurationException,
                   IllegalAccessException, TransformerException {
        
        if (isUserExists(message.getSenderNick())) {
            domHelper.addMessage(sourceFilePath, message);
        } else {
            throw new IllegalAccessException(String.format(
                ILLEGAL_ACCESS_EXCEPTION_MESSAGE, message.getSenderNick()));
        }
    }
    
    public void addUser(User user)
        throws IOException, SAXException, TransformerException {
        domHelper.addUser(sourceFilePath, user);
    }
    
    public boolean isUserExists(String userNick)
        throws IOException, SAXException, ParserConfigurationException {
        
        List<User> allUsers = saxHelper.getUsers(sourceFilePath);
        
        boolean userExists = false;
        for (int i = 0; i < allUsers.size() && !userExists; i++) {
            userExists = allUsers.get(i).getNick().equals(userNick);
        }
        
        return userExists;
    }
    
    public void unkick(String userNick)
        throws IOException, SAXException, TransformerException {
        domHelper.unkick(sourceFilePath, userNick);
    }
}
