package com.epam.jdbc.parser.dom;

import com.epam.jdbc.datalayer.dto.Message;
import com.epam.jdbc.datalayer.dto.StatusTitle;
import com.epam.jdbc.datalayer.dto.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ChatDOMHelper extends DOMHelper {
    
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'kk:mm:ss";
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_USER_ELEMENT = "user_from";
    private static final String MESSAGE_DATE_ELEMENT = "time_stamp";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    private static final String[] MESSAGE_CHILD_ELEMENTS =
        new String[]{MESSAGE_USER_ELEMENT, MESSAGE_DATE_ELEMENT,
            MESSAGE_TEXT_ELEMENT, MESSAGE_STATUS_ELEMENT};
    
    private static final String USER_MAIN_ELEMENT = "User";
    private static final String USER_NICK_ELEMENT = "nick";
    private static final String USER_ROLE_ELEMENT = "role";
    private static final String[] USER_CHILD_ELEMENTS =
        new String[]{USER_NICK_ELEMENT, USER_ROLE_ELEMENT};
    
    public Node formMessageNode(Document document, Message message) {
        return createElementWithSimpleChildren(document, MESSAGE_MAIN_ELEMENT,
            MESSAGE_CHILD_ELEMENTS,
            new String[]{message.getSenderNick(), new SimpleDateFormat(
                DATE_FORMAT_PATTERN).format(message.getTimeStamp()),
                message.getMessage(),
                message.getStatus().toString()});
    }
    
    public Node formUserNode(Document document, User user) {
        return createElementWithSimpleChildren(document, USER_MAIN_ELEMENT,
            USER_CHILD_ELEMENTS,
            new String[]{user.getNick(), user.getRole().toString()});
    }
    
    public void addMessage(String sourceXMLPath, Message message)
        throws IOException, SAXException, TransformerException {
        Document document = getParsedDocument(sourceXMLPath);
        Element root = document.getDocumentElement();
        
        Node newMessageNode = formMessageNode(document, message);
        
        root.insertBefore(newMessageNode, root.getFirstChild());
        
        writeDocument(document, sourceXMLPath);
    }
    
    public void addUser(String sourceXMLPath, User user)
        throws IOException, SAXException, TransformerException {
        Document document = getParsedDocument(sourceXMLPath);
        Element root = document.getDocumentElement();
        
        Node userNode = formUserNode(document, user);
        
        root.appendChild(userNode);
        
        writeDocument(document, sourceXMLPath);
    }
    
    public void unkick(String sourceXMLPath, String userNick)
        throws SAXException, IOException, TransformerException {
        Document document = getParsedDocument(sourceXMLPath);
        Element root = document.getDocumentElement();
        
        Element messageElement = findElement(root, MESSAGE_MAIN_ELEMENT,
            new String[]{MESSAGE_TEXT_ELEMENT, MESSAGE_STATUS_ELEMENT},
            new String[]{userNick, StatusTitle.KICK.toString()});
        
        root.removeChild(messageElement);
        
        writeDocument(document, sourceXMLPath);
    }
    
}
