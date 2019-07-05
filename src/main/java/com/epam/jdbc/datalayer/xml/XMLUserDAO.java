package com.epam.jdbc.datalayer.xml;

import com.epam.jdbc.datalayer.UserDAO;
import com.epam.jdbc.datalayer.dto.*;
import com.epam.jdbc.parser.ParseHelper;
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
    private static final String ADMIN_NICK_SUFFIX = "@epam.com";
    private static final String KICK_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
        "Kickable user %s doesn't exist";
    private static final String KICK_ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "User \'%s\' must be ADMIN to kick other users";
    
    private ParseHelper parseHelper;
    
    public XMLUserDAO(String sourcePath) {
        parseHelper = new ParseHelper(sourcePath);
    }
    
    @Override
    public void login(String loginingUserNick)
        throws IllegalAccessException, IOException, SAXException,
                   TransformerException, ParserConfigurationException {
        
        if (!parseHelper.isUserExists(loginingUserNick)) {
            RoleTitle userRole = RoleTitle.USER;
            if (loginingUserNick.endsWith(ADMIN_NICK_SUFFIX)) {
                userRole = RoleTitle.ADMIN;
            }
            User user = new User(loginingUserNick, userRole);
            
            parseHelper.addUser(user);
        }
        
        Message message = new Message(loginingUserNick, new Date(),
            String.format(USER_LOGIN_MESSAGE, loginingUserNick),
            StatusTitle.LOGIN);
        
        parseHelper.sendMessage(message);
    }
    
    @Override
    public boolean isLogged(String userNick)
        throws SAXException, IOException, ParserConfigurationException {
        
        List<Message> messages = parseHelper.getLastMessages();
        
        StatusTitle messageStatus = null;
        
        for (int i = 0; i < messages.size() && messageStatus == null; i++) {
            Message message = messages.get(i);
            if (message.getSenderNick().equals(userNick)) {
                messageStatus = message.getStatus();
            }
        }
        
        return !(messageStatus == null ||
                     StatusTitle.LOGOUT.equals(messageStatus) ||
                     StatusTitle.KICK.equals(messageStatus));
    }
    
    @Override
    public void logout(String logoutingUserNick)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException {
        Message message = new Message(logoutingUserNick, new Date(),
            String.format(USER_LOGOUT_MESSAGE, logoutingUserNick),
            StatusTitle.LOGOUT);
        
        parseHelper.sendMessage(message);
    }
    
    @Override
    public void kick(String adminNick, String kickableUserNick)
        throws IOException, SAXException, TransformerException,
                   ParserConfigurationException, IllegalAccessException {
        boolean canKick = false;
        
        List<User> allUsers = parseHelper.getUsers();
        
        User admin = null;
        for (int i = 0; i < allUsers.size() && admin == null; i++) {
            User user = allUsers.get(i);
            if (user.getNick().equals(adminNick)) {
                admin = user;
                canKick = user.getRole().equals(RoleTitle.ADMIN);
            }
        }
        
        if (canKick) {
            if (parseHelper.isUserExists(kickableUserNick)) {
                Message message = new Message(adminNick, new Date(),
                    kickableUserNick, StatusTitle.KICK);
                
                parseHelper.sendMessage(message);
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
            parseHelper.unkick(userNick);
        }
    }
    
    @Override
    public boolean isKicked(String userNick)
        throws SAXException, IOException, ParserConfigurationException {
        boolean isKicked = false;
        
        List<Message> messages = parseHelper.getLastMessages();
        
        for (int i = 0; i < messages.size() && !isKicked; i++) {
            Message message = messages.get(i);
            isKicked = message.getMessage().equals(userNick) &&
                           message.getStatus().equals(
                               StatusTitle.KICK);
        }
        
        return isKicked;
    }
    
    @Override
    public List<User> getAllLogged()
        throws IOException, SAXException, ParserConfigurationException {
        List<User> loggedUsers = new ArrayList<>();
        
        List<User> allUsers = parseHelper.getUsers();
        
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
        
        List<Role> roles = parseHelper.getRoles();
        List<User> users = parseHelper.getUsers();
        
        Role userRole = null;
        
        for (int i = 0; i < users.size() && userRole == null; i++) {
            User user = users.get(i);
            if (user.getNick().equals(userNick)) {
                for (int j = 0; j < roles.size() && userRole == null; j++) {
                    Role role = roles.get(j);
                    if (role.getTitle().equals(user.getRole())) {
                        userRole = role;
                    }
                }
            }
        }
        
        return userRole;
    }
}

