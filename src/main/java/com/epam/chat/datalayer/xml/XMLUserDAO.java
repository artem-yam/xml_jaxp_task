package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.*;
import com.epam.chat.parser.ParseHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLUserDAO implements UserDAO {
    
    private static final String USER_LOGIN_MESSAGE = "User %s logged in";
    private static final String USER_LOGOUT_MESSAGE = "User %s logged out";
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    private static final String LOGIN_ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "User %s already logged in";
    private static final String ADMIN_NICK_SUFFIX = "@epam.com";
    private static final String KICK_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
        "Kickable user %s doesn't exist";
    private static final String KICK_ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "User \'%s\' must be ADMIN to kick other users";
    
    private ParseHelper parseHelper;
    private String sourceXMLPath;
    
    public XMLUserDAO(String sourcePath) {
        this.sourceXMLPath = sourcePath;
        parseHelper = new ParseHelper();
    }
    
    @Override
    public void login(String loginingUserNick)
        throws IllegalAccessException, IOException, SAXException,
                   TransformerException, ParserConfigurationException {
        
        if (!isLogged(loginingUserNick)) {
            if (!parseHelper.isUserExists(sourceXMLPath, loginingUserNick)) {
                RoleTitle userRole = RoleTitle.USER;
                if (loginingUserNick.endsWith(ADMIN_NICK_SUFFIX)) {
                    userRole = RoleTitle.ADMIN;
                }
                User user = new User(loginingUserNick, new Role(userRole));
                
                parseHelper.addUser(sourceXMLPath, user);
            }
            
            Message message = new Message(loginingUserNick, new Date(),
                String.format(USER_LOGIN_MESSAGE, loginingUserNick),
                new Status(StatusTitle.LOGIN));
            
            parseHelper.sendMessage(sourceXMLPath, message);
        } else {
            throw new IllegalAccessException(String.format(
                LOGIN_ILLEGAL_ACCESS_EXCEPTION_MESSAGE, loginingUserNick));
        }
    }
    
    @Override
    public boolean isLogged(String userNick)
        throws SAXException, IOException, ParserConfigurationException {
        
        parseHelper.parseFileWithSAX(sourceXMLPath);
        
        List<Message> messages = parseHelper.getSaxHelper().getMessages();
        
        Message lastUserMessage = null;
        
        for (Message message : messages) {
            if (message.getSenderNick().equals(userNick)) {
                lastUserMessage = message;
                
                break;
            }
        }
        
        return !(lastUserMessage == null ||
                     lastUserMessage.getStatus().getTitle().equals(
                         StatusTitle.LOGOUT));
    }
    
    @Override
    public void logout(String logoutingUserNick)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException {
        Message message = new Message(logoutingUserNick, new Date(),
            String.format(USER_LOGOUT_MESSAGE, logoutingUserNick),
            new Status(StatusTitle.LOGOUT));
        
        parseHelper.sendMessage(sourceXMLPath, message);
    }
    
    @Override
    public void kick(String adminNick, String kickableUserNick)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException {
        boolean canKick = false;
        
        parseHelper.parseFileWithSAX(sourceXMLPath);
        
        List<User> allUsers = parseHelper.getSaxHelper().getUsers();
        
        for (User user : allUsers) {
            if (user.getNick().equals(adminNick)) {
                if (user.getRole().getTitle().equals(RoleTitle.ADMIN)) {
                    canKick = true;
                }
                break;
            }
        }
        
        if (canKick) {
            if (parseHelper.isUserExists(sourceXMLPath, kickableUserNick)) {
                Message message = new Message(adminNick, new Date(),
                    kickableUserNick, new Status(StatusTitle.KICK));
                
                parseHelper.sendMessage(sourceXMLPath, message);
            } else {
                throw new IllegalArgumentException(String.format(
                    KICK_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, kickableUserNick));
            }
        } else {
            throw new IllegalAccessException(String.format(
                KICK_ILLEGAL_ACCESS_EXCEPTION_MESSAGE, adminNick));
        }
    }
    
    @Override
    public void unkick(String userNick)
        throws SAXException, IOException, TransformerException,
                   ParserConfigurationException {
        if (isKicked(userNick)) {
            Document document =
                parseHelper.getDomHelper().getParsedDocument(
                    sourceXMLPath);
            Element root = document.getDocumentElement();
            
            Element messageElement = parseHelper.getDomHelper().findElement(
                root, MESSAGE_MAIN_ELEMENT,
                new String[]{MESSAGE_TEXT_ELEMENT, MESSAGE_STATUS_ELEMENT},
                new String[]{userNick, StatusTitle.KICK.toString()});
            
            root.removeChild(messageElement);
            
            parseHelper.getDomHelper().writeDocument(document, sourceXMLPath);
            
        }
    }
    
    @Override
    public boolean isKicked(String userNick)
        throws SAXException, IOException, ParserConfigurationException {
        boolean isKicked = false;
        
        List<Message> messages = parseHelper.getLastMessages(sourceXMLPath);
        
        for (Message message : messages) {
            if (message.getMessage().equals(userNick) &&
                    message.getStatus().getTitle().equals(StatusTitle.KICK)) {
                isKicked = true;
                
                break;
            }
        }
        
        return isKicked;
    }
    
    @Override
    public List<User> getAllLogged()
        throws IOException, SAXException, ParserConfigurationException {
        List<User> loggedUsers = new ArrayList<>();
        
        parseHelper.parseFileWithSAX(sourceXMLPath);
        
        List<User> allUsers = new ArrayList<>(
            parseHelper.getSaxHelper().getUsers());
        
        for (User user : allUsers) {
            if (isLogged(user.getNick())) {
                loggedUsers.add(user);
            }
        }
        
        return loggedUsers;
    }
    
    @Override
    public Role getRole(String userNick)
        throws IOException, SAXException, ParserConfigurationException {
        
        parseHelper.parseFileWithSAX(sourceXMLPath);
        
        List<Role> roles = parseHelper.getSaxHelper().getRoles();
        List<User> users = parseHelper.getSaxHelper().getUsers();
        
        Role userRole = null;
        
        for (User user : users) {
            if (user.getNick().equals(userNick)) {
                for (Role role : roles) {
                    if (role.getTitle().equals(user.getRole().getTitle())) {
                        userRole = role;
                        break;
                    }
                }
                break;
            }
        }
        
        return userRole;
    }
}

