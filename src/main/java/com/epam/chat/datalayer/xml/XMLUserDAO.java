package com.epam.chat.datalayer.xml;

import com.epam.chat.datalayer.UserDAO;
import com.epam.chat.datalayer.dto.*;
import com.epam.chat.utils.DOMHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLUserDAO implements UserDAO {

    private static final String USER_LOGIN_MESSAGE = "User %s logged in";
    private static final String USER_LOGOUT_MESSAGE = "User %s logged out";
    private static final String USER_MAIN_ELEMENT = "User";
    private static final String USER_NICK_ELEMENT = "nick";
    private static final String USER_ROLE_ELEMENT = "role";
    private static final String ROLE_MAIN_ELEMENT = "Role";
    private static final String ROLE_TITLE_ELEMENT = "title";
    private static final String ROLE_DESCRIPTION_ELEMENT = "description";
    private static final String MESSAGE_MAIN_ELEMENT = "Message";
    private static final String MESSAGE_TEXT_ELEMENT = "message";
    private static final String MESSAGE_STATUS_ELEMENT = "status";
    private static final String ILLEGAL_ACCESS_EXCEPTION_MESSAGE =
        "User %s already logged in";

    //Parsers

    @Override
    public void logIn(User loginingUser)
        throws IllegalAccessException, IOException, SAXException,
        ParseException, TransformerException {
        if (!isLogged(loginingUser)) {
            Message message = new Message(loginingUser, new Date(),
                String.format(USER_LOGIN_MESSAGE, loginingUser.getNick()),
                new Status(StatusTitle.LOGIN));

            new XMLMessageDAO().sendMessage(message);
        } else {
            throw new IllegalAccessException(
                String.format(ILLEGAL_ACCESS_EXCEPTION_MESSAGE,
                    loginingUser)
            );
        }
    }

    @Override
    public boolean isLogged(User user)
        throws ParseException, SAXException, IOException {

        List<Message> messages = new XMLMessageDAO().getLast(0);

        Message lastUserMessage = null;

        for (Message message : messages) {
            if (message.getFromUser().equals(user)) {
                lastUserMessage = message;

                break;
            }
        }

        return !(lastUserMessage == null ||
            lastUserMessage.getStatus().getTitle().equals(
                StatusTitle.LOGOUT));
    }

    @Override
    public void logout(User logoutingUser)
        throws IOException, SAXException, TransformerException {
        Message message = new Message(logoutingUser, new Date(),
            String.format(USER_LOGOUT_MESSAGE, logoutingUser.getNick()),
            new Status(StatusTitle.LOGOUT));

        new XMLMessageDAO().sendMessage(message);
    }

    @Override
    public void kick(User admin, User kickableUser)
        throws IOException, SAXException, TransformerException {
        Message message = new Message(admin, new Date(),
            kickableUser.getNick(),
            new Status(StatusTitle.KICK));

        new XMLMessageDAO().sendMessage(message);
    }

    @Override
    public void unkick(User user)
        throws ParseException, SAXException, IOException,
        TransformerException {
        if (isKicked(user)) {
            Document document = DOMHelper.getDocumentParsedWithDOM(
                DOMHelper.getSourceXmlFilePath());
            Element root = document.getDocumentElement();

            //Поиск соответствующего сообщения в xml
            Element messageElement =
                DOMHelper.findElement(root, MESSAGE_MAIN_ELEMENT,
                    new String[]{MESSAGE_TEXT_ELEMENT, MESSAGE_STATUS_ELEMENT},
                    new String[]{user.getNick(), StatusTitle.KICK.toString()});

            root.removeChild(messageElement);

            DOMHelper.writeDocument(document, DOMHelper.SOURCE_XML_FILE_PATH);
        }
    }

    @Override
    public boolean isKicked(User user)
        throws ParseException, SAXException, IOException {
        boolean isKicked = false;

        List<Message> messages = new XMLMessageDAO().getLast(0);

        for (Message message : messages) {
            if (message.getMessage().equals(user.getNick()) &&
                message.getStatus().getTitle().equals(StatusTitle.KICK)) {
                isKicked = true;

                break;
            }
        }

        return isKicked;
    }

    @Override
    public List<User> getAllLogged()
        throws IOException, SAXException, ParseException {
        List<User> loggedUsers = new ArrayList<>();

        Element root = DOMHelper.getDocumentParsedWithDOM(
            DOMHelper.getSourceXmlFilePath()).getDocumentElement();

        NodeList userNodes = root.getElementsByTagName(USER_MAIN_ELEMENT);

        for (int i = 0; i < userNodes.getLength(); i++) {
            Element userElement = (Element) userNodes.item(i);

            String nick =
                DOMHelper.getChildValue(userElement, USER_NICK_ELEMENT);

            User user = new User(nick, getRole(nick));

            if (isLogged(user)) {
                loggedUsers.add(user);
            }

        }

        return loggedUsers;
    }

    @Override
    public Role getRole(String nick) throws IOException, SAXException {
        Element root = DOMHelper.getDocumentParsedWithDOM(
            DOMHelper.getSourceXmlFilePath()).getDocumentElement();

        //Поиск соответствующего юзера в xml
        Element userElement =
            DOMHelper.findElement(root, USER_MAIN_ELEMENT,
                new String[]{USER_NICK_ELEMENT},
                new String[]{nick});
        String userRole =
            DOMHelper.getChildValue(userElement, USER_ROLE_ELEMENT);

        return new Role(RoleTitle.valueOf(userRole), DOMHelper
            .getChildValue(DOMHelper
                    .findElement(root, ROLE_MAIN_ELEMENT,
                        new String[]{ROLE_TITLE_ELEMENT},
                        new String[]{userRole}),
                ROLE_DESCRIPTION_ELEMENT));
    }
}

