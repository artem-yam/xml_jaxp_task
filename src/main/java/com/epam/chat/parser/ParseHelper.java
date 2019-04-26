package com.epam.chat.parser;

import com.epam.chat.datalayer.dto.Message;
import com.epam.chat.datalayer.dto.User;
import com.epam.chat.parser.dom.ChatDOMHelper;
import com.epam.chat.parser.sax.ChatSAXHandler;
import com.epam.chat.utils.MessageByDateReverseComparator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class ParseHelper {
    
    private static final String ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "Can't send message from not existing user: %s";
    
    private ChatSAXHandler saxHandler = new ChatSAXHandler();
    private ChatDOMHelper domHelper = new ChatDOMHelper();
    
    public ParseHelper() {
    }
    
    public ParseHelper(ChatSAXHandler saxHandler, ChatDOMHelper domHelper) {
        this.saxHandler = saxHandler;
        this.domHelper = domHelper;
    }
    
    public ChatSAXHandler getSaxHandler() {
        return saxHandler;
    }
    
    public ChatDOMHelper getDomHelper() {
        return domHelper;
    }
    
    public List<Message> getLastMessages(String sourceXMLPath)
        throws ParserConfigurationException, SAXException, IOException {
        return getLastMessages(sourceXMLPath, 0);
    }
    
    public List<Message> getLastMessages(String sourceXMLPath, int count)
        throws IOException, SAXException, ParserConfigurationException {
        saxHandler.parseFile(sourceXMLPath);
        
        List<Message> messages = saxHandler.getMessages();
        
        //сортировка (в начале списка - более поздние сообщения)
        messages.sort(new MessageByDateReverseComparator<>());
        
        if (messages.size() > count && count != 0) {
            messages = messages.subList(0, count);
        }
        
        return messages;
    }
    
    public void sendMessage(String sourceXMLPath, Message message)
        throws IOException, SAXException, TransformerException,
        ParserConfigurationException, IllegalAccessException {
        
        if (isUserExists(sourceXMLPath, message.getSenderNick())) {
            
            Document document = domHelper.getParsedDocument(
                sourceXMLPath);
            Element root = document.getDocumentElement();
            
            Node newMessageNode = domHelper.formMessageNode(document, message);
            
            root.insertBefore(newMessageNode, root.getFirstChild());
            
            domHelper.writeDocument(document, sourceXMLPath);
        } else {
            throw new IllegalAccessException(String.format(
                ILLEGAL_ACCESS_EXCEPTION_MESSAGE, message.getSenderNick()));
        }
    }
    
    public void addUser(String sourceXMLPath, User user)
        throws IOException, SAXException, TransformerException {
        
        Document document = domHelper.getParsedDocument(sourceXMLPath);
        Element root = document.getDocumentElement();
        
        Node userNode = domHelper.formUserNode(document, user);
        
        root.appendChild(userNode);
        
        domHelper.writeDocument(document, sourceXMLPath);
    }
    
    public boolean isUserExists(String sourceXMLPath, String userNick)
        throws IOException, SAXException, ParserConfigurationException {
        boolean userExists = false;
        
        saxHandler.parseFile(sourceXMLPath);
        List<User> allUsers = saxHandler.getUsers();
        for (User user : allUsers) {
            userExists = user.getNick().equals(userNick);
            if (userExists) {
                break;
            }
        }
        
        return userExists;
    }
}
